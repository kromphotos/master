package com.kristina.gwttreecrud.client.allnodes;

import java.util.List;

import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesPresenter {
    private AllNodesView view;
    private AllNodesViewData viewData;
 
    public AllNodesPresenter(AllNodesView view,
            AllNodesViewData viewData) {
        this.view = view;
        this.viewData = viewData;
    }
    public void refreshNodes(List<TreeNode> nodes) {
        viewData.setNodes(nodes);
        view.showNodes(viewData.getNodes());
    }

}
