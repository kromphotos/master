package com.kristina.gwttreecrud.client.nodeinfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.AppGwtService;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.ClearSelectionEvent;
import com.kristina.gwttreecrud.client.events.ClearSelectionEventHandler;
import com.kristina.gwttreecrud.client.events.EditNodeEvent;
import com.kristina.gwttreecrud.client.events.EditNodeEventHandler;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEventHandler;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEvent;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoInterface.NodeInfoViewHandler;
import com.kristina.gwttreecrud.shared.InputValidator;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeInfoPresenter implements NodeSelectedEventHandler, EditNodeEventHandler, ClearSelectionEventHandler {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";

    private NodeInfoInterface view;
    private TreeNode selectedNode;

    public NodeInfoPresenter(NodeInfoInterface viewParameter) {
        this.view = viewParameter;

        view.setHandler(new NodeInfoViewHandler() {
            @Override
            public void onSaveNode(String name, String ip, String port) {
                saveNode(name, ip, port);
            }

            @Override
            public void onCancel() {
                if (selectedNode != null) {
                    view.showNode(new NodeInfoViewData(selectedNode.getId(),
                            selectedNode.getParentId(),
                            selectedNode.getName(),
                            selectedNode.getIp(),
                            selectedNode.getPort()));
                }
            }
        });

        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this);
        AppEventBus.get().addHandler(EditNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);
    }

    private void updateNodeInfo(TreeNode node) {
        selectedNode = node;

        if (selectedNode == null) {
            clear();
            return;
        }

        view.showNode(new NodeInfoViewData(node.getId(),
                node.getParentId(),
                node.getName(),
                node.getIp(),
                node.getPort()));
    }

    private void clear() {
        selectedNode = null;
        view.clear();
    }

    private void saveNode(String name, String ip, String port) {
        if (selectedNode == null) {
            return;
        }

        try {
            selectedNode.setName(InputValidator.validateName(name));
            selectedNode.setIp(InputValidator.validateIp(ip));
            selectedNode.setPort(InputValidator.validatePort(port));

            AppGwtService.get().updateNode(selectedNode, new AsyncCallback<TreeNode>() {
                @Override
                public void onSuccess(TreeNode updatedNode) {
                    GWT.log("Узел успешно обновлён");
                    if (selectedNode.getId().equals(updatedNode.getId())) {
                        updateNodeInfo(updatedNode);
                    }
                    AppEventBus.get().fireEvent(new NodeUpdatedEvent(updatedNode));
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
    public void onNodeSelected(NodeSelectedEvent event) {
        TreeNode node = event.getNode();
        updateNodeInfo(node);
    }

    @Override
    public void editNode(EditNodeEvent event) {
        if (selectedNode == null) {
            return;
        }
        view.showEditMode(new NodeInfoViewData(selectedNode.getId(),
                selectedNode.getParentId(),
                selectedNode.getName(),
                selectedNode.getIp(),
                selectedNode.getPort()));
    }

    @Override
    public void clearSelection(ClearSelectionEvent event) {
        clear();
    }
}
