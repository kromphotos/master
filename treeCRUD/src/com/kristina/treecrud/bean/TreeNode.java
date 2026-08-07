package com.kristina.treecrud.bean;

public class TreeNode {
    private Integer id;
    private Integer parentId;
    private String name;
    private String ip;
    private Integer port;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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
    
    @Override
    public String toString() {
        return "id=" + id + ", parentId=" + parentId + ", name=" + name + ", ip=" + ip + ", port=" + port + ";";
    }
}
