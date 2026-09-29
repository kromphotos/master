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

public class NodeInfoView extends Composite implements NodeInfoInterface{
    private static final String NAME_OF_NODE_EDIT_WINDOW = "Edit:";
    private static final int AMOUNT_OF_COLUMNS = 2;
    private static final int AMOUNT_OF_ROWS = 5;
    private static final int INDEX_OF_FIFTH_ROW = 4;
    private static final int INDEX_OF_FOURTH_ROW = 3;
    private static final int INDEX_OF_THIRD_ROW = 2;
    private static final int INDEX_OF_SECOND_ROW = 1;
    private static final int INDEX_OF_SECOND_COLUMN = 1;
    private static final int INDEX_OF_FIRST_COLUMN = 0;
    private static final int INDEX_OF_FIRST_ROW = 0;
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
    private static final String STYLE_NAME_NODE_INFO_VALUE_CELL = "node-info-value-cell";
    private static final String STYLE_NAME_NODE_INFO_LABEL_CELL = "node-info-label-cell";
    private static final String STYLE_NAME_NODE_INFO_CELL = "node-info-cell";
    private static final String STYLE_NAME_NODE_INFO_ERROR = "node-info-error";
    private static final String STYLE_NAME_NODE_INFO_PANEL = "node-info-panel";
    private static final String STYLE_NAME_NODE_INFO_TITLE = "node-info-title";
    private static final String NAME_OF_NODE_INFORMATION_WINDOW = "Selected:";
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
        title.setStyleName(STYLE_NAME_NODE_INFO_TITLE);
        panel.add(title);
        panel.setStyleName(STYLE_NAME_NODE_INFO_PANEL);
        table = new FlexTable();
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
        nodeName = new TextBox();
        nodeIp = new TextBox();
        nodePort = new TextBox();
        errorLabel = new Label();
        saveButton = new Button(BUTTON_SAVE);
        cancelButton = new Button(BUTTON_CANCEL);
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        errorLabel.setStyleName(STYLE_NAME_NODE_INFO_ERROR);

        nodeIp.setMaxLength(MAX_LENGTH_OF_NODE_IP);
        nodePort.setMaxLength(MAX_LENGTH_OF_NODE_PORT);

        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        
        panel.add(errorLabel);
        
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

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

        table.setText(INDEX_OF_FIRST_ROW, INDEX_OF_FIRST_COLUMN, ID_FIELD_NAME);
        table.setText(INDEX_OF_FIRST_ROW, INDEX_OF_SECOND_COLUMN, String.valueOf(data.getId()));

        table.setText(INDEX_OF_SECOND_ROW, INDEX_OF_FIRST_COLUMN, PARENT_ID_FIELD_NAME);
        table.setText(INDEX_OF_SECOND_ROW, INDEX_OF_SECOND_COLUMN, String.valueOf(data.getParentId()));

        table.setText(INDEX_OF_THIRD_ROW, INDEX_OF_FIRST_COLUMN, NAME_FIELD_NAME);
        table.setText(INDEX_OF_THIRD_ROW, INDEX_OF_SECOND_COLUMN, data.getName());

        table.setText(INDEX_OF_FOURTH_ROW, INDEX_OF_FIRST_COLUMN, IP_FIELD_NAME);
        table.setText(INDEX_OF_FOURTH_ROW, INDEX_OF_SECOND_COLUMN, data.getIp());

        table.setText(INDEX_OF_FIFTH_ROW, INDEX_OF_FIRST_COLUMN, PORT_FIELD_NAME);
        table.setText(INDEX_OF_FIFTH_ROW, INDEX_OF_SECOND_COLUMN, String.valueOf(data.getPort()));

        styleTable();
    }

    private void styleTable() {
        for (int row = 0; row < AMOUNT_OF_ROWS; row++) {
            for (int column = 0; column < AMOUNT_OF_COLUMNS; column++) {
                table.getCellFormatter().setStyleName(
                        row,
                        column,
                        STYLE_NAME_NODE_INFO_CELL);
            }
            table.getCellFormatter().addStyleName(
                    row,
                    INDEX_OF_FIRST_COLUMN,
                    STYLE_NAME_NODE_INFO_LABEL_CELL);

            table.getCellFormatter().addStyleName(
                    row,
                    INDEX_OF_SECOND_COLUMN,
                    STYLE_NAME_NODE_INFO_VALUE_CELL);
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

        table.setWidget(INDEX_OF_THIRD_ROW, INDEX_OF_SECOND_COLUMN, nodeName);
        table.setWidget(INDEX_OF_FOURTH_ROW, INDEX_OF_SECOND_COLUMN, nodeIp);
        table.setWidget(INDEX_OF_FIFTH_ROW, INDEX_OF_SECOND_COLUMN, nodePort);

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

        table.setText(INDEX_OF_FIRST_ROW, INDEX_OF_FIRST_COLUMN, ID_FIELD_NAME);
        table.setText(INDEX_OF_FIRST_ROW, INDEX_OF_SECOND_COLUMN, EMPTY_FIELD);

        table.setText(INDEX_OF_SECOND_ROW, INDEX_OF_FIRST_COLUMN, PARENT_ID_FIELD_NAME);
        table.setText(INDEX_OF_SECOND_ROW, INDEX_OF_SECOND_COLUMN, EMPTY_FIELD);

        table.setText(INDEX_OF_THIRD_ROW, INDEX_OF_FIRST_COLUMN, NAME_FIELD_NAME);
        table.setText(INDEX_OF_THIRD_ROW, INDEX_OF_SECOND_COLUMN, EMPTY_FIELD);

        table.setText(INDEX_OF_FOURTH_ROW, INDEX_OF_FIRST_COLUMN, IP_FIELD_NAME);
        table.setText(INDEX_OF_FOURTH_ROW, INDEX_OF_SECOND_COLUMN, EMPTY_FIELD);

        table.setText(INDEX_OF_FIFTH_ROW, INDEX_OF_FIRST_COLUMN, PORT_FIELD_NAME);
        table.setText(INDEX_OF_FIFTH_ROW, INDEX_OF_SECOND_COLUMN, EMPTY_FIELD);

        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        styleTable();
    }
    
    public void showError(String message) {
        errorLabel.setText(message);
    }
}
