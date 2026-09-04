package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.kristina.gwttreecrud.shared.TreeNode;


//Наш NodeSelectedEvent является событием GWT.
//Это событие будет обрабатываться через NodeSelectedEvent.Handler
public class NodeSelectedEvent extends GwtEvent<NodeSelectedEvent.Handler>{
    //идентификатор типа события
    public static final Type<Handler> TYPE = new Type<Handler>();
    
    private final TreeNode node;
    
    public NodeSelectedEvent(TreeNode node) {
        this.node = node;
    }
    
    public TreeNode getNode() {
        return node;
    }
    
    //Любой объект, который хочет слушать NodeSelectedEvent,
    //должен реализовать Handler
    public interface Handler extends EventHandler {
        void onNodeSelected(NodeSelectedEvent event);
    }
    //Для этого объекта NodeSelectedEvent используй тип NodeSelectedEvent.TYPE
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }
    
    @Override
    protected void dispatch(Handler handler) {
        handler.onNodeSelected(this);
    }

}
