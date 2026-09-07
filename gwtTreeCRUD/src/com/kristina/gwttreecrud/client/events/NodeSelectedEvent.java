package com.kristina.gwttreecrud.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.kristina.gwttreecrud.shared.TreeNode;


//Наш NodeSelectedEvent является событием GWT.
//Это событие будет обрабатываться через NodeSelectedEvent.Handler
public class NodeSelectedEvent extends GwtEvent<NodeSelectedEventHandler>{
    //идентификатор типа события
    public static final Type<NodeSelectedEventHandler> TYPE = new Type<NodeSelectedEventHandler>();
    
    private final TreeNode node;
    
    public NodeSelectedEvent(TreeNode node) {
        this.node = node;
    }
    
    public TreeNode getNode() {
        return node;
    }
    
    //Для этого объекта NodeSelectedEvent используй тип NodeSelectedEvent.TYPE
    @Override
    public Type<NodeSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }
    
    //Что сделать, когда найден Handler?
    @Override
    protected void dispatch(NodeSelectedEventHandler handler) {
        handler.onNodeSelected(this);
    }
    //Вызвать у Handler метод onNodeSelected() и передать ему это событие

}
