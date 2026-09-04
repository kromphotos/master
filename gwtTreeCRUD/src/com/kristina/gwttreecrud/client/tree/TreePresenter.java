package com.kristina.gwttreecrud.client.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.web.bindery.event.shared.EventBus;
import com.kristina.gwttreecrud.client.TreeController;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreePresenter {
    private TreeView view;
    private TreeController controller;
    private EventBus eventBus;
    
    private List<TreeNode> nodes;
    private List<TreeViewData> viewNodes;
    private Set<Integer> expandedNodeIds;//раскрытые ноды
    private TreeViewData selectedNode;

    public TreePresenter(TreeView view, EventBus eventBus) {
        this.view = view;
        this.eventBus = eventBus;
        this.nodes = new ArrayList<TreeNode>();
        this.viewNodes = new ArrayList<TreeViewData>();
        this.expandedNodeIds = new HashSet<Integer>();
    }

    public void setController(TreeController controller) {
        this.controller = controller;
    }

    public void refreshNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
        
        Integer selectedNodeId = null;
        if (selectedNode != null) {
            selectedNodeId = selectedNode.getId();
        }
        
        viewNodes.clear();
        for (TreeNode node : nodes) {
            TreeViewData viewNode = new TreeViewData(
                    node.getId(),
                    node.getParentId(),
                    node.getName());

            viewNodes.add(viewNode);
        }
        
        if (selectedNodeId != null) {
            selectedNode = findViewNodeById(selectedNodeId);
        }
        
        refreshTree();
    }

    public void expandNode(Integer nodeId) {
        expandedNodeIds.add(nodeId);
        refreshTree();
    }

    //сворачивание ноды(nodeId - кого свернули)
    public void collapseNode(Integer nodeId) {
        expandedNodeIds.remove(nodeId);
        removeExpandedDescendants(nodeId);
        if (selectedNode != null && isDescendant(selectedNode.getId(), nodeId)) {
            controller.clearSelection();
        }
        refreshTree();
    }

    //поиск потомков для удаления
    private void removeExpandedDescendants(Integer nodeId) {
        for (TreeViewData node : viewNodes) {
            if (nodeId.equals(node.getParentId())) {
                if (expandedNodeIds.contains(node.getId())) {
                    expandedNodeIds.remove(node.getId());
                    removeExpandedDescendants(node.getId());
                }
            }
        }

    }

    private boolean isDescendant(Integer selectedNodeId, Integer collapsedNodeId) {
        TreeViewData selectedViewNode = findViewNodeById(selectedNodeId);

        if (selectedViewNode == null) {
            return false;
        }
        Integer parentId = selectedViewNode.getParentId();

        while (parentId != null) {
            if (parentId.equals(collapsedNodeId)) {
                return true;
            }

            TreeViewData parentNode = findViewNodeById(parentId);

            if (parentNode == null) {
                return false;
            }

            parentId = parentNode.getParentId();
        }

        return false;
    }

    private void refreshTree() {
        view.showTree(viewNodes, expandedNodeIds, selectedNode);
    }

    //поиск отображаемой ноды
    private TreeViewData findViewNodeById(Integer nodeId) {
        for (TreeViewData node : viewNodes) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        return null;
    }

    public void selectNode(Integer nodeId) {
        TreeViewData viewNode = findViewNodeById(nodeId);
        if (viewNode == null) {
            return;
        }

        TreeNode node = findNodeById(nodeId);
        if (node == null) {
            return;
        }
        selectedNode = viewNode;
        //controller.selectNode(node);
        //ообщем все кто подписан на событие нодселектедивент
        //что пользователь выбрал эту ноду
        eventBus.fireEvent(new NodeSelectedEvent(node));
        refreshTree();
    }

    private TreeNode findNodeById(Integer nodeId) {
        for (TreeNode node : nodes) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 
     */
    public void clearSelection() {
        selectedNode = null;
    }
}