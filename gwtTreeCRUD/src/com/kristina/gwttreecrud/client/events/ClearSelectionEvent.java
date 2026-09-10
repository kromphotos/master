package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class ClearSelectionEvent extends GwtEvent<ClearSelectionEventHandler> {
    public static final Type<ClearSelectionEventHandler> TYPE = new Type<ClearSelectionEventHandler>();

    @Override
    public Type<ClearSelectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ClearSelectionEventHandler handler) {
        handler.clearSelection(this);
    }
}
