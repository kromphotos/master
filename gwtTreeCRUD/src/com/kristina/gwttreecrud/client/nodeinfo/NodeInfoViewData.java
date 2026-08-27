package com.kristina.gwttreecrud.client.nodeinfo;

import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeInfoViewData {
    private TreeNode selectedNode;
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    public void clear() {
        selectedNode = null;
    }
}
