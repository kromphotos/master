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
    private NodeInfoViewHandler handler;

    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    @Override
    public void setHandler(NodeInfoViewHandler handler) {
        this.handler = handler;
    }
    
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
        title = new Label("Selected:");
        title.setStyleName("node-info-title");
        panel.add(title);
        panel.setStyleName("node-info-panel");
        table = new FlexTable();
        panel.add(table);
        createEditElements();
        initWidget(panel);
        clear();
    }

    private void createEditElements() {
        nodeName = new TextBox();
        nodeIp = new TextBox();
        nodePort = new TextBox();
        errorLabel = new Label();
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        errorLabel.setStyleName("node-info-error");

        nodeIp.setMaxLength(15);
        nodePort.setMaxLength(4);

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
        title.setText("Selected:");
        errorLabel.setText("");

        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        setVisible(true);

        table.setText(0, 0, "ID");
        table.setText(0, 1, String.valueOf(data.getId()));

        table.setText(1, 0, "Parent ID");
        table.setText(1, 1, String.valueOf(data.getParentId()));

        table.setText(2, 0, "Name");
        table.setText(2, 1, data.getName());

        table.setText(3, 0, "IP");
        table.setText(3, 1, data.getIp());

        table.setText(4, 0, "Port");
        table.setText(4, 1, String.valueOf(data.getPort()));

        styleTable();
    }

    private void styleTable() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 2; column++) {
                table.getCellFormatter().setStyleName(
                        row,
                        column,
                        "node-info-cell");
            }
            table.getCellFormatter().addStyleName(
                    row,
                    0,
                    "node-info-label-cell");

            table.getCellFormatter().addStyleName(
                    row,
                    1,
                    "node-info-value-cell");
        }
    }

    @Override
    public void showEditMode(NodeInfoViewData data) {
        if (data == null) {
            return;
        }

        title.setText("Edit:");
        nodeName.setText(data.getName());
        nodeIp.setText(data.getIp());
        nodePort.setText(String.valueOf(data.getPort()));

        table.setWidget(2, 1, nodeName);
        table.setWidget(3, 1, nodeIp);
        table.setWidget(4, 1, nodePort);

        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        nodeName.setVisible(true);
        nodeIp.setVisible(true);
        nodePort.setVisible(true);
    }

    @Override
    public void clear() {
        table.clear();
        title.setText("Selected:");
        errorLabel.setText("");

        table.setText(0, 0, "ID");
        table.setText(0, 1, "");

        table.setText(1, 0, "Parent ID");
        table.setText(1, 1, "");

        table.setText(2, 0, "Name");
        table.setText(2, 1, "");

        table.setText(3, 0, "IP");
        table.setText(3, 1, "");

        table.setText(4, 0, "Port");
        table.setText(4, 1, "");

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
