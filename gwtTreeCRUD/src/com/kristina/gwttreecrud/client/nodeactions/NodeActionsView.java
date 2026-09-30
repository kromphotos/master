package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class NodeActionsView extends Composite implements NodeActionsInterface {
    private static final int SPACE_BETWEEN_BUTTONS = 10;

    private static final String BUTTON_DELETE = "Delete";
    private static final String BUTTON_EDIT = "Edit";
    private static final String BUTTON_ADD_CHILD = "Add child";
    private static final String BUTTON_ADD_ROOT_NODE = "Add root node";

    private NodeActionsViewHandler handler;
    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    private HorizontalPanel panel;
    
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;

    public NodeActionsView() {
        panel = new HorizontalPanel();

        addRootButton = new Button(BUTTON_ADD_ROOT_NODE);
        addChildButton = new Button(BUTTON_ADD_CHILD);
        editButton = new Button(BUTTON_EDIT);
        deleteButton = new Button(BUTTON_DELETE);

        panel.setSpacing(SPACE_BETWEEN_BUTTONS);

        addChildButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //TODO(by Tutor)
                // что за новые строки для открывающейся скобки ифа?
                if (handler != null) {
                    handler.onEdit();
                }
            }
        });
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler != null) {
                    handler.onChild();
                }
            }
        });
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler != null) {
                    handler.onDelete();
                }
            }
        });
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler != null) {
                    handler.onAddRoot();
                }
            }
        });

        panel.add(addRootButton);
        panel.add(addChildButton);
        panel.add(editButton);
        panel.add(deleteButton);

        initWidget(panel);
    }

    @Override
    public void setHandler(NodeActionsViewHandler handler) {
        this.handler = handler;
    }
    //TODO(by Tutor)
    // А это зачем тут? прямой нужды в отображении сообщения конкретной въюхой нет. 
    // Доступ к Window.alert есть и у презентора

    @Override
    public void setNodeSelected(boolean selected) {
        addChildButton.setEnabled(selected);
        editButton.setEnabled(selected);
        deleteButton.setEnabled(selected);
    }
}
