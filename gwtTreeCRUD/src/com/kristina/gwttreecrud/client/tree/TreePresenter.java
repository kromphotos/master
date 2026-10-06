package com.kristina.gwttreecrud.client.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.AppGwtService;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.ClearSelectionEvent;
import com.kristina.gwttreecrud.client.events.ClearSelectionEventHandler;
import com.kristina.gwttreecrud.client.events.DeleteNodeEvent;
import com.kristina.gwttreecrud.client.events.DeleteNodeEventHandler;
import com.kristina.gwttreecrud.client.events.NodeAddedEvent;
import com.kristina.gwttreecrud.client.events.NodeAddedEventHandler;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEvent;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEventHandler;
import com.kristina.gwttreecrud.client.tree.TreeInterface.NodeTreeViewHandler;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

//TODO(by Tutor)
// проверить все методы на публичность!

public class TreePresenter
        implements NodeUpdatedEventHandler, NodeAddedEventHandler, DeleteNodeEventHandler, ClearSelectionEventHandler {
    private static final String ERROR_UNKNOWN = "Произошла неизвестная ошибка";
    //private GwtServiceAsync service = AppGwtService.get();
    private TreeInterface view;

    private Map<Long, TreeNode> loadedNodes;
    private List<TreeViewData> viewNodes;
    private Set<Long> expandedNodeIds;

    private TreeNode selectedNode;

    public TreePresenter(TreeInterface view) {
        this.view = view;
        this.viewNodes = new ArrayList<TreeViewData>();
        this.expandedNodeIds = new HashSet<Long>();
        this.loadedNodes = new HashMap<Long, TreeNode>();

        view.setHandler(new NodeTreeViewHandler() {
            @Override
            public void onCollapseNode(final Long id) {
                expandedNodeIds.remove(id);
                removeExpandedDescendants(id);
                if (selectedNode != null && isDescendant(selectedNode.getId(), id)) {
                    AppEventBus.get().fireEvent(new ClearSelectionEvent());
                }
                refreshTree();
            }

            @Override
            public void onExpandNode(final Long id) {
                TreeNode node = loadedNodes.get(id);

                if (node == null) {
                    return;
                }
                if (node.getChildren() != null) {
                    expandedNodeIds.add(id);
                    rebuildViewNodes();
                    return;
                }
                AppGwtService.get().getAllChildById(id, new AsyncCallback<List<TreeNode>>() {
                    @Override
                    public void onSuccess(List<TreeNode> children) {
                        TreeNode node = loadedNodes.get(id);
                        node.setChildren(children);
                        for (TreeNode child : children) {
                            loadedNodes.put(child.getId(), child);
                        }
                        expandedNodeIds.add(id);
                        rebuildViewNodes();
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        if (e instanceof TreeCrudProgramException) {
                            Window.alert(e.getMessage());
                        } else {
                            Window.alert(ERROR_UNKNOWN);
                        }
                    }
                });
            }

            @Override
            public void onSelectNode(Long id) {
                TreeNode node = findNodeById(id);

                if (node == null) {
                    return;
                }
                selectedNode = node;
                AppEventBus.get().fireEvent(new NodeSelectedEvent(node));
                refreshTree();
            }
        });
        loadRoots();

        AppEventBus.get().addHandler(NodeUpdatedEvent.TYPE, this);
        AppEventBus.get().addHandler(NodeAddedEvent.TYPE, this);
        AppEventBus.get().addHandler(DeleteNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);

    }

    private void loadRoots() {
        AppGwtService.get().getAllRoots(new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> roots) {
                for (TreeNode node : roots) {
                    loadedNodes.put(node.getId(), node);
                }
                rebuildViewNodes();
            }

            @Override
            public void onFailure(Throwable e) {
                if (e instanceof TreeCrudProgramException) {
                    Window.alert(e.getMessage());
                } else {
                    Window.alert(ERROR_UNKNOWN);
                }
            }
        });
    }

    private void rebuildViewNodes() {
        viewNodes.clear();
        for (TreeNode node : loadedNodes.values()) {
            TreeViewData viewNode = new TreeViewData(
                    node.getId(),
                    node.getParentId(),
                    node.getName(),
                    node.isHasChildren());
            viewNodes.add(viewNode);
        }
        refreshTree();
    }
    /*
    public void expandNode(final Integer nodeId) {
        TreeNode node = loadedNodes.get(nodeId);
        if (node == null) {
            return;
        }
        if (node.getChildren() != null) {
            expandedNodeIds.add(nodeId);
            rebuildViewNodes();
            return;
        }
        service.getAllChildById(nodeId, new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> children) {
                TreeNode node = loadedNodes.get(nodeId);
                node.setChildren(children);
                for (TreeNode child : children) {
                    loadedNodes.put(child.getId(), child);
                }
                expandedNodeIds.add(nodeId);
                rebuildViewNodes();
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки дочерних нод", caught);
            }
        });
    }
    */
    /*
    public void collapseNode(Integer nodeId) {
        expandedNodeIds.remove(nodeId);
        removeExpandedDescendants(nodeId);
        if (selectedNode != null && isDescendant(selectedNode.getId(), nodeId)) {
            AppEventBus.get().fireEvent(new ClearSelectionEvent());
        }
        refreshTree();
    }
    */

    private void removeExpandedDescendants(Long nodeId) {
        TreeNode node = findNodeById(nodeId);

        if (node == null || node.getChildren() == null) {
            return;
        }

        for (TreeNode child : node.getChildren()) {
            if (expandedNodeIds.contains(child.getId())) {
                expandedNodeIds.remove(child.getId());
            }
            removeExpandedDescendants(child.getId());
        }
    }

    private boolean isDescendant(Long selectedNodeId, Long collapsedNodeId) {
        TreeNode node = findNodeById(selectedNodeId);

        if (node == null) {
            return false;
        }

        Long parentId = node.getParentId();
        while (parentId != null) {
            if (parentId.equals(collapsedNodeId)) {
                return true;
            }
            node = findNodeById(parentId);
            if (node == null) {
                return false;
            }
            parentId = node.getParentId();
        }

        return false;
    }

    private void refreshTree() {
        TreeViewData selectedViewNode = null;

        if (selectedNode != null) {
            selectedViewNode = new TreeViewData(
                    selectedNode.getId(),
                    selectedNode.getParentId(),
                    selectedNode.getName(),
                    selectedNode.isHasChildren());
        }
        view.showTree(viewNodes, expandedNodeIds, selectedViewNode);
    }
    /*
    public void selectNode(Integer nodeId) {
        TreeNode node = findNodeById(nodeId);
        if (node == null) {
            return;
        }
        selectedNode = node;
        AppEventBus.get().fireEvent(new NodeSelectedEvent(node));
        refreshTree();
    }
    */

    private TreeNode findNodeById(Long nodeId) {
        return loadedNodes.get(nodeId);
    }

    //
    /*
    public void updateNodeName(Integer nodeId, String name) {
        TreeNode node = findNodeById(nodeId);
        if (node == null) {
            return;
        }
        node.setName(name);
        rebuildViewNodes();
    }
    */

    private Set<Long> findDescendantIds(Long nodeId) {
        TreeNode node = loadedNodes.get(nodeId);
        Set<Long> descendantIds = new HashSet<Long>();

        if (node == null || node.getChildren() == null) {
            return descendantIds;
        }
        for (TreeNode child : node.getChildren()) {
            descendantIds.add(child.getId());
            descendantIds.addAll(findDescendantIds(child.getId()));
        }
        return descendantIds;
    }

    @Override
    public void onNodeUpdated(NodeUpdatedEvent event) {
        TreeNode updatedNode = event.getNode();
        TreeNode node = findNodeById(updatedNode.getId());

        if (node == null) {
            return;
        }
        node.setName(updatedNode.getName());
        rebuildViewNodes();
    }

    @Override
    public void nodeAdded(NodeAddedEvent event) {
        TreeNode node = event.getNode();

        if (node.getParentId() == null) {
            loadedNodes.put(node.getId(), node);
            rebuildViewNodes();
            return;
        }
        TreeNode parent = findNodeById(node.getParentId());
        if (parent == null) {
            return;
        }
        parent.setHasChildren(true);
        if (parent.getChildren() != null) {
            parent.getChildren().add(node);
            loadedNodes.put(node.getId(), node);
        }
        rebuildViewNodes();
    }

    @Override
    public void deleteNode(DeleteNodeEvent event) {
        Long nodeId = event.getNodeId();
        TreeNode node = findNodeById(nodeId);

        if (node == null) {
            return;
        }

        Set<Long> idsToRemove = findDescendantIds(nodeId);
        idsToRemove.add(nodeId);

        for (Long id : idsToRemove) {
            loadedNodes.remove(id);
        }

        Long parentId = node.getParentId();
        if (parentId != null) {
            TreeNode parent = findNodeById(parentId);
            if (parent != null && parent.getChildren() != null) {
                List<TreeNode> children = parent.getChildren();
                for (int i = 0; i < children.size(); i++) {
                    if (nodeId.equals(children.get(i).getId())) {
                        children.remove(i);
                        break;
                    }
                }
                if (children.isEmpty()) {
                    parent.setHasChildren(false);
                }
            }
        }

        expandedNodeIds.removeAll(idsToRemove);

        if (selectedNode != null && idsToRemove.contains(selectedNode.getId())) {
            AppEventBus.get().fireEvent(new ClearSelectionEvent());
        }
        rebuildViewNodes();

    }

    @Override
    public void clearSelection(ClearSelectionEvent event) {
        selectedNode = null;
        refreshTree();
    }
}
