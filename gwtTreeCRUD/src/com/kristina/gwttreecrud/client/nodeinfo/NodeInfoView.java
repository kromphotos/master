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
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeInfoView extends Composite {
    private NodeInfoPresenter presenter;

    public void setPresenter(NodeInfoPresenter presenter) {
        this.presenter = presenter;
    }

    private VerticalPanel panel;
    private FlexTable table;
    private Label title;
    private TextBox nodeName;
    private TextBox nodeIp;
    private TextBox nodePort;
    private Button saveButton;
    private Button cancelButton;

    private void createEditElements() {
        nodeName = new TextBox();
        nodeIp = new TextBox();
        nodePort = new TextBox();

        nodeIp.setMaxLength(15);
        nodePort.setMaxLength(5);

        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        HorizontalPanel buttonsPanel = new HorizontalPanel();

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        panel.add(buttonsPanel);

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.saveNode();
            }
        });

        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.cancelEdit();
            }
        });
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    public NodeInfoView() {
        panel = new VerticalPanel();
        title = new Label("Selected:");

        title.getElement().getStyle().setProperty("fontWeight", "bold");
        panel.add(title);
        panel.getElement().getStyle().setProperty(
                "border",
                "1px solid #D8BFD8");
        panel.getElement().getStyle().setProperty(
                "borderRadius",
                "6px");
        panel.getElement().getStyle().setProperty(
                "backgroundColor",
                "#FFF9FC");
        panel.getElement().getStyle().setProperty(
                "padding",
                "15px");
        table = new FlexTable();
        panel.add(table);
        createEditElements();
        initWidget(panel);
        clear();
    }

    public void showNode(TreeNode node) {
        if (node == null) {
            clear();
            return;
        }
        title.setText("Selected:");
        nodeName.setVisible(false);
        nodeIp.setVisible(false);
        nodePort.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        setVisible(true);

        table.setText(0, 0, "ID");
        table.setText(0, 1, String.valueOf(node.getId()));

        table.setText(1, 0, "Parent ID");
        table.setText(1, 1, String.valueOf(node.getParentId()));

        table.setText(2, 0, "Name");
        table.setText(2, 1, node.getName());

        table.setText(3, 0, "IP");
        table.setText(3, 1, node.getIp());

        table.setText(4, 0, "Port");
        table.setText(4, 1, String.valueOf(node.getPort()));

        styleTable();
    }

    private void styleTable() {

        for (int row = 0; row < 5; row++) {

            for (int column = 0; column < 2; column++) {

                table.getCellFormatter()
                        .getElement(row, column)
                        .getStyle()
                        .setProperty(
                                "border",
                                "1px solid #E0D0D8");

                table.getCellFormatter()
                        .getElement(row, column)
                        .getStyle()
                        .setProperty(
                                "padding",
                                "8px");
            }
            table.getCellFormatter()
                    .getElement(row, 0)
                    .getStyle()
                    .setProperty("width", "80px");

            table.getCellFormatter()
                    .getElement(row, 1)
                    .getStyle()
                    .setProperty("width", "100px");
        }
    }

    public void showEditMode(TreeNode node) {
        if (node == null) {
            return;
        }

        title.setText("Edit:");
        nodeName.setText(node.getName());
        nodeIp.setText(node.getIp());
        nodePort.setText(String.valueOf(node.getPort()));

        table.setWidget(2, 1, nodeName);
        table.setWidget(3, 1, nodeIp);
        table.setWidget(4, 1, nodePort);

        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        nodeName.setVisible(true);
        nodeIp.setVisible(true);
        nodePort.setVisible(true);
    }

    public String getNodeName() {
        return nodeName.getText();
    }

    public String getNodeIp() {
        return nodeIp.getText();
    }

    public String getNodePort() {
        return nodePort.getText();
    }

    public void clear() {
        table.clear();
        title.setText("Selected:");

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

}
