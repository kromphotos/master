package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface NodesLoadedEventHandler extends EventHandler{
    void onNodesLoaded(NodesLoadedEvent event);
}
