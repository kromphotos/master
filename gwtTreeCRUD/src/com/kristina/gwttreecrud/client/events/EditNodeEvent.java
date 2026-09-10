package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class EditNodeEvent extends GwtEvent<EditNodeEventHandler> {
public static final Type<EditNodeEventHandler> TYPE = new Type<EditNodeEventHandler>();
    
    @Override
    public Type<EditNodeEventHandler> getAssociatedType() {
        return TYPE;
    }
    
    @Override
    protected void dispatch(EditNodeEventHandler handler) {
        handler.editNode(this);
    }


}
