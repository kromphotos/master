package com.kristina.gwttreecrud.client.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TreeView extends Composite {
    private VerticalPanel treePanel;
    private TreePresenter presenter;

    public TreeView() {
        treePanel = new VerticalPanel(); 
        treePanel.getElement().getStyle().setProperty(
                "border",
                "1px solid #B8CFE0");
        treePanel.getElement().getStyle().setProperty(
                "backgroundColor",
                "#EAF6FF");
        treePanel.getElement().getStyle().setProperty(
                "padding",
                "10px");
        initWidget(treePanel);
    }

    public void setPresenter(TreePresenter presenter) {
        this.presenter = presenter;
    }

    public void showTree(List<TreeViewData> nodes,
            Set<Integer> expandedNodeIds,
            TreeViewData selectedNode) {
        treePanel.clear();
        List<TreeViewData> roots = new ArrayList<TreeViewData>();
        for (TreeViewData node : nodes) {
            if (node.getParentId() == null) {
                roots.add(node);
            }
        }
        Collections.sort(roots, new Comparator<TreeViewData>() {
            @Override
            public int compare(TreeViewData first, TreeViewData second) {
                return first.getName().compareToIgnoreCase(second.getName());
            }
        });
        
        for (TreeViewData root : roots) {
            addNode(root, nodes, expandedNodeIds, selectedNode, 0);
        }
    }

    private List<TreeViewData> findChildren(TreeViewData parent, List<TreeViewData> nodes) {
        List<TreeViewData> children = new ArrayList<TreeViewData>();
        for (TreeViewData node : nodes) {
            if (parent.getId().equals(node.getParentId())) {
                children.add(node);
            }
        }
        return children;
    }

    private void addNode(TreeViewData node,
            List<TreeViewData> nodes,
            Set<Integer> expandedNodeIds,
            TreeViewData selectedNode,
            int level) {
        List<TreeViewData> children = findChildren(node, nodes);
        HorizontalPanel row = createNodeRow(node, children, expandedNodeIds, selectedNode, level);
        treePanel.add(row);
        if (expandedNodeIds.contains(node.getId())) {
            for (TreeViewData child : children) {
                addNode(child, nodes, expandedNodeIds, selectedNode, level + 1);
            }
        }
    }

    private HorizontalPanel createNodeRow(final TreeViewData node, List<TreeViewData> children,
            final Set<Integer> expandedNodeIds, TreeViewData selectedNode, int level) {

        HorizontalPanel row = new HorizontalPanel();
        row.getElement().getStyle().setProperty("marginBottom", "4px");

        Label indent = new Label();
        indent.setWidth((level * 20) + "px");
        row.add(indent);

        if (!children.isEmpty()) {
            final Button expandButton;
            if (expandedNodeIds.contains(node.getId())) {
                expandButton = new Button("-");
            } else {
                expandButton = new Button("+");
            }
          
            expandButton.setWidth("15px");
            expandButton.setHeight("15px");
            expandButton.getElement().getStyle().setProperty(
                    "padding", "0px");

            expandButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (expandedNodeIds.contains(node.getId())) {
                        presenter.collapseNode(node.getId());
                    } else {
                        presenter.expandNode(node.getId());
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
            leafButton.setEnabled(false);

            row.add(leafButton);
            Label space = new Label();
            space.setWidth("3px");
            row.add(space);
        }

        Label nameLabel = new Label(node.getName());
        nameLabel.getElement().getStyle().setProperty(
                "cursor",
                "pointer");
        if (selectedNode != null
                && selectedNode.getId().equals(node.getId())) {

            nameLabel.getElement().getStyle().setProperty(
                    "backgroundColor",
                    "#FCE4EC");

            nameLabel.getElement().getStyle().setProperty(
                    "padding",
                    "3px 6px");

            nameLabel.getElement().getStyle().setProperty(
                    "borderRadius",
                    "4px");
        }
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