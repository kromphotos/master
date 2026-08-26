package com.kristina.gwttreecrud.client.tree;

import java.util.List;

//import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.kristina.gwttreecrud.client.GwtService;
//import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsPresenter;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreePresenter {
    private NodeActionsPresenter nodeActionsPresenter;
    private TreeView view;
    private TreeViewData viewData;
    private NodeInfoView nodeInfoView;

    //private GwtServiceAsync service = GWT.create(GwtService.class);

    public TreePresenter(TreeView view, TreeViewData viewData, NodeInfoView nodeInfoView, NodeActionsPresenter nodeActionsPresenter) {
        this.view = view;
        this.viewData = viewData;
        this.nodeInfoView = nodeInfoView;
        this.nodeActionsPresenter = nodeActionsPresenter;
    }
    /*
    public void loadNodes() {
        AsyncCallback<List<TreeNode>> callback = new AsyncCallback<List<TreeNode>>() {
            
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                viewData.setNodes(nodes);
                view.showTree(viewData.getNodes(), viewData.getExpandedNodeIds());
            }
            
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки TreeNode", caught);
            }
        };
        service.getAllNodes(callback);
    }
    */
    
    public void refreshNodes(List<TreeNode> nodes) {
        viewData.setNodes(nodes);
        view.showTree(viewData.getNodes(), viewData.getExpandedNodeIds());
        
        Integer selectedNodeId = viewData.getSelectedNodeId();
        if(selectedNodeId != null) {
            TreeNode selectedNode = findNodeById(selectedNodeId);
            if (selectedNode != null) {
                nodeInfoView.showNode(selectedNode);
            }   else {
                nodeInfoView.clear();
            }
        }
    }

    public void expandNode(Integer nodeId) {
        viewData.expandId(nodeId);
        refreshTree();
    }

    public void collapseNode(Integer nodeId) {
        viewData.removeId(nodeId);
        refreshTree();
    }

    private void refreshTree() {
        view.showTree(viewData.getNodes(), viewData.getExpandedNodeIds());
    }
    
    public void selectNode(Integer nodeId) {
        viewData.setSelectedNodeId(nodeId);
        TreeNode selectedNode = findNodeById(nodeId);
        if (selectedNode != null) {
            nodeInfoView.showNode(selectedNode);
        }   else {
            nodeInfoView.clear();
        }
        nodeActionsPresenter.selectNode(nodeId);
    }
    
    private TreeNode findNodeById(Integer nodeId) {
        for(TreeNode node : viewData.getNodes() ) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        return null;
    }
    
    public void clearSelection() {
        viewData.clearSelectedNode();
        nodeInfoView.clear();
    }
}