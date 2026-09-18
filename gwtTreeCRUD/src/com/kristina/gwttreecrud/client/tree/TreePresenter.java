package com.kristina.gwttreecrud.client.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
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
import com.kristina.gwttreecrud.client.tree.TreeView.NodeTreeViewHandler;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreePresenter
        implements NodeUpdatedEventHandler, NodeAddedEventHandler, DeleteNodeEventHandler, ClearSelectionEventHandler {
    private GwtServiceAsync service = GWT.create(GwtService.class);
    private TreeView view;
    private Map<Integer, TreeNode> loadedNodes;//ключ айди и значение нода
    private List<TreeViewData> viewNodes;
    private Set<Integer> expandedNodeIds;//то что прям щас раскрыто на экране!
    private TreeNode selectedNode;

    public TreePresenter(TreeView view) {
        this.view = view;
        this.viewNodes = new ArrayList<TreeViewData>();
        this.expandedNodeIds = new HashSet<Integer>();
        this.loadedNodes = new HashMap<Integer, TreeNode>();

        view.setHandler(new NodeTreeViewHandler() {
            @Override
            public void onCollapseNode(Integer id) {
                collapseNode(id);
            }
            @Override
            public void onExpandNode(Integer id) {
                expandNode(id);
            }
            @Override
            public void onSelectNode(Integer id) {
                selectNode(id);
            }
        });

        AppEventBus.get().addHandler(NodeUpdatedEvent.TYPE, this);
        AppEventBus.get().addHandler(NodeAddedEvent.TYPE, this);
        AppEventBus.get().addHandler(DeleteNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);

    }

    public void loadRoots() {
        service.getAllRoots(new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> roots) {
                for (TreeNode node : roots) {
                    loadedNodes.put(node.getId(), node);
                }
                rebuildViewNodes();
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки корневых нод", caught);
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
    //при нажатии на + пользователем срабатывает
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

    //сворачивание ноды(nodeId - кого свернули)
    public void collapseNode(Integer nodeId) {
        expandedNodeIds.remove(nodeId);
        removeExpandedDescendants(nodeId);
        if (selectedNode != null && isDescendant(selectedNode.getId(), nodeId)) {
            AppEventBus.get().fireEvent(new ClearSelectionEvent());
        }
        refreshTree();
    }

    private void removeExpandedDescendants(Integer nodeId) {
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
    
    //Является ли выбранная нода потомком той ноды, которую сейчас свернули?
    private boolean isDescendant(Integer selectedNodeId, Integer collapsedNodeId) {
        TreeNode node = findNodeById(selectedNodeId);

        if (node == null) {
            return false;
        }
        
        Integer parentId = node.getParentId();
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

    public void selectNode(Integer nodeId) {
        TreeNode node = findNodeById(nodeId);
        if (node == null) {
            return;
        }
        selectedNode = node;
        AppEventBus.get().fireEvent(new NodeSelectedEvent(node));
        refreshTree();
    }

    private TreeNode findNodeById(Integer nodeId) {
        return loadedNodes.get(nodeId);
    }

    public void updateNodeName(Integer nodeId, String name) {
        TreeNode node = findNodeById(nodeId);
        if (node == null) {
            return;
        }
        node.setName(name);
        rebuildViewNodes();
    }
    
    private Set<Integer> findDescendantIds(Integer nodeId) {
        TreeNode node = loadedNodes.get(nodeId);
        Set<Integer> descendantIds = new HashSet<Integer>();
        
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
        TreeNode node = event.getNode();
        updateNodeName(node.getId(), node.getName());
    }

    @Override
    public void nodeAdded(NodeAddedEvent event) {
        TreeNode node = event.getNode();
        
        //если новая нода - корень
        if (node.getParentId() == null) {
            loadedNodes.put(node.getId(), node);
            rebuildViewNodes();
            return;
        }
        TreeNode parent = findNodeById(node.getParentId());
        //если родителя до этого не раскрывали
        if (parent == null) {
            return;
        }
        parent.setHasChildren(true);
        //если родителя до этого уже раскрывали
        if (parent.getChildren() != null) {
            parent.getChildren().add(node);
            loadedNodes.put(node.getId(), node);
        }
        rebuildViewNodes();
    }

    @Override
    public void deleteNode(DeleteNodeEvent event) {
        Integer nodeId = event.getNodeId();//айди удаленной ноды
        TreeNode node = findNodeById(nodeId);//сама удаляемая нода
        
        if (node == null){
            return;
        }
        
        Set<Integer> idsToRemove = findDescendantIds(nodeId);
        idsToRemove.add(nodeId);
        
        for (Integer id : idsToRemove) {
            loadedNodes.remove(id);
        }
        //удаление ноды из children его родителя
        Integer parentId = node.getParentId();
        if (parentId != null) {
            TreeNode parent = findNodeById(parentId);
            if (parent != null && parent.getChildren() != null) {
                List<TreeNode> children = parent.getChildren();
                for (int i=0; i < children.size(); i++) {
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
        
        //Была ли сейчас выбрана нода, которую мы только что удалили?
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
