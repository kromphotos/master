package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.user.client.Window;
import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEventHandler;
import com.kristina.gwttreecrud.shared.TreeNode;


public class NodeActionsPresenter implements NodeSelectedEventHandler {
    private NodeActionsView view;
    private TreeController controller;
    
    private TreeNode selectedNode;
    
    public NodeActionsPresenter(NodeActionsView view) {
        this.view = view;
        
        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this); //подписка на события типа NodeSelectedEvent
    }

    public void setController(TreeController controller) {
        this.controller = controller;
    }

    public void editNode() {
        if (selectedNode == null) {
            return;
        }
        controller.editNode();
    }

    public void addChildNode() {
        if (selectedNode == null) {
            return;
        }
        controller.addChildNode();
    }
    
    public void addRootNode() {
        controller.addRootNode();
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

        controller.deleteNode();
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

}
