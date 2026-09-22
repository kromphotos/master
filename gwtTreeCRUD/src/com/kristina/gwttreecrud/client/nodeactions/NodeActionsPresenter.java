package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.GwtServiceCreator;
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
    //TODO(by Tutor)
    // singleton? зачем тебе для него переменная то личная вообще теперь
    private GwtServiceAsync service = GwtServiceCreator.get();
    
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
                editNode();
                
            }
            @Override
            public void onDelete() {
                deleteNode();
                
            }
            @Override
            public void onChild() {
                addChildNode();
                
            }
            @Override
            public void onAddRoot() {
                addRootNode();
                
            }
        });

        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this); 
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);
    }

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

    public void addChildNode() {
        if (selectedNode == null) {
            return;
        }
        AppEventBus.get().fireEvent(new AddChildNodeEvent(selectedNode.getId()));
    }

    public void addRootNode() {
        AppEventBus.get().fireEvent(new AddRootNodeEvent());
    }

    //TODO(by Tutor)
    // зачем паблик? зачем отдельным методом?
    public void selectNode(TreeNode node) {
        selectedNode = node;
        view.setNodeSelected(node != null);
    }

    public void deleteNode() {
        if (selectedNode == null) {
            return;
        }
        
        //TODO(by Tutor)
        // решили же, что корень удалять можно
        if (selectedNode.getParentId() == null) {
            view.showMessage("Корневую ноду удалять запрещено!");
            return;
        }

        
        //TODO(by Tutor)
        // зачем выносить в отдельную переменную?
        boolean confirmed = Window.confirm(
                "Вы действительно хотите выполнить удаление?");

        if (!confirmed) {
            return;
        }

        final Integer nodeId = selectedNode.getId();

        service.deleteById(nodeId, new AsyncCallback<Void>() {
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

    //TODO(by Tutor)
    // зачем паблик?
    public void clearSelection() {
        selectedNode = null;
        view.setNodeSelected(false);
    }

    @Override
    public void onNodeSelected(NodeSelectedEvent event) {
        TreeNode node = event.getNode();
        selectNode(node);
    }
    
    @Override
    public void clearSelection(ClearSelectionEvent event) {
        clearSelection();
    }
}
