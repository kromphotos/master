package com.kristina.gwttreecrud.client.nodeadd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.GwtServiceCreator;
import com.kristina.gwttreecrud.client.events.AddChildNodeEvent;
import com.kristina.gwttreecrud.client.events.AddChildNodeEventHandler;
import com.kristina.gwttreecrud.client.events.AddRootNodeEvent;
import com.kristina.gwttreecrud.client.events.AddRootNodeEventHandler;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.NodeAddedEvent;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddView.NodeAddViewHandler;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeAddPresenter implements AddChildNodeEventHandler, AddRootNodeEventHandler {
    private NodeAddView view;
    private boolean addingRoot;
    private GwtServiceAsync service = GwtServiceCreator.get();
    
    public NodeAddPresenter(NodeAddView view) {
        this.view = view;
        
        view.setHandler(new NodeAddViewHandler() {
            @Override
            public void onSaveNode() {
                saveNode(NodeAddPresenter.this.view.getParentId(),
                        NodeAddPresenter.this.view.getNodeName(),
                        NodeAddPresenter.this.view.getNodeIp(),
                        NodeAddPresenter.this.view.getNodePort());
            }
            @Override
            public void onCancel() {
                cancel();
            }
        });
        
        AppEventBus.get().addHandler(AddChildNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(AddRootNodeEvent.TYPE, this);
    }
    
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
        service.insertNode(node, new AsyncCallback<TreeNode>() {
            @Override
            public void onSuccess(TreeNode savedNode) {
                GWT.log("Узел добавлен!");
                view.hideAddCard();
                AppEventBus.get().fireEvent(new NodeAddedEvent(savedNode));
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
