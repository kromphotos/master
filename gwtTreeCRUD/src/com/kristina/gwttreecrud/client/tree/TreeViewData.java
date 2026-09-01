package com.kristina.gwttreecrud.client.tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kristina.gwttreecrud.shared.TreeNode;

public class TreeViewData {
    private List<TreeNode> nodes;

    private Set<Integer> expandedNodeIds;

    private Integer selectedNodeId;

    public TreeViewData() {
        expandedNodeIds = new HashSet<Integer>();
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public Set<Integer> getExpandedNodeIds() {
        return expandedNodeIds;
    }

    public Integer getSelectedNodeId() {
        return selectedNodeId;
    }

    public void setSelectedNodeId(Integer selectedNodeId) {
        this.selectedNodeId = selectedNodeId;
    }

    public boolean isExpanded(Integer nodeId) {
        return expandedNodeIds.contains(nodeId);
    }

    public void expandId(Integer nodeId) {
        expandedNodeIds.add(nodeId);
    }

    public void removeId(Integer nodeId) {
        expandedNodeIds.remove(nodeId);
    }
    
    public void clearSelectedNode() {
        selectedNodeId = null;
    }
}