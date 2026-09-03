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

    private TreeNode selectedNode;

    public NodeInfoPresenter(NodeInfoView view, NodeInfoViewData data) {
        this.view = view;
        this.viewData = data;
    }

    public void setController(TreeController controller) {
        this.controller = controller;
    }

    //было: showNode
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

        view.showNode(viewData);// отображение передаем объект даты! не общий
    }

    /**
     * 
     */
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
                view.showNode(viewData);
                controller.refresh();
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка обновления узла", caught);
            }
        });
    }


}
