package com.kristina.gwttreecrud.client.allnodes;

import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;

public class AllNodesView extends Composite implements AllNodesInterface {
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_PARENT_ID = "Parent ID";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_IP = "IP";
    private static final String COLUMN_PORT = "Порт";
    
    private CellTable<AllNodesViewData> table;

    public AllNodesView() {
        init();
    }

    private void init() {
        table = new CellTable<AllNodesViewData>();
        createColumns();
        initWidget(table);
    }

    private void createColumns() {
        TextColumn<AllNodesViewData> idColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return String.valueOf(node.getId());
            }
        };
        table.addColumn(idColumn, COLUMN_ID);

        TextColumn<AllNodesViewData> parentIdColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return String.valueOf(node.getParentId());
            }
        };
        table.addColumn(parentIdColumn, COLUMN_PARENT_ID);

        TextColumn<AllNodesViewData> nameColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return node.getName();
            }
        };
        table.addColumn(nameColumn, COLUMN_NAME);

        TextColumn<AllNodesViewData> ipColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return node.getIp();
            }
        };
        table.addColumn(ipColumn, COLUMN_IP);

        TextColumn<AllNodesViewData> portColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return String.valueOf(node.getPort());
            }
        };
        table.addColumn(portColumn, COLUMN_PORT);
    }

    @Override
    public void showNodes(List<AllNodesViewData> nodes) {
        table.setRowData(nodes);
    }

}
