package com.kristina.gwttreecrud.client.allnodes;

import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesView extends Composite {
    
    
    
    
    
    private CellTable<TreeNode> table;

    public AllNodesView() {
        init();
    }

    private void init() {
        table = new CellTable<TreeNode>();
        createColumns();
        initWidget(table);
    }

    /*
    private void loadNodes() {
        //        объект, который умеет обработать результат асинхронного запроса
        AsyncCallback<List<TreeNode>> callback = new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                table.setRowData(nodes);
            }
    
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки TreeNode", caught);
            }
        };
        service.getAllNodes(callback);//обращение к серверу. выполнение на стороне сервера и запись рез-та в коллбэк
    }
    */
    private void createColumns() {
        TextColumn<TreeNode> idColumn = new TextColumn<TreeNode>() {
            @Override
            public String getValue(TreeNode node) {
                return String.valueOf(node.getId());
            }
        };
        table.addColumn(idColumn, "ID");

        TextColumn<TreeNode> parentIdColumn = new TextColumn<TreeNode>() {
            @Override
            public String getValue(TreeNode node) {
                return String.valueOf(node.getParentId());
            }
        };
        table.addColumn(parentIdColumn, "Parent ID");

        TextColumn<TreeNode> nameColumn = new TextColumn<TreeNode>() {
            @Override
            public String getValue(TreeNode node) {
                return node.getName();
            }
        };
        table.addColumn(nameColumn, "Name");

        TextColumn<TreeNode> ipColumn = new TextColumn<TreeNode>() {
            @Override
            public String getValue(TreeNode node) {
                return node.getIp();
            }
        };
        table.addColumn(ipColumn, "IP");

        TextColumn<TreeNode> portColumn = new TextColumn<TreeNode>() {
            @Override
            public String getValue(TreeNode node) {
                return String.valueOf(node.getPort());
            }
        };
        table.addColumn(portColumn, "Порт");
    }
    
    //это делает уже презентер
    public void showNodes(List<TreeNode> nodes) {
        table.setRowData(nodes);
    }

}
