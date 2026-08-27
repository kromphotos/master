package com.kristina.gwttreecrud.client.nodeinfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeInfoPresenter {
    private GwtServiceAsync service = GWT.create(GwtService.class);
    private NodeInfoView view;
    private NodeInfoViewData viewData;
    private TreeController controller;

    public NodeInfoPresenter(NodeInfoView view, NodeInfoViewData data) {
        this.view = view;
        this.viewData = data;
    }
    
    public void setController(TreeController controller) {
        this.controller = controller;
    }

    public void showNode(TreeNode node) {
        viewData.setSelectedNode(node);
        view.showNode(node);
    }
    

    public void clear() {
        viewData.clear();
        view.clear();
    }

    public void startEdit() {
        TreeNode node = viewData.getSelectedNode();

        if (node == null) {
            return;
        }

        view.showEditMode(node);
    }

    public void cancelEdit() {
        TreeNode node = viewData.getSelectedNode();

        if (node == null) {
            return;
        }

        view.showNode(node);
    }

    public void saveNode() {
        final TreeNode node = viewData.getSelectedNode();
        if (node == null) {
            return;
        }
        //значения из текст бокс(новые)
        String name = view.getNodeName();
        String ip = view.getNodeIp();
        String port = view.getNodePort();

        if (name.trim().isEmpty() || ip.trim().isEmpty() || port.trim().isEmpty()) {
            return;
        }

        Integer portInt;

        try {
            portInt = Integer.valueOf(port);
        } catch (NumberFormatException e) {
            return;
        }

        node.setName(name);
        node.setIp(ip);
        node.setPort(portInt);

        service.updateNode(node, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                GWT.log("Узел успешно обновлён");
                view.showNode(node);
                controller.refresh();
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка обновления узла", caught);
            }
        });
    }
}
