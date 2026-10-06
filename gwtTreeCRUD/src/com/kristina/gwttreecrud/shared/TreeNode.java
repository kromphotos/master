package com.kristina.gwttreecrud.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TreeNode implements IsSerializable {
    private Long id;
    private Long parentId;
    private String name;
    private String ip;
    private Integer port;
    private boolean hasChildren;

    List<TreeNode> children;
    
    public TreeNode() {
    }

    public TreeNode(Long id, Long parentId, String name, String ip, Integer port) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    
    public List<TreeNode> getChildren(){
        return children;
    }
    
    public void setChildren(List<TreeNode> children){
        this.children = children;
    }
    
    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    @Override
    public String toString() {
        return "TreeNode [id=" + id + ", parentId=" + parentId + ", name=" + name + ", ip=" + ip + ", port=" + port + ", hasChildren=" + hasChildren
                + ", children=" + children + "]";
    }
}
