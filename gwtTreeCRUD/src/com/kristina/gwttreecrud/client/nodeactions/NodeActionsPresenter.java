package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddView;
import com.kristina.gwttreecrud.client.nodeedit.NodeEditView;
import com.kristina.gwttreecrud.client.tree.TreeViewData;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeActionsPresenter {
    private GwtServiceAsync service = GWT.create(GwtService.class);
    private NodeActionsView view;
    private TreeViewData viewData;
    private NodeEditView editView;
    private NodeAddView addView;
    private TreeController controller;
    
    public NodeActionsPresenter(NodeActionsView view, TreeViewData viewData, NodeEditView editView, NodeAddView addView) {
        this.view = view;
        this.viewData = viewData;
        this.editView = editView;
        this.addView = addView;
    }
    public void setController(TreeController controller) {
        this.controller = controller;
    }
    public void editNode() {
        Integer selectedNodeId = viewData.getSelectedNodeId();
        if (selectedNodeId == null) {
            return;
        }
        service.findById(selectedNodeId, new AsyncCallback<TreeNode>() {
            @Override
            public void onSuccess(TreeNode node) {
                editView.showEditCard(node);
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки узла!", caught);
            }
        });
    }
    
    public void addChildNode() {
        Integer selectedNodeId = viewData.getSelectedNodeId();
        if (selectedNodeId == null) {
            return;
        }
        addView.showAddCard(selectedNodeId);
    }
    
    /**
     * 
     */
    public void deleteNode() {
        final Integer selectedNodeId = viewData.getSelectedNodeId();
        if (selectedNodeId == null) {
            return;
        }
        service.findById(selectedNodeId, new AsyncCallback<TreeNode>(){
            /**
             *
             */
            @Override
            public void onSuccess(TreeNode node) {
                if (node == null) {
                    return;
                }
                if (node.getParentId() == null) {
                    view.showMessage("Корневую ноду удалять запрещено!");
                    return;
                }
                service.deleteById(selectedNodeId, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        view.showMessage("Удаление выполнено успешно!");
                        controller.clearSelection();
                        controller.refresh();
                    }
                    @Override
                    public void onFailure(Throwable caught) {

                        GWT.log("Ошибка удаления узла", caught);
                    }
                });
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки узла перед удалением", caught);
            }
        });
    }
    
    public void selectNode(Integer nodeId) {
        view.showSelectedNodeId(nodeId);
    }
    
    public void clearSelection() {
        view.showSelectedNodeId(null);
    }

}
