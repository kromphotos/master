package com.kristina.gwttreecrud.client.tree;

public class TreeViewData {
    private Integer id;
    private Integer parentId;
    private String name;
    
    private boolean hasChildren;
    
    public TreeViewData(Integer id, Integer parentId, String name, boolean hasChildren) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.hasChildren = hasChildren;
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
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isHasChildren() {
        return hasChildren;
    }
}