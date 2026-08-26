package com.kristina.gwttreecrud.client.nodeedit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeEditView extends Composite {
    private TextBox nodeName;
    private TextBox nodeIp;
    private TextBox nodePort;
    private Button saveButton;
    private FlexTable formTable;

    private TreeNode currentNode;
    private NodeEditPresenter presenter;

    public void setPresenter(NodeEditPresenter presenter) {
        this.presenter = presenter;
    }

    public TreeNode getCurrentNode() {
        return currentNode;
    }

    public NodeEditView() {
        VerticalPanel editPanel = new VerticalPanel();

        //Заголовок карточки
        Label titleLabel = new Label("Edit information:");
        titleLabel.setStyleName("card-title");
        editPanel.add(titleLabel);

        formTable = new FlexTable();

        //поля ввода
        nodeName = new TextBox();
        nodeIp = new TextBox();
        nodePort = new TextBox();

        saveButton = new Button("Save");
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.saveNode();
            }
        });

        editPanel.add(formTable);
        editPanel.add(saveButton);
        initWidget(editPanel);
        setVisible(false);
    }

    public void showEditCard(TreeNode node) {
        setVisible(true);
        currentNode = node;

        formTable.clear();
        formTable.setWidget(0, 0, new Label("Name of node:"));
        formTable.setWidget(0, 1, nodeName);

        formTable.setWidget(1, 0, new Label("Node IP:"));
        formTable.setWidget(1, 1, nodeIp);

        formTable.setWidget(2, 0, new Label("Node port:"));
        formTable.setWidget(2, 1, nodePort);

        nodeName.setText(node.getName());
        nodeIp.setText(node.getIp());
        nodePort.setText(String.valueOf(node.getPort()));

        nodeIp.setMaxLength(15);
        nodePort.setMaxLength(4);
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
    
    public void hideEditCard() {
        setVisible(false);
        currentNode = null;
    }

}
