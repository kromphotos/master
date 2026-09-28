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
    private static final String NODE_ADD_ERROR = "node-add-error";

    private NodeAddViewHandler handler;
    
    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    
    private TextBox parentId;
    private TextBox nodeName;
    private TextBox nodeIp;
    private TextBox nodePort;

    private Label errorLabel;
    private Button saveButton;
    private Button cancelButton;
    private FlexTable formTable;
    
    public NodeAddView() {
        setText("Add node:");
        
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
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        
        errorLabel.setStyleName(NODE_ADD_ERROR);
        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //TODO(by Tutor)
                // опять скобки с новой строки
                if (handler!=null) {
                    handler.onSaveNode(parentId.getText(), nodeName.getText(), nodeIp.getText(), nodePort.getText());
                }
            }
        });

        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler!=null)
                {
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
        nodeName.setText("");
        nodeIp.setText("");
        nodePort.setText("");
        errorLabel.setText("");

        formTable.clear();

        //TODO(by Tutor)
        // не надо так делать. пиши конкретный номер строки в самом методе setWidget
        parentId.setText(String.valueOf(parentIdValue));
        parentId.setReadOnly(true);

        formTable.setWidget(0, 0, new Label("Parent's id:"));
        formTable.setWidget(0, 1, parentId);

        formTable.setWidget(1, 0, new Label("Node name:"));
        formTable.setWidget(1, 1, nodeName);

        formTable.setWidget(2, 0, new Label("Node's Ip:"));
        formTable.setWidget(2, 1, nodeIp);

        formTable.setWidget(3, 0, new Label("Node's port:"));
        formTable.setWidget(3, 1, nodePort);

        nodeIp.setMaxLength(15);
        //TODO(by Tutor)
        // порт может быть 5-ти значным
        nodePort.setMaxLength(5);

        center();
        show();
    }
    
    @Override
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
        nodePort.setMaxLength(5);

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
