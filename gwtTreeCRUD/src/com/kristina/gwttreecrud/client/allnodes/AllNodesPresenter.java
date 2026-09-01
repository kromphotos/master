package com.kristina.gwttreecrud.client.allnodes;

import java.util.ArrayList;
import java.util.List;

import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesPresenter {
    private AllNodesView view;
    
    public AllNodesPresenter(AllNodesView view) {
        this.view = view;
    }
 
    public List<AllNodesViewData> convertToData(List<TreeNode> nodes){
        List<AllNodesViewData> newNodes = new ArrayList<>();
        if (nodes != null ) {
            for (TreeNode node : nodes) {
                newNodes.add(new AllNodesViewData(node.getId(),node.getParentId(),node.getName(),node.getIp(),node.getPort()));
            }
        }
        return newNodes;
    }
    
    public void refreshNodes(List<TreeNode> nodes) {
        view.showNodes(convertToData(nodes));
    }

}
