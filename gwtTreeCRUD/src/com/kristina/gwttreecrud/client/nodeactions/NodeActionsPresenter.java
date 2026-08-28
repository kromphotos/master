package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddPresenter;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoPresenter;
import com.kristina.gwttreecrud.client.tree.TreeViewData;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeActionsPresenter {
    private GwtServiceAsync service = GWT.create(GwtService.class);
    private NodeActionsView view;
    private TreeViewData viewData;
    //private NodeAddView addView;
    private NodeAddPresenter addPresenter;
    private TreeController controller;
    private NodeInfoPresenter nodeInfoPresenter;

    public NodeActionsPresenter(NodeActionsView view, TreeViewData viewData, NodeInfoPresenter nodeInfoPresenter, NodeAddPresenter addPresenter) {
        this.view = view;
        this.viewData = viewData;
        this.nodeInfoPresenter = nodeInfoPresenter;
        this.addPresenter = addPresenter;
    }

    public void setController(TreeController controller) {
        this.controller = controller;
    }

    public void editNode() {
        if (viewData.getSelectedNodeId() == null) {
            return;
        }
        nodeInfoPresenter.startEdit();
        ;
    }

    public void addChildNode() {
        Integer selectedNodeId = viewData.getSelectedNodeId();
        if (selectedNodeId == null) {
            return;
        }
        addPresenter.startAddChild(selectedNodeId);
    }
    
    public void addRootNode() {
        addPresenter.startAddingRoot();
    }

    public void selectNode(Integer nodeId) {
        viewData.setSelectedNodeId(nodeId);
        if (nodeId != null) {
            view.setNodeSelected(true);
        } else {
            view.setNodeSelected(false);
        }
    }

    public void deleteNode() {
        final Integer selectedNodeId = viewData.getSelectedNodeId();
        if (selectedNodeId == null) {
            return;
        }
        service.findById(selectedNodeId, new AsyncCallback<TreeNode>() {
            
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
    public void clearSelection() {
        view.setNodeSelected(false);
    }

}
