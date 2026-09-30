package com.kristina.gwttreecrud.client.nodeinfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NodeInfoView extends Composite implements NodeInfoInterface {
    private static final int AMOUNT_OF_COLUMNS = 2;
    private static final int AMOUNT_OF_ROWS = 5;

    private static final int COLUMN_OF_LABEL = 0;
    private static final int COLUMN_OF_VALUE = 1;

    private static final int ROW_OF_ID = 0;
    private static final int ROW_OF_PARENT_ID = 1;
    private static final int ROW_OF_NAME = 2;
    private static final int ROW_OF_IP = 3;
    private static final int ROW_OF_PORT = 4;

    private static final String PORT_FIELD_NAME = "Port";
    private static final String IP_FIELD_NAME = "IP";
    private static final String NAME_FIELD_NAME = "Name";
    private static final String PARENT_ID_FIELD_NAME = "Parent ID";
    private static final String ID_FIELD_NAME = "ID";

    private static final String EMPTY_FIELD = "";

    private static final String BUTTON_CANCEL = "Cancel";
    private static final String BUTTON_SAVE = "Save";

    private static final int MAX_LENGTH_OF_NODE_PORT = 5;
    private static final int MAX_LENGTH_OF_NODE_IP = 15;

    private static final String STYLE_NODE_INFO_VALUE_CELL = "node-info-value-cell";
    private static final String STYLE_NODE_INFO_LABEL_CELL = "node-info-label-cell";
    private static final String STYLE_NODE_INFO_CELL = "node-info-cell";
    private static final String STYLE_NODE_INFO_ERROR = "node-info-error";
    private static final String STYLE_NODE_INFO_PANEL = "node-info-panel";
    private static final String STYLE_NODE_INFO_TITLE = "node-info-title";

    private static final String NAME_OF_NODE_INFORMATION_WINDOW = "Selected:";
    private static final String NAME_OF_NODE_EDIT_WINDOW = "Edit:";
    private NodeInfoViewHandler handler;

    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    private VerticalPanel panel;
    private FlexTable table;

    private Label title;
    private Label errorLabel;

    private TextBox nodeName;
    private TextBox nodeIp;
    private TextBox nodePort;

    private Button saveButton;
    private Button cancelButton;

    public NodeInfoView() {
        panel = new VerticalPanel();
        title = new Label(NAME_OF_NODE_INFORMATION_WINDOW);
        table = new FlexTable();

        title.setStyleName(STYLE_NODE_INFO_TITLE);
        panel.setStyleName(STYLE_NODE_INFO_PANEL);

        panel.add(title);
        panel.add(table);

        createEditElements();
        initWidget(panel);
        clear();
    }

    @Override
    public void setHandler(NodeInfoViewHandler handler) {
        this.handler = handler;
    }

    private void createEditElements() {
        HorizontalPanel buttonsPanel = new HorizontalPanel();

        nodeName = new TextBox();
        nodeIp = new TextBox();
        nodePort = new TextBox();

        errorLabel = new Label();

        saveButton = new Button(BUTTON_SAVE);
        cancelButton = new Button(BUTTON_CANCEL);

        errorLabel.setStyleName(STYLE_NODE_INFO_ERROR);

        nodeIp.setMaxLength(MAX_LENGTH_OF_NODE_IP);
        nodePort.setMaxLength(MAX_LENGTH_OF_NODE_PORT);

        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        panel.add(errorLabel);
        panel.add(buttonsPanel);

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onSaveNode(nodeName.getText(), nodeIp.getText(), nodePort.getText());
            }
        });

        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onCancel();
            }
        });
    }

    @Override
    public void showNode(NodeInfoViewData data) {
        if (data == null) {
            clear();
            return;
        }
        title.setText(NAME_OF_NODE_INFORMATION_WINDOW);
        errorLabel.setText(EMPTY_FIELD);

        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        setVisible(true);

        table.setText(ROW_OF_ID, COLUMN_OF_LABEL, ID_FIELD_NAME);
        table.setText(ROW_OF_ID, COLUMN_OF_VALUE, String.valueOf(data.getId()));

        table.setText(ROW_OF_PARENT_ID, COLUMN_OF_LABEL, PARENT_ID_FIELD_NAME);
        table.setText(ROW_OF_PARENT_ID, COLUMN_OF_VALUE, String.valueOf(data.getParentId()));

        table.setText(ROW_OF_NAME, COLUMN_OF_LABEL, NAME_FIELD_NAME);
        table.setText(ROW_OF_NAME, COLUMN_OF_VALUE, data.getName());

        table.setText(ROW_OF_IP, COLUMN_OF_LABEL, IP_FIELD_NAME);
        table.setText(ROW_OF_IP, COLUMN_OF_VALUE, data.getIp());

        table.setText(ROW_OF_PORT, COLUMN_OF_LABEL, PORT_FIELD_NAME);
        table.setText(ROW_OF_PORT, COLUMN_OF_VALUE, String.valueOf(data.getPort()));

        styleTable();
    }

    private void styleTable() {
        for (int row = 0; row < AMOUNT_OF_ROWS; row++) {
            for (int column = 0; column < AMOUNT_OF_COLUMNS; column++) {
                table.getCellFormatter().setStyleName(
                        row,
                        column,
                        STYLE_NODE_INFO_CELL);
            }
            table.getCellFormatter().addStyleName(
                    row,
                    COLUMN_OF_LABEL,
                    STYLE_NODE_INFO_LABEL_CELL);

            table.getCellFormatter().addStyleName(
                    row,
                    COLUMN_OF_VALUE,
                    STYLE_NODE_INFO_VALUE_CELL);
        }
    }

    @Override
    public void showEditMode(NodeInfoViewData data) {
        if (data == null) {
            return;
        }

        title.setText(NAME_OF_NODE_EDIT_WINDOW);
        nodeName.setText(data.getName());
        nodeIp.setText(data.getIp());
        nodePort.setText(String.valueOf(data.getPort()));

        table.setWidget(ROW_OF_NAME, COLUMN_OF_VALUE, nodeName);
        table.setWidget(ROW_OF_IP, COLUMN_OF_VALUE, nodeIp);
        table.setWidget(ROW_OF_PORT, COLUMN_OF_VALUE, nodePort);

        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        nodeName.setVisible(true);
        nodeIp.setVisible(true);
        nodePort.setVisible(true);
    }

    @Override
    public void clear() {
        table.clear();
        title.setText(NAME_OF_NODE_INFORMATION_WINDOW);
        errorLabel.setText(EMPTY_FIELD);

        table.setText(ROW_OF_ID, COLUMN_OF_LABEL, ID_FIELD_NAME);
        table.setText(ROW_OF_ID, COLUMN_OF_VALUE, EMPTY_FIELD);

        table.setText(ROW_OF_PARENT_ID, COLUMN_OF_LABEL, PARENT_ID_FIELD_NAME);
        table.setText(ROW_OF_PARENT_ID, COLUMN_OF_VALUE, EMPTY_FIELD);

        table.setText(ROW_OF_NAME, COLUMN_OF_LABEL, NAME_FIELD_NAME);
        table.setText(ROW_OF_NAME, COLUMN_OF_VALUE, EMPTY_FIELD);

        table.setText(ROW_OF_IP, COLUMN_OF_LABEL, IP_FIELD_NAME);
        table.setText(ROW_OF_IP, COLUMN_OF_VALUE, EMPTY_FIELD);

        table.setText(ROW_OF_PORT, COLUMN_OF_LABEL, PORT_FIELD_NAME);
        table.setText(ROW_OF_PORT, COLUMN_OF_VALUE, EMPTY_FIELD);

        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        styleTable();
    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
    }
}
