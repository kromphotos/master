package com.kristina.gwttreecrud.client.nodeedit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeEditPresenter {
    private TreeController controller;
    private NodeEditView view;
    private GwtServiceAsync service = GWT.create(GwtService.class);
    public NodeEditPresenter(NodeEditView view) {
        this.view = view;
    }
    
    public void setController(TreeController controller) {
        this.controller = controller;
    }
    
    public void saveNode() {
        TreeNode node = view.getCurrentNode();
        if (node == null) {
            return;
        }
        
        String name = view.getNodeName();
        String ip = view.getNodeIp();
        String port = view.getNodePort();
        
        if (name.trim().isEmpty() || ip.trim().isEmpty() || port.trim().isEmpty())
        {
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
                view.hideEditCard();
                controller.refresh();
            }
            @Override
            public void onFailure(Throwable caught) {

                GWT.log("Ошибка обновления узла", caught);
            }
        });
    }
}
