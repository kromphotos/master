package com.kristina.gwttreecrud.client.tree;

import java.util.List;
import java.util.Set;

public interface TreeInterface {
    interface NodeTreeViewHandler {
        void onCollapseNode(Integer id);
        void onExpandNode(Integer id);
        void onSelectNode(Integer id);
    };
    void setHandler(NodeTreeViewHandler handler);
    void showTree(List<TreeViewData> nodes,
            Set<Integer> expandedNodeIds,
            TreeViewData selectedNode);
}
