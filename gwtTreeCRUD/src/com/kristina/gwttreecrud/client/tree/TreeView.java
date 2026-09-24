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

public class TreeView extends Composite implements TreeInterface {
    private NodeTreeViewHandler handler;

    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    @Override
    public void setHandler(NodeTreeViewHandler handler) {
        this.handler = handler;
    }

    private VerticalPanel treePanel;

    public TreeView() {
        treePanel = new VerticalPanel(); 
        treePanel.setStyleName("tree-panel");
        initWidget(treePanel);
    }

    @Override
    public void showTree(List<TreeViewData> nodes, Set<Integer> expandedNodeIds, TreeViewData selectedNode) {
        treePanel.clear();

        List<TreeViewData> roots = new ArrayList<TreeViewData>();
        for (TreeViewData node : nodes) {
            if (node.getParentId() == null) {
                roots.add(node);
            }
        }

        //TODO(by Tutor)
        // отсортирован будет только рут?
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
        row.setStyleName("tree-node-row");

        Label indent = new Label();
        indent.setWidth((level * 20) + "px");
        row.add(indent);

        if (node.isHasChildren()) {
            final Button expandButton;
            if (expandedNodeIds.contains(node.getId())) {
                expandButton = new Button("-");
            } else {
                expandButton = new Button("+");
            }
            expandButton.setStyleName("tree-expand-button");

            expandButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (expandedNodeIds.contains(node.getId())) {
                        handler.onCollapseNode(node.getId());
                    } else {
                        handler.onExpandNode(node.getId());
                    }
                }
            });

            row.add(expandButton);

        } else {
            Button leafButton = new Button("-");
            leafButton.setStyleName("tree-expand-button");
            leafButton.setEnabled(false);

            row.add(leafButton);
        }

        Label nameLabel = new Label(node.getName());
        nameLabel.setStyleName("tree-node-name");
        if (selectedNode != null
                && selectedNode.getId().equals(node.getId())) {
            nameLabel.addStyleName("tree-node-name-selected");//добавить еще 1 класс не убирая существующий
        }
        nameLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onSelectNode(node.getId());
            }

        });
        row.add(nameLabel);

        return row;
    }
}