package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class NodeActionsView extends Composite implements NodeActionsInterface {   
    private NodeActionsViewHandler handler;
    
    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    @Override
    public void setHandler(NodeActionsViewHandler handler) {
        this.handler = handler;
    }
    
    private HorizontalPanel panel;
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;

    public NodeActionsView() {
        panel = new HorizontalPanel();
        panel.setSpacing(10);
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
                //TODO(by Tutor)
                // что за новые строки для открывающейся скобки ифа?
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

        //addRootButton.addStyleName("node-actions-button");
        //addChildButton.addStyleName("node-actions-button");
        //editButton.addStyleName("node-actions-button");
        //deleteButton.addStyleName("node-actions-button");

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
    
    //TODO(by Tutor)
    // А это зачем тут? прямой нужды в отображении сообщения конкретной въюхой нет. 
    // Доступ к Window.alert есть и у презентора
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
