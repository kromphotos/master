package com.kristina.gwttreecrud.client.tree;

import java.util.List;
import java.util.Set;

public interface TreeInterface {
    interface NodeTreeViewHandler {
        void onCollapseNode(Long id);

        void onExpandNode(Long id);

        void onSelectNode(Long id);
    };

    void setHandler(NodeTreeViewHandler handler);

    void showTree(List<TreeViewData> nodes,
            Set<Long> expandedNodeIds,
            TreeViewData selectedNode);
}
