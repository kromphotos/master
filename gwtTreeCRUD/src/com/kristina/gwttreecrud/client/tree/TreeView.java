package com.kristina.gwttreecrud.client.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreeView extends Composite {

    private VerticalPanel treePanel;//все дерево

    private TreePresenter presenter;

    public TreeView() {
        treePanel = new VerticalPanel(); // общая панель на все дерево
        treePanel.getElement().getStyle().setProperty(
                "border",
                "1px solid #B8CFE0"
        );
        treePanel.getElement().getStyle().setProperty(
                "backgroundColor",
                "#EAF6FF"
        );
        treePanel.getElement().getStyle().setProperty(
                "padding",
                "10px"
        );
        initWidget(treePanel);
    }

    public void setPresenter(TreePresenter presenter) {
        this.presenter = presenter;
    }

    public void showTree(List<TreeNode> nodes, Set<Integer> expandedNodeIds) {
        treePanel.clear();
        TreeNode root = findRoot(nodes);//есь список узлов
        if (root != null) {
            addNode(root, nodes, expandedNodeIds, 0);
        }
    }

    private TreeNode findRoot(List<TreeNode> nodes) {
        for (TreeNode node : nodes) {
            if (node.getParentId() == null) {
                return node;//нет родителя
            }
        }
        return null;
    }

    private List<TreeNode> findChildren(TreeNode parent, List<TreeNode> nodes) {
        List<TreeNode> children = new ArrayList<TreeNode>();
        for (TreeNode node : nodes) {
            if (parent.getId().equals(node.getParentId())) {
                children.add(node);
            }
        }
        return children;
    }

    private void addNode(TreeNode node, List<TreeNode> nodes,
                        Set<Integer> expandedNodeIds, int level) {
        List<TreeNode> children = findChildren(node, nodes);
        HorizontalPanel row = createNodeRow(node, children, expandedNodeIds, level);
        treePanel.add(row);
        if (expandedNodeIds.contains(node.getId())) {
            for (TreeNode child : children) {
                addNode(child, nodes, expandedNodeIds, level + 1);
            }
        }
    }

    private HorizontalPanel createNodeRow(final TreeNode node, List<TreeNode> children,
                                          final Set<Integer> expandedNodeIds, int level) {

        HorizontalPanel row = new HorizontalPanel();
        row.getElement().getStyle().setProperty("marginBottom", "4px");//отступ между строками

        Label indent = new Label();
        indent.setWidth((level * 20) + "px");//отступ для уровней
        row.add(indent);

        if (!children.isEmpty()) {//если детей нет
            final Button expandButton;
            if (expandedNodeIds.contains(node.getId())) {
                expandButton = new Button("-");
            } else {
                expandButton = new Button("+");
            }
            //кнопка размер
            expandButton.setWidth("15px");
            expandButton.setHeight("15px");
            expandButton.getElement().getStyle().setProperty(
                    "padding", "0px");//центрирование внутри кнопки
            
            expandButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (expandedNodeIds.contains(node.getId())) {
                        presenter.collapseNode(node.getId());//закрываем узел
                    } else {
                        presenter.expandNode(node.getId());//раскрываем узел
                    }
                }
            });

            row.add(expandButton);
            Label space = new Label();
            space.setWidth("3px");
            row.add(space);

        } else {
            Button leafButton = new Button("-");
            leafButton.setWidth("15px");
            leafButton.setHeight("15px");
            leafButton.getElement().getStyle().setProperty(
                    "padding", "0px");
            leafButton.setEnabled(false);//нельзя на нее нажать

            row.add(leafButton);
            Label space = new Label();
            space.setWidth("3px");
            row.add(space);
        }

        Label nameLabel = new Label(node.getName());
        nameLabel.getElement().getStyle().setProperty(
                "cursor",
                "pointer"
        );
        nameLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.selectNode(node.getId());
            }
            
        });
        row.add(nameLabel);

        return row;
    }

}