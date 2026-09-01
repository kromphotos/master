package com.kristina.gwttreecrud.client.allnodes;

import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
//import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesView extends Composite implements AllNodesInterface {
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
        table.addColumn(idColumn, "ID");

        TextColumn<AllNodesViewData> parentIdColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return String.valueOf(node.getParentId());
            }
        };
        table.addColumn(parentIdColumn, "Parent ID");

        TextColumn<AllNodesViewData> nameColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return node.getName();
            }
        };
        table.addColumn(nameColumn, "Name");

        TextColumn<AllNodesViewData> ipColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return node.getIp();
            }
        };
        table.addColumn(ipColumn, "IP");

        TextColumn<AllNodesViewData> portColumn = new TextColumn<AllNodesViewData>() {
            @Override
            public String getValue(AllNodesViewData node) {
                return String.valueOf(node.getPort());
            }
        };
        table.addColumn(portColumn, "Порт");
    }
    
    //это делает уже презентер
    @Override
    public void showNodes(List<AllNodesViewData> nodes) {
        table.setRowData(nodes);
    }

}
