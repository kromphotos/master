package com.kristina.gwttreecrud.client.nodeadd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
//import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.client.events.AddChildNodeEvent;
import com.kristina.gwttreecrud.client.events.AddChildNodeEventHandler;
import com.kristina.gwttreecrud.client.events.AddRootNodeEvent;
import com.kristina.gwttreecrud.client.events.AddRootNodeEventHandler;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.NodeAddedEvent;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeAddPresenter implements AddChildNodeEventHandler, AddRootNodeEventHandler {
    private NodeAddView view;
    //private TreeController controller;
    private boolean addingRoot;
    private GwtServiceAsync service = GWT.create(GwtService.class);
    
    public NodeAddPresenter(NodeAddView view) {
        this.view = view;
        
        AppEventBus.get().addHandler(AddChildNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(AddRootNodeEvent.TYPE, this);
    }
    
    //public void setController(TreeController controller) {
        //this.controller = controller;
    //}
    
    public void startAddChild(Integer parentId) {
        addingRoot = false;
        view.showAddCard(parentId);
    }
    
    public void startAddingRoot() {
        addingRoot = true;
        view.showAddRootCard();
    }
    
    public void saveNode(String parentId, String name, String ip, String port) {
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
        
        final TreeNode node = new TreeNode(null, parentIdInt, name, ip, portInt);
        //AsyncCallback<Void> не воид!
        service.insertNode(node, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                GWT.log("Узел добавлен!");
                view.hideAddCard();
                AppEventBus.get().fireEvent(new NodeAddedEvent(node));
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка добавления узла",caught);
            }
        });

    }
    
    public void cancel() {
        view.hideAddCard();
    }
    
    @Override
    public void addChildNode(AddChildNodeEvent event) {
        if (event.getParentId() == null) {
            return;
        }
        startAddChild(event.getParentId());
    }
    
    @Override
    public void addRootNode(AddRootNodeEvent event) {
        startAddingRoot();
    }
    

}
