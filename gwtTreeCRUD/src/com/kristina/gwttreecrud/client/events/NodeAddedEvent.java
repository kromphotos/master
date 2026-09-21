package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeAddedEvent extends GwtEvent<NodeAddedEventHandler> {
    public static final Type<NodeAddedEventHandler> TYPE = new Type<NodeAddedEventHandler>();
    private final TreeNode node;

    public NodeAddedEvent(TreeNode node) {
        this.node = node;
    }

    public TreeNode getNode() {
        return node;
    }

    @Override
    public Type<NodeAddedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(NodeAddedEventHandler handler) {
        handler.nodeAdded(this);
    }
}
