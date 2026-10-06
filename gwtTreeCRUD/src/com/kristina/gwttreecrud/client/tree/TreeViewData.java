package com.kristina.gwttreecrud.client.tree;

public class TreeViewData implements Comparable<TreeViewData> {
    private Long id;
    private Long parentId;
    private String name;

    private boolean hasChildren;

    public TreeViewData(Long id, Long parentId, String name, boolean hasChildren) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.hasChildren = hasChildren;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
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

    @Override
    public int compareTo(TreeViewData other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}