package com.kristina.gwttreecrud.client.nodeadd;

public interface NodeAddInterface {
    void showAddCard(Long parentIdValue);

    void showAddRootCard();

    void showError(String message);

    void hideAddCard();

    interface NodeAddViewHandler {
        void onSaveNode(String parentId, String nodeName, String nodeIp, String port);

        void onCancel();
    };

    void setHandler(NodeAddViewHandler handler);
}
