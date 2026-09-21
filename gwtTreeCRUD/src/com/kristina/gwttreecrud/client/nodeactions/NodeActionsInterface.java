package com.kristina.gwttreecrud.client.nodeactions;

public interface NodeActionsInterface {
    void showMessage(String message);
    void setNodeSelected(boolean selected);
    interface NodeActionsViewHandler {
        void onEdit();
        void onChild();
        void onDelete();
        void onAddRoot();
    };
    void setHandler(NodeActionsViewHandler handler);
}
