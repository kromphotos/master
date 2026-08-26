package com.kristina.gwttreecrud.client.allnodes;
import java.util.List;

import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesViewData {
    private List<TreeNode> nodes;
    public List<TreeNode> getNodes(){
        return nodes;
    }
    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }
}
