package com.kristina.gwttreecrud.client.nodeadd;

public interface NodeAddInterface {
    void showAddCard(Integer parentIdValue);
    void showAddRootCard();
    void showError(String message);
    void hideAddCard();
    interface NodeAddViewHandler {
        void onSaveNode();
        void onCancel();
    };
    void setHandler(NodeAddViewHandler handler);
}
