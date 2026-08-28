package com.kristina.gwttreecrud.client.nodeadd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeAddPresenter {
    private NodeAddView view;
    private TreeController controller;
    private boolean addingRoot;
    private GwtServiceAsync service = GWT.create(GwtService.class);
    
    public NodeAddPresenter(NodeAddView view) {
        this.view = view;
    }
    
    public void setController(TreeController controller) {
        this.controller = controller;
    }
    
    public void startAddChild(Integer parentId) {
        addingRoot = false;
        view.showAddCard(parentId);
    }
    
    public void startAddingRoot() {
        addingRoot = true;
        view.showAddRootCard();
    }
    
    public void saveNode() {
        String parentId = view.getParentId();
        String name = view.getNodeName();
        String ip = view.getNodeIp();
        String port = view.getNodePort();
        
        if (name.trim().isEmpty()
                || ip.trim().isEmpty()
                || port.trim().isEmpty()) {
            view.showError("Одно из полей было пустое!");
            return;
        }
        
        Integer parentIdInt = null;
        Integer portInt;
        
        if (!addingRoot && !parentId.trim().isEmpty()) {
            try {
                parentIdInt = Integer.valueOf(parentId);
            } catch (NumberFormatException e) {
                view.showError("ID родителя должен быть числом!");
                return;
            }
        }
        
        try {
            portInt = Integer.valueOf(port);
        } catch (NumberFormatException e) {
            view.showError("Порт должен быть числом!");
            return;
        }
        
        TreeNode node = new TreeNode(null, parentIdInt, name, ip, portInt);
        
        service.insertNode(node, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                GWT.log("Узел добавлен!");
                view.hideAddCard();
                controller.refresh();
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка добавления узла",caught);
            }
        });

    }

}
