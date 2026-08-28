package com.kristina.gwttreecrud.client.tree;

import java.util.List;

import com.kristina.gwttreecrud.client.nodeactions.NodeActionsPresenter;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoPresenter;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreePresenter {
    private NodeActionsPresenter nodeActionsPresenter;
    private TreeView view;
    private TreeViewData viewData;
    private NodeInfoPresenter nodeInfoPresenter;

    public TreePresenter(TreeView view, TreeViewData viewData, NodeInfoPresenter nodeInfoPresenter, NodeActionsPresenter nodeActionsPresenter) {
        this.view = view;
        this.viewData = viewData;
        this.nodeInfoPresenter = nodeInfoPresenter;
        this.nodeActionsPresenter = nodeActionsPresenter;
    }

    public void refreshNodes(List<TreeNode> nodes) {
        viewData.setNodes(nodes);
        view.showTree(viewData.getNodes(), viewData.getExpandedNodeIds(), viewData.getSelectedNodeId());

        Integer selectedNodeId = viewData.getSelectedNodeId();
        if (selectedNodeId != null) {
            TreeNode selectedNode = findNodeById(selectedNodeId);
            if (selectedNode != null) {
                nodeInfoPresenter.showNode(selectedNode);
            } else {
                nodeInfoPresenter.clear();
            }
        }
    }

    public void expandNode(Integer nodeId) {
        viewData.expandId(nodeId);
        refreshTree();
    }

    //сворачивание ноды(nodeId - кого свернули)
    public void collapseNode(Integer nodeId) {
        viewData.removeId(nodeId);
        removeExpandedDescendants(nodeId);
        Integer selectedNodeId = viewData.getSelectedNodeId();//айди выбранной ноды
        if (selectedNodeId != null && isDescendant(selectedNodeId, nodeId)) {
            //потом в контроллер лучше запихнуть(как рефереш сделать)
            viewData.clearSelectedNode();
            nodeInfoPresenter.clear();
            nodeActionsPresenter.clearSelection();
        }
        refreshTree();
    }

    //поиск потомков для удаления
    private void removeExpandedDescendants(Integer nodeId) {
        for (TreeNode node : viewData.getNodes()) {
            if (nodeId.equals(node.getParentId())) {
                if (viewData.isExpanded(node.getId())) {
                    viewData.removeId(node.getId());
                    removeExpandedDescendants(node.getId());
                }
            }
        }

    }

    private boolean isDescendant(Integer selectedNodeId, Integer collapsedNodeId) {
        TreeNode selectedNode = findNodeById(selectedNodeId);

        if (selectedNode == null) {
            return false;
        }
        Integer parentId = selectedNode.getParentId();

        while (parentId != null) {
            if (parentId.equals(collapsedNodeId)) {
                return true;
            }

            TreeNode parentNode = findNodeById(parentId);

            if (parentNode == null) {
                return false;
            }

            parentId = parentNode.getParentId();
        }

        return false;
    }

    private void refreshTree() {
        view.showTree(viewData.getNodes(), viewData.getExpandedNodeIds(), viewData.getSelectedNodeId());
    }

    public void selectNode(Integer nodeId) {
        viewData.setSelectedNodeId(nodeId);
        nodeActionsPresenter.selectNode(nodeId);
        TreeNode selectedNode = findNodeById(nodeId);
        if (selectedNode != null) {
            nodeInfoPresenter.showNode(selectedNode);
        } else {
            nodeInfoPresenter.clear();
        }
        refreshTree();
    }

    private TreeNode findNodeById(Integer nodeId) {
        for (TreeNode node : viewData.getNodes()) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        return null;
    }

    public void clearSelection() {
        viewData.clearSelectedNode();
        nodeInfoPresenter.clear();
        refreshTree();
    }
}