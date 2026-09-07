package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface NodeSelectedEventHandler extends EventHandler {
    //Любой объект, который хочет слушать NodeSelectedEvent,
    //должен реализовать Handler
    void onNodeSelected(NodeSelectedEvent event);
}
