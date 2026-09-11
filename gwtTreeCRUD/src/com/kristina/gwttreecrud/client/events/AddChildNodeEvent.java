package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AddChildNodeEvent extends GwtEvent<AddChildNodeEventHandler> {
    public static final Type<AddChildNodeEventHandler> TYPE = new Type<AddChildNodeEventHandler>();
    private final Integer parentId;
    
    public AddChildNodeEvent(Integer parentId) {
        this.parentId = parentId;
    }
    
    public Integer getParentId() {
        return parentId;
    }
    
    @Override
    public Type<AddChildNodeEventHandler> getAssociatedType() {
        return TYPE;
    }
    
    @Override
    protected void dispatch(AddChildNodeEventHandler handler) {
        handler.addChildNode(this);
    }

}
