package com.kristina.gwttreecrud.client.nodeadd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.AppGwtService;
import com.kristina.gwttreecrud.client.events.AddChildNodeEvent;
import com.kristina.gwttreecrud.client.events.AddChildNodeEventHandler;
import com.kristina.gwttreecrud.client.events.AddRootNodeEvent;
import com.kristina.gwttreecrud.client.events.AddRootNodeEventHandler;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.NodeAddedEvent;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddInterface.NodeAddViewHandler;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeAddPresenter implements AddChildNodeEventHandler, AddRootNodeEventHandler {
    private static final String ERROR_EMPTY_PARENT_ID = "The parent ID field is empty!";
    private static final String ERROR_UNKNOWN = "An unknown error occurred";
    private static final String ERROR_NOT_NUMBER_PORT = "The port must be a number!";
    private static final String ERROR_NOT_NUMBER_ID = "The parent ID must be a number!";
    private static final String ERROR_EMPTY_FIELD = "One of the fields was empty!";

    private NodeAddView view;
    
    private boolean addingRoot;

    public NodeAddPresenter(NodeAddView viewParameter) {
        this.view = viewParameter;

        view.setHandler(new NodeAddViewHandler() {
            @Override
            public void onSaveNode(String parentId, String nodeName, String nodeIp, String port) {
                saveNode(parentId, nodeName, nodeIp, port);
            }

            @Override
            public void onCancel() {
                view.hideAddCard();
            }
        });

        AppEventBus.get().addHandler(AddChildNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(AddRootNodeEvent.TYPE, this);
    }

    private void saveNode(String parentId, String name, String ip, String port) {
        if (name.trim().isEmpty()
                || ip.trim().isEmpty()
                || port.trim().isEmpty()) {
            view.showError(ERROR_EMPTY_FIELD);
            return;
        }

        Long parentIdLong = null;
        Integer portInt;

        if (!addingRoot) {
            if (parentId.trim().isEmpty()) {
                view.showError(ERROR_EMPTY_PARENT_ID);
                return;
            }
            try {
                parentIdLong = Long.valueOf(parentId);
            } catch (NumberFormatException e) {
                view.showError(ERROR_NOT_NUMBER_ID);
                return;
            }
        }

        try {
            portInt = Integer.valueOf(port);
        } catch (NumberFormatException e) {
            view.showError(ERROR_NOT_NUMBER_PORT);
            return;
        }

        final TreeNode node = new TreeNode(null, parentIdLong, name, ip, portInt);
        AppGwtService.get().insertNode(node, new AsyncCallback<TreeNode>() {
            @Override
            public void onSuccess(TreeNode savedNode) {
                GWT.log("Узел добавлен!");
                view.hideAddCard();
                AppEventBus.get().fireEvent(new NodeAddedEvent(savedNode));
            }

            @Override
            public void onFailure(Throwable e) {
                if (e instanceof TreeCrudProgramException) {
                    Window.alert(e.getMessage());
                } else {
                    Window.alert(ERROR_UNKNOWN);
                }
            }
        });

    }

    @Override
    public void addChildNode(AddChildNodeEvent event) {
        if (event.getParentId() == null) {
            return;
        }
        addingRoot = false;
        view.showAddCard(event.getParentId());
    }

    @Override
    public void addRootNode(AddRootNodeEvent event) {
        addingRoot = true;
        view.showAddRootCard();
    }
}
