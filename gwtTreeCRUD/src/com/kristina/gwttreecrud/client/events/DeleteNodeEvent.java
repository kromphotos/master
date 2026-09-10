package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteNodeEvent extends GwtEvent<DeleteNodeEventHandler> {
    public static final Type<DeleteNodeEventHandler> TYPE = new Type<DeleteNodeEventHandler>();
    private final Integer nodeId;
    
    public DeleteNodeEvent(Integer nodeId) {
        this.nodeId = nodeId;
    }
    
    public Integer getNodeId() {
        return nodeId;
    }
    
    public Type<DeleteNodeEventHandler> getAssociatedType() {
        return TYPE;
    }
    
    //Что сделать, когда найден Handler?
    @Override
    protected void dispatch(DeleteNodeEventHandler handler) {
        handler.deleteNode(this);
    }

}
