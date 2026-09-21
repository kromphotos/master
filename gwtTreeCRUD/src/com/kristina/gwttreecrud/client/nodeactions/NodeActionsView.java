package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class NodeActionsView extends Composite implements NodeActionsInterface {
    
    interface NodeActionsViewHandler {
        void onEdit();
        void onChild();
        void onDelete();
        void onAddRoot();
    }
    
    private NodeActionsViewHandler handler;
    public void setHandler(NodeActionsViewHandler handler) {
        this.handler = handler;
    }
    
    //private NodeActionsPresenter presenter;
    private HorizontalPanel panel;
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;

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
                if (handler!=null)
                {
                    handler.onEdit();
                }
            }
        });
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler!=null)
                {
                    handler.onChild();
                }
            }
        });
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler!=null)
                {
                    handler.onDelete();
                }
            }
        });
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler!=null)
                {
                    handler.onAddRoot();
                }
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
    /*
    public void setPresenter(NodeActionsPresenter presenter) {
        this.presenter = presenter;
    }
    */
    @Override
    public void showMessage(String message) {
        Window.alert(message);
    }
    
    @Override
    public void setNodeSelected(boolean selected) {
        addChildButton.setEnabled(selected);
        editButton.setEnabled(selected);
        deleteButton.setEnabled(selected);
    }
}
