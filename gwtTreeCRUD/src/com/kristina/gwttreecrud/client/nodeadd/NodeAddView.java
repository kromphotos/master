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

public class NodeAddView extends DialogBox{
    private TextBox parentId;
    private TextBox nodeName;
    private TextBox nodeIp;
    private TextBox nodePort;
    
    private Label errorLabel;
    private Button saveButton;
    private Button cancelButton;
    private FlexTable formTable;
    private NodeAddPresenter presenter;

    public void setPresenter(NodeAddPresenter presenter) {
        this.presenter = presenter;
    }
    public NodeAddView() {
        //Заголовок окна
        setText("Add node:");
        
        //Label titleLabel = new Label("Add information:");
        //titleLabel.setStyleName("card-title");
        //editPanel.add(titleLabel);
        
        // Настройки самого DialogBox
        setAnimationEnabled(true);
        setGlassEnabled(true);

        VerticalPanel addPanel = new VerticalPanel();
        HorizontalPanel buttonsPanel = new HorizontalPanel();


        formTable = new FlexTable();

        //поля ввода
        parentId = new TextBox();
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
        
        errorLabel = new Label();
        errorLabel.getElement().getStyle().setProperty("color", "red");
        
        cancelButton = new Button("Cancel");
        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hideAddCard();
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
    
    public void showError(String message) {
        errorLabel.setText(message);
    }
    
    public void showAddCard(Integer parentIdValue) {
        parentId.setText(String.valueOf(parentIdValue));
        nodeName.setText("");
        nodeIp.setText("");
        nodePort.setText("");
        errorLabel.setText("");

        formTable.clear();

        int row = 0;

        if (parentIdValue != null) {
            parentId.setText(String.valueOf(parentIdValue));

            formTable.setWidget(row, 0, new Label("Parent's id:"));
            formTable.setWidget(row, 1, parentId);

            row++;
        }

        formTable.setWidget(row, 0, new Label("Node name:"));
        formTable.setWidget(row, 1, nodeName);
        row++;

        formTable.setWidget(row, 0, new Label("Node's Ip:"));
        formTable.setWidget(row, 1, nodeIp);
        row++;

        formTable.setWidget(row, 0, new Label("Node's port:"));
        formTable.setWidget(row, 1, nodePort);
        
        nodeIp.setMaxLength(15);
        nodePort.setMaxLength(4);
        
        center();
        show();
    }
    
    public void showAddRootCard() {
        nodeName.setText("");
        nodeIp.setText("");
        nodePort.setText("");

        errorLabel.setText("");
        formTable.clear();

        formTable.setWidget(0, 0, new Label("Node name:"));
        formTable.setWidget(0, 1, nodeName);

        formTable.setWidget(1, 0, new Label("Node's Ip:"));
        formTable.setWidget(1, 1, nodeIp);

        formTable.setWidget(2, 0, new Label("Node's port:"));
        formTable.setWidget(2, 1, nodePort);

        nodeIp.setMaxLength(15);
        nodePort.setMaxLength(4);

        center();
        show();
    }
    
    public String getParentId() {
        return parentId.getText();
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
    
    public void hideAddCard() {
        hide();
        //currentNode = null;
    }


}
