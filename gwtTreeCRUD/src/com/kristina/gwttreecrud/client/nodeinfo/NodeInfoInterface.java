package com.kristina.gwttreecrud.client.nodeinfo;

public interface NodeInfoInterface {
    void showNode(NodeInfoViewData data);
    void showEditMode(NodeInfoViewData data);
    void clear();
    void showError(String message);
}
