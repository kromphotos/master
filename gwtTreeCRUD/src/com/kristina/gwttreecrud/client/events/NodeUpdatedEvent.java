package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeUpdatedEvent extends GwtEvent<NodeUpdatedEventHandler>{
    public static final Type<NodeUpdatedEventHandler> TYPE = new Type<NodeUpdatedEventHandler>();

    private TreeNode node;

    public NodeUpdatedEvent(TreeNode node) {
        this.node = node;
    }

    public TreeNode getNode() {
        return node;
    }

    @Override
    public Type<NodeUpdatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(NodeUpdatedEventHandler handler) {
        handler.onNodeUpdated(this);
    }
}
