package com.kristina.gwttreecrud.client.tree;

import java.util.List;

import com.kristina.gwttreecrud.client.nodeactions.NodeActionsPresenter;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoPresenter;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreePresenter {
    private NodeActionsPresenter nodeActionsPresenter;
    private TreeView view;
    private TreeViewData viewData;
    private NodeInfoPresenter nodeInfoPresenter;

    public TreePresenter(TreeView view, TreeViewData viewData, NodeInfoPresenter nodeInfoPresenter, NodeActionsPresenter nodeActionsPresenter) {
        this.view = view;
        this.viewData = viewData;
        this.nodeInfoPresenter = nodeInfoPresenter;
        this.nodeActionsPresenter = nodeActionsPresenter;
    } 
    public void refreshNodes(List<TreeNode> nodes) {
        viewData.setNodes(nodes);
        view.showTree(viewData.getNodes(), viewData.getExpandedNodeIds(), viewData.getSelectedNodeId());
        
        Integer selectedNodeId = viewData.getSelectedNodeId();
        if(selectedNodeId != null) {
            TreeNode selectedNode = findNodeById(selectedNodeId);
            if (selectedNode != null) {
                nodeInfoPresenter.showNode(selectedNode);
            }   else {
                nodeInfoPresenter.clear();
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
        view.showTree(viewData.getNodes(), viewData.getExpandedNodeIds(), viewData.getSelectedNodeId());
    }
    
    public void selectNode(Integer nodeId) {
        viewData.setSelectedNodeId(nodeId);
        nodeActionsPresenter.selectNode(nodeId);
        TreeNode selectedNode = findNodeById(nodeId);
        if (selectedNode != null) {
            nodeInfoPresenter.showNode(selectedNode);
        }   else {
            nodeInfoPresenter.clear();
        }
        refreshTree();
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
        nodeInfoPresenter.clear();
    }
}