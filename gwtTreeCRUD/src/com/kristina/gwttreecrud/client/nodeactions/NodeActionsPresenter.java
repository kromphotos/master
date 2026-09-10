package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
//import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.client.events.AddChildNodeEvent;
import com.kristina.gwttreecrud.client.events.AddRootNodeEvent;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.ClearSelectionEvent;
import com.kristina.gwttreecrud.client.events.ClearSelectionEventHandler;
import com.kristina.gwttreecrud.client.events.DeleteNodeEvent;
import com.kristina.gwttreecrud.client.events.EditNodeEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEventHandler;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeActionsPresenter implements NodeSelectedEventHandler, ClearSelectionEventHandler {
    private GwtServiceAsync service = GWT.create(GwtService.class);

    private NodeActionsView view;
    //private TreeController controller;

    private TreeNode selectedNode;

    public NodeActionsPresenter(NodeActionsView view) {
        this.view = view;

        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this); //подписка на события типа NodeSelectedEvent
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);
    }

    //public void setController(TreeController controller) {
        //this.controller = controller;
   // }

    public void editNode() {
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

    public void selectNode(TreeNode node) {
        selectedNode = node;
        view.setNodeSelected(node != null);
    }

    public void deleteNode() {
        if (selectedNode == null) {
            return;
        }

        if (selectedNode.getParentId() == null) {
            view.showMessage("Корневую ноду удалять запрещено!");
            return;
        }

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
                clearSelection();
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка удаления узла", caught);
            }
        });
    }

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
