package com.kristina.gwttreecrud.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.kristina.gwttreecrud.shared.Node;

public class AllNodesTable extends Composite {
    private CellTable<Node> table;

    public AllNodesTable() {
        init();
    }

    private void init() {
        table = new CellTable<Node>();
        createColumns();
        addTestData();
        initWidget(table);
    }

    private void createColumns() {
        TextColumn<Node> idColumn = new TextColumn<Node>() {
            @Override
            public String getValue(Node node) {
                return String.valueOf(node.getId());
            }
        };
        table.addColumn(idColumn, "ID");

        TextColumn<Node> parentIdColumn = new TextColumn<Node>() {
            @Override
            public String getValue(Node node) {
                return String.valueOf(node.getParentId());
            }
        };
        table.addColumn(parentIdColumn, "Parent ID");

        TextColumn<Node> nameColumn = new TextColumn<Node>() {
            @Override
            public String getValue(Node node) {
                return node.getName();
            }
        };
        table.addColumn(nameColumn, "Name");

        TextColumn<Node> ipColumn = new TextColumn<Node>() {
            @Override
            public String getValue(Node node) {
                return node.getIp();
            }
        };
        table.addColumn(ipColumn, "IP");

        TextColumn<Node> portColumn = new TextColumn<Node>() {
            @Override
            public String getValue(Node node) {
                return String.valueOf(node.getPort());
            }
        };
        table.addColumn(portColumn, "Порт");
    }

    //тестовые данные 
    private void addTestData() {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(
                1,
                null,
                "Server 1",
                "192.168.1.10",
                8080));
        nodes.add(new Node(
                2,
                1,
                "Server 2",
                "192.168.1.11",
                8081));
        nodes.add(new Node(
                3,
                1,
                "Server 3",
                "192.168.1.12",
                8082));
        nodes.add(new Node(
                4,
                2,
                "Server 4",
                "192.168.1.13",
                8083));

        table.setRowData(nodes);
    }
}
