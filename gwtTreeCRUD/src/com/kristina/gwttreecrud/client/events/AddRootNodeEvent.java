package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AddRootNodeEvent extends GwtEvent<AddRootNodeEventHandler> {
    public static final Type<AddRootNodeEventHandler> TYPE = new Type<AddRootNodeEventHandler>();
    
    @Override
    public Type<AddRootNodeEventHandler> getAssociatedType() {
        return TYPE;
    }
    
    @Override
    protected void dispatch(AddRootNodeEventHandler handler) {
        handler.addRootNode(this);
    }

}
