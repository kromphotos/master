package com.kristina.gwttreecrud.client.tree;

import java.util.ArrayList;
import java.util.Collections;
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
    private static final int INDENT_SIZE = 20;
    private static final int LEVEL_INCREMENT = 1;
    private static final int ROOT_LEVEL = 0;

    private static final String STYLE_TREE_NODE_NAME_SELECTED = "tree-node-name-selected";
    private static final String STYLE_TREE_NODE_NAME = "tree-node-name";
    private static final String STYLE_TREE_EXPAND_BUTTON = "tree-expand-button";
    private static final String STYLE_TREE_NODE_ROW = "tree-node-row";
    private static final String STYLE_TREE_PANEL = "tree-panel";

    private static final String PLUS_BUTTON = "+";
    private static final String MINUS_BUTTON = "-";

    private static final String PIXEL = "px";

    private NodeTreeViewHandler handler;

    //TODO(by Tutor)
    // нельзя тут оставлять, это блок перменных, а не методов
    private VerticalPanel treePanel;

    public TreeView() {
        treePanel = new VerticalPanel();
        treePanel.setStyleName(STYLE_TREE_PANEL);
        initWidget(treePanel);
    }

    @Override
    public void setHandler(NodeTreeViewHandler handler) {
        this.handler = handler;
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
        // всех надо сортировать
        Collections.sort(roots);

        for (TreeViewData root : roots) {
            addNode(root, nodes, expandedNodeIds, selectedNode, ROOT_LEVEL);
        }
    }

    private List<TreeViewData> findChildren(TreeViewData parent, List<TreeViewData> nodes) {
        List<TreeViewData> children = new ArrayList<TreeViewData>();

        for (TreeViewData node : nodes) {
            if (parent.getId().equals(node.getParentId())) {
                children.add(node);
            }
        }
        Collections.sort(children);
        return children;
    }

    private void addNode(TreeViewData node,
            List<TreeViewData> nodes,
            Set<Integer> expandedNodeIds,
            TreeViewData selectedNode,
            int level) {
        List<TreeViewData> children = findChildren(node, nodes);
        HorizontalPanel row = createNodeRow(node, expandedNodeIds, selectedNode, level);

        treePanel.add(row);
        if (expandedNodeIds.contains(node.getId())) {
            for (TreeViewData child : children) {
                addNode(child, nodes, expandedNodeIds, selectedNode, level + LEVEL_INCREMENT);
            }
        }
    }

    private HorizontalPanel createNodeRow(final TreeViewData node,
            final Set<Integer> expandedNodeIds, TreeViewData selectedNode, int level) {
        HorizontalPanel row = new HorizontalPanel();
        row.setStyleName(STYLE_TREE_NODE_ROW);

        Label indent = new Label();
        indent.setWidth((level * INDENT_SIZE) + PIXEL);
        row.add(indent);

        if (node.isHasChildren()) {
            final Button expandButton;
            if (expandedNodeIds.contains(node.getId())) {
                expandButton = new Button(MINUS_BUTTON);
            } else {
                expandButton = new Button(PLUS_BUTTON);
            }
            expandButton.setStyleName(STYLE_TREE_EXPAND_BUTTON);

            expandButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (handler != null) {
                        if (expandedNodeIds.contains(node.getId())) {
                            handler.onCollapseNode(node.getId());
                        } else {
                            handler.onExpandNode(node.getId());
                        }
                    }
                }
            });

            row.add(expandButton);

        } else {
            Button leafButton = new Button(MINUS_BUTTON);
            leafButton.setStyleName(STYLE_TREE_EXPAND_BUTTON);
            leafButton.setEnabled(false);

            row.add(leafButton);
        }

        Label nameLabel = new Label(node.getName());
        nameLabel.setStyleName(STYLE_TREE_NODE_NAME);
        if (selectedNode != null
                && selectedNode.getId().equals(node.getId())) {
            nameLabel.addStyleName(STYLE_TREE_NODE_NAME_SELECTED);
        }
        nameLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler != null) {
                    handler.onSelectNode(node.getId());
                }
            }
        });
        row.add(nameLabel);

        return row;
    }
}