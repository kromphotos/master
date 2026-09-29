package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.AppGwtService;
import com.kristina.gwttreecrud.client.events.AddChildNodeEvent;
import com.kristina.gwttreecrud.client.events.AddRootNodeEvent;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.ClearSelectionEvent;
import com.kristina.gwttreecrud.client.events.ClearSelectionEventHandler;
import com.kristina.gwttreecrud.client.events.DeleteNodeEvent;
import com.kristina.gwttreecrud.client.events.EditNodeEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEventHandler;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsInterface.NodeActionsViewHandler;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeActionsPresenter implements NodeSelectedEventHandler, ClearSelectionEventHandler {
    private static final String LOG_ERROR_OF_DELETE_NODE = "Ошибка удаления узла";
    private static final String WINDOW_MESSAGE = "Вы действительно хотите выполнить удаление?";
    //TODO(by Tutor)
    // singleton? зачем тебе для него переменная то личная вообще теперь
    //private GwtServiceAsync service = GwtServiceCreator.get();
    private NodeActionsView view;
    private TreeNode selectedNode;

    public NodeActionsPresenter(NodeActionsView view) {
        this.view = view;

        view.setHandler(new NodeActionsViewHandler() {
            //TODO(by Tutor)
            // что за новые строки после каждого вызова метода?
            // и все, что помещается в три строки и не переиспользуется в отдельный метод вынесить не нужно
            @Override
            public void onEdit() {
                if (selectedNode != null) {
                    AppEventBus.get().fireEvent(new EditNodeEvent());
                }
            }

            @Override
            public void onDelete() {
                if (selectedNode == null) {
                    return;
                }

                //TODO(by Tutor)
                // решили же, что корень удалять можно

                //TODO(by Tutor)
                // зачем выносить в отдельную переменную?
                if (!Window.confirm(WINDOW_MESSAGE)) {
                    return;
                }

                final Integer nodeId = selectedNode.getId();

                AppGwtService.get().deleteById(nodeId, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        AppEventBus.get().fireEvent(new DeleteNodeEvent(nodeId));
                        AppEventBus.get().fireEvent(new ClearSelectionEvent());
                        clearSelection();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log(LOG_ERROR_OF_DELETE_NODE, caught);
                    }
                });
            }

            @Override
            public void onChild() {
                if (selectedNode == null) {
                    return;
                }
                AppEventBus.get().fireEvent(new AddChildNodeEvent(selectedNode.getId()));
            }

            @Override
            public void onAddRoot() {
                AppEventBus.get().fireEvent(new AddRootNodeEvent());
            }
        });

        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this);
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);
    }

    /*
    public void editNode() {
      //TODO(by Tutor)
      // в данном случае так короче и понятнее
    //        if (selectedNode != null) {
    //            AppEventBus.get().fireEvent(new EditNodeEvent());
    //        }
        
        if (selectedNode == null) {
            return;
        }
        AppEventBus.get().fireEvent(new EditNodeEvent());
    }
    */
    /*
    public void addChildNode() {
        if (selectedNode == null) {
            return;
        }
        AppEventBus.get().fireEvent(new AddChildNodeEvent(selectedNode.getId()));
    }
    */
    /*
    public void addRootNode() {
        AppEventBus.get().fireEvent(new AddRootNodeEvent());
    }
    */

    //TODO(by Tutor)
    // зачем паблик? зачем отдельным методом?
    /*
    public void selectNode(TreeNode node) {
        selectedNode = node;
        view.setNodeSelected(node != null);
    }
    */
    /*
    public void deleteNode() {
        if (selectedNode == null) {
            return;
        }
        
        //TODO(by Tutor)
        // решили же, что корень удалять можно
    
        
        //TODO(by Tutor)
        // зачем выносить в отдельную переменную?
        boolean confirmed = Window.confirm(
                "Вы действительно хотите выполнить удаление?");
    
        if (!confirmed) {
            return;
        }
    
        final Integer nodeId = selectedNode.getId();
    
        GwtServiceCreator.get().deleteById(nodeId, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                AppEventBus.get().fireEvent(new DeleteNodeEvent(nodeId));
                AppEventBus.get().fireEvent(new ClearSelectionEvent());
                clearSelection();
            }
    
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка удаления узла", caught);
            }
        });
    }
    */

    //TODO(by Tutor)
    // зачем паблик?
    private void clearSelection() {
        selectedNode = null;
        view.setNodeSelected(false);
    }

    @Override
    public void onNodeSelected(NodeSelectedEvent event) {
        TreeNode node = event.getNode();
        selectedNode = node;
        view.setNodeSelected(node != null);
    }

    @Override
    public void clearSelection(ClearSelectionEvent event) {
        clearSelection();
    }
}
