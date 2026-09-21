package com.kristina.gwttreecrud.client.nodeinfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.GwtServiceCreator;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.ClearSelectionEvent;
import com.kristina.gwttreecrud.client.events.ClearSelectionEventHandler;
import com.kristina.gwttreecrud.client.events.EditNodeEvent;
import com.kristina.gwttreecrud.client.events.EditNodeEventHandler;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEventHandler;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEvent;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoInterface.NodeInfoViewHandler;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeInfoPresenter implements NodeSelectedEventHandler, EditNodeEventHandler, ClearSelectionEventHandler {
    private GwtServiceAsync service = GwtServiceCreator.get();
    private NodeInfoInterface view;
    private NodeInfoViewData viewData;
    private TreeNode selectedNode;

    public NodeInfoPresenter(NodeInfoInterface view, NodeInfoViewData data) {
        this.view = view;
        this.viewData = data;
        
        view.setHandler(new NodeInfoViewHandler() {
            @Override
            public void onSaveNode(String name, String ip, String port) {
                saveNode(name,ip,port);
            }
            @Override
            public void onCancel() {
                cancelEdit();
            }
        });

        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this);
        AppEventBus.get().addHandler(EditNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);
    }

    public void selectNode(TreeNode node) {
        selectedNode = node;

        if (node == null) {
            clear();
            return;
        }
        viewData.setData(
                node.getId(),
                node.getParentId(),
                node.getName(),
                node.getIp(),
                node.getPort());

        view.showNode(viewData);
    }

    private void updateNodeInfo(TreeNode node) {
        selectedNode = node;

        viewData.setData(
                node.getId(),
                node.getParentId(),
                node.getName(),
                node.getIp(),
                node.getPort());

        view.showNode(viewData);
    }

    public void clear() {
        selectedNode = null;
        viewData.clear();
        view.clear();
    }

    public void startEdit() {
        if (selectedNode == null) {
            return;
        }

        view.showEditMode(viewData);
    }

    public void cancelEdit() {
        if (selectedNode == null) {
            return;
        }

        view.showNode(viewData);
    }

    public void saveNode(String name, String ip, String port) {
        final TreeNode node = selectedNode;
        if (node == null) {
            return;
        }

        if (name.trim().isEmpty() || ip.trim().isEmpty() || port.trim().isEmpty()) {
            view.showError("Одно из полей было пустое!");
            return;
        }

        Integer portInt;

        try {
            portInt = Integer.valueOf(port);
        } catch (NumberFormatException e) {
            view.showError("Порт должен быть числом!");
            return;
        }

        node.setName(name);
        node.setIp(ip);
        node.setPort(portInt);

        service.updateNode(node, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                GWT.log("Узел успешно обновлён");
                updateNodeInfo(node);
                AppEventBus.get().fireEvent(new NodeUpdatedEvent(node));//рассылка обновления
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка обновления узла", caught);
            }
        });
    }

    @Override
    public void onNodeSelected(NodeSelectedEvent event) {
        TreeNode node = event.getNode();
        selectNode(node);
    }
    
    @Override
    public void editNode(EditNodeEvent event) {
        startEdit();
    }
    
    @Override
    public void clearSelection(ClearSelectionEvent event) {
        clear();
    }
}
