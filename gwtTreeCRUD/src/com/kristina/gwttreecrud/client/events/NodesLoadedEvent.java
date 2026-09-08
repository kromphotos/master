package com.kristina.gwttreecrud.client.events;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodesLoadedEvent extends GwtEvent<NodesLoadedEventHandler> {
    private final List<TreeNode> nodes;
    public static final Type<NodesLoadedEventHandler> TYPE = new Type<NodesLoadedEventHandler>();
    
    public NodesLoadedEvent(List<TreeNode> nodes) {
        this.nodes = nodes;
    }
    
    public List<TreeNode> getNodes(){
        return nodes;
    }
    
    @Override
    public Type<NodesLoadedEventHandler> getAssociatedType() {
        return TYPE;
    }
    
    @Override
    protected void dispatch(NodesLoadedEventHandler handler) {
        handler.onNodesLoaded(this);
    }
}
