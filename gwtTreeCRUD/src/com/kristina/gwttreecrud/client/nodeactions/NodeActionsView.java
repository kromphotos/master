package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class NodeActionsView extends Composite{
    private NodeActionsPresenter presenter;
    public void setPresenter(NodeActionsPresenter presenter) {
        this.presenter = presenter;
    }
    
    private HorizontalPanel panel;
    
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;
    
    public void showMessage(String message) {
        Window.alert(message);
    }
    
    public void setNodeSelected(boolean selected) {
        addChildButton.setEnabled(selected);
        editButton.setEnabled(selected);
        deleteButton.setEnabled(selected);
    }
    
    public NodeActionsView() {
        panel = new HorizontalPanel();
        
        addRootButton = new Button("Add root node");
        addChildButton = new Button("Add child");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        
        addChildButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.editNode();
            }
        });
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.addChildNode();
            }
        });
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.deleteNode();
            }
        });
        
        addRootButton.getElement().getStyle().setProperty("marginRight", "10px");
        addChildButton.getElement().getStyle().setProperty("marginRight", "10px");
        editButton.getElement().getStyle().setProperty("marginRight", "10px");
        
        
        panel.add(addRootButton);
        panel.add(addChildButton);
        panel.add(editButton);
        panel.add(deleteButton);

        
        initWidget(panel);
    }


}
