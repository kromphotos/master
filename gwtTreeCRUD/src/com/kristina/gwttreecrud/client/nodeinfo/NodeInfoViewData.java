package com.kristina.gwttreecrud.client.nodeinfo;

public class NodeInfoViewData {
    private Integer id;
    private Integer parentId;
    private String name;
    private String ip;
    private Integer port;

    public NodeInfoViewData() {
    }

    public NodeInfoViewData(Integer id, Integer parentId, String name, String ip, Integer port) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    
    //TODO(by Tutor)
      //  это тут зачем? почему просто не пересоздать вью дату через new
    public void setData(Integer id, Integer parentId, String name, String ip, Integer port) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }  
    
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

    
  //TODO(by Tutor)
    //  это тут зачем? почему просто не удалить укзаатель на вью дату там, где это нужно
    public void clear() {
        id = null;
        parentId = null;
        name = null;
        ip = null;
        port = null;
    }
    
    @Override
    public String toString() {
        return "TreeNode [id=" + id + ", parentId=" + parentId + ", name=" + name + ", ip=" + ip + ", port=" + port + "]";
    }
}
