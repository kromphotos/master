package com.kristina.gwttreecrud.client.tree;

import java.util.List;
import java.util.Set;

public interface TreeInterface {
    void showTree(List<TreeViewData> nodes,
            Set<Integer> expandedNodeIds,
            TreeViewData selectedNode);
}
