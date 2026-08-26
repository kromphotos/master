package com.kristina.gwttreecrud.client.tree;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeInfoView extends Composite {
    private VerticalPanel panel;
    private FlexTable table;
    private Label title;

    public NodeInfoView() {
        panel = new VerticalPanel();
        title = new Label("Selected:");
       
        title.getElement().getStyle().setProperty("fontWeight","bold");
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
        initWidget(panel);
        clear();
        setVisible(false);
    }

    public void showNode(TreeNode node) {
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
        }
    }

    public void clear() {
        table.clear();
        setVisible(false);
    }

}
