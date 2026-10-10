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
import com.kristina.gwttreecrud.shared.InputValidator;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeAddPresenter implements AddChildNodeEventHandler, AddRootNodeEventHandler {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";
    
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
        try {
            Long validParentId = addingRoot ? null : InputValidator.validateParentId(parentId);

            final TreeNode node = new TreeNode(null, validParentId, InputValidator.validateName(name), InputValidator.validateIp(ip), InputValidator.validatePort(port));

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
        } catch (TreeCrudProgramException e) {
            view.showError(e.getMessage());
        }
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
