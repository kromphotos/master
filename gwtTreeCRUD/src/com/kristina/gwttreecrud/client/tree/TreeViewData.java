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
}