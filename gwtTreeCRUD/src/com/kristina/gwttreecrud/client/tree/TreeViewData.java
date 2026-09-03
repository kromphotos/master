package com.kristina.gwttreecrud.client.tree;

public class TreeViewData {
    private Integer id;
    private Integer parentId;
    private String name;
    
    public TreeViewData(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    /*
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
    */
}