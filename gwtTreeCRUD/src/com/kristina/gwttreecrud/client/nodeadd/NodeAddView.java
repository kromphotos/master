package com.kristina.gwttreecrud.client.nodeadd;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NodeAddView extends DialogBox implements NodeAddInterface {
    private static final int COLUMN_OF_LABEL = 0;
    private static final int COLUMN_OF_FIELD = 1;

    private static final int ROW_OF_PARENT_ID_IN_ADD_CHILD = 0;
    private static final int ROW_OF_NAME_IN_ADD_CHILD = 1;
    private static final int ROW_OF_IP_IN_ADD_CHILD = 2;
    private static final int ROW_OF_PORT_IN_ADD_CHILD = 3;

    private static final int ROW_OF_NAME_IN_ADD_ROOT = 0;
    private static final int ROW_OF_IP_IN_ADD_ROOT = 1;
    private static final int ROW_OF_PORT_IN_ADD_ROOT = 2;

    private static final int MAX_LENGTH_OF_NODE_PORT = 5;
    private static final int MAX_LENGTH_OF_NODE_IP = 15;

    private static final String NODE_PORT_FIELD_NAME = "Node's port:";
    private static final String NODE_IP_FIELD_NAME = "Node's Ip:";
    private static final String NODE_NAME_FIELD_NAME = "Node name:";
    private static final String PARENT_ID_FIELD_NAME = "Parent's id:";

    private static final String EMPTY_FIELD = "";

    private static final String CANCEL_BUTTON = "Cancel";
    private static final String SAVE_BUTTON = "Save";

    private static final String ADD_WINDOW_NAME = "Add node:";

    private static final String STYLE_ADD_ERROR = "node-add-error";

    private NodeAddViewHandler handler;
    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    private FlexTable formTable;

    private TextBox parentId;
    private TextBox nodeName;
    private TextBox nodeIp;
    private TextBox nodePort;

    private Label errorLabel;

    private Button saveButton;
    private Button cancelButton;

    public NodeAddView() {
        setText(ADD_WINDOW_NAME);
        setAnimationEnabled(true);
        setGlassEnabled(true);

        VerticalPanel addPanel = new VerticalPanel();
        HorizontalPanel buttonsPanel = new HorizontalPanel();

        formTable = new FlexTable();
        parentId = new TextBox();
        nodeName = new TextBox();
        nodeIp = new TextBox();
        nodePort = new TextBox();
        errorLabel = new Label();
        saveButton = new Button(SAVE_BUTTON);
        cancelButton = new Button(CANCEL_BUTTON);

        errorLabel.setStyleName(STYLE_ADD_ERROR);

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //TODO(by Tutor)
                // опять скобки с новой строки
                if (handler != null) {
                    handler.onSaveNode(parentId.getText(), nodeName.getText(), nodeIp.getText(), nodePort.getText());
                }
            }
        });

        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler != null) {
                    handler.onCancel();
                }
            }
        });

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        addPanel.add(formTable);
        addPanel.add(errorLabel);
        addPanel.add(buttonsPanel);

        setWidget(addPanel);
        hide();
    }

    @Override
    public void setHandler(NodeAddViewHandler handler) {
        this.handler = handler;
    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void showAddCard(Integer parentIdValue) {
        parentId.setText(String.valueOf(parentIdValue));
        nodeName.setText(EMPTY_FIELD);
        nodeIp.setText(EMPTY_FIELD);
        nodePort.setText(EMPTY_FIELD);
        errorLabel.setText(EMPTY_FIELD);

        formTable.clear();

        //TODO(by Tutor)
        // не надо так делать. пиши конкретный номер строки в самом методе setWidget
        parentId.setReadOnly(true);

        formTable.setWidget(ROW_OF_PARENT_ID_IN_ADD_CHILD, COLUMN_OF_LABEL, new Label(PARENT_ID_FIELD_NAME));
        formTable.setWidget(ROW_OF_PARENT_ID_IN_ADD_CHILD, COLUMN_OF_FIELD, parentId);

        formTable.setWidget(ROW_OF_NAME_IN_ADD_CHILD, COLUMN_OF_LABEL, new Label(NODE_NAME_FIELD_NAME));
        formTable.setWidget(ROW_OF_NAME_IN_ADD_CHILD, COLUMN_OF_FIELD, nodeName);

        formTable.setWidget(ROW_OF_IP_IN_ADD_CHILD, COLUMN_OF_LABEL, new Label(NODE_IP_FIELD_NAME));
        formTable.setWidget(ROW_OF_IP_IN_ADD_CHILD, COLUMN_OF_FIELD, nodeIp);

        formTable.setWidget(ROW_OF_PORT_IN_ADD_CHILD, COLUMN_OF_LABEL, new Label(NODE_PORT_FIELD_NAME));
        formTable.setWidget(ROW_OF_PORT_IN_ADD_CHILD, COLUMN_OF_FIELD, nodePort);

        nodeIp.setMaxLength(MAX_LENGTH_OF_NODE_IP);
        //TODO(by Tutor)
        // порт может быть 5-ти значным
        nodePort.setMaxLength(MAX_LENGTH_OF_NODE_PORT);

        center();
        show();
    }

    @Override
    public void showAddRootCard() {
        nodeName.setText(EMPTY_FIELD);
        nodeIp.setText(EMPTY_FIELD);
        nodePort.setText(EMPTY_FIELD);
        errorLabel.setText(EMPTY_FIELD);

        formTable.clear();

        formTable.setWidget(ROW_OF_NAME_IN_ADD_ROOT, COLUMN_OF_LABEL, new Label(NODE_NAME_FIELD_NAME));
        formTable.setWidget(ROW_OF_NAME_IN_ADD_ROOT, COLUMN_OF_FIELD, nodeName);

        formTable.setWidget(ROW_OF_IP_IN_ADD_ROOT, COLUMN_OF_LABEL, new Label(NODE_IP_FIELD_NAME));
        formTable.setWidget(ROW_OF_IP_IN_ADD_ROOT, COLUMN_OF_FIELD, nodeIp);

        formTable.setWidget(ROW_OF_PORT_IN_ADD_ROOT, COLUMN_OF_LABEL, new Label(NODE_PORT_FIELD_NAME));
        formTable.setWidget(ROW_OF_PORT_IN_ADD_ROOT, COLUMN_OF_FIELD, nodePort);

        nodeIp.setMaxLength(MAX_LENGTH_OF_NODE_IP);
        nodePort.setMaxLength(MAX_LENGTH_OF_NODE_PORT);

        center();
        show();
    }
    //TODO(by Tutor)
    // ликвидировать! всех четверых под трибунал!

    @Override
    public void hideAddCard() {
        hide();
    }
}
