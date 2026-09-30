package com.kristina.gwttreecrud.client.nodeadd;

public interface NodeAddInterface {
    void showAddCard(Integer parentIdValue);

    void showAddRootCard();

    void showError(String message);

    void hideAddCard();

    interface NodeAddViewHandler {
        void onSaveNode(String parentiD, String nodeName, String nodeIp, String port);

        void onCancel();
    };

    void setHandler(NodeAddViewHandler handler);
}
