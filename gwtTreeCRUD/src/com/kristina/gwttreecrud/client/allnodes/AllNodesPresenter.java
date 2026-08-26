package com.kristina.gwttreecrud.client.allnodes;

import java.util.List;

//import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.kristina.gwttreecrud.client.GwtService;
//import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesPresenter {
    private AllNodesView view;
    private AllNodesViewData viewData;
    //объект, который умеет работать с GwtService
    //private GwtServiceAsync service = GWT.create(GwtService.class);

    public AllNodesPresenter(AllNodesView view,
            AllNodesViewData viewData) {
        this.view = view;
        this.viewData = viewData;
    }
    /*
    public void loadNodes() {
        //объект, который умеет обработать результат асинхронного запроса
        AsyncCallback<List<TreeNode>> callback = new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                viewData.setNodes(nodes);
                view.showNodes(viewData.getNodes());
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки TreeNode", caught);
            }
        };
        service.getAllNodes(callback);//обращение к серверу. выполнение на стороне сервера и запись рез-та в коллбэк
    }
    */
    
    public void refreshNodes(List<TreeNode> nodes) {
        viewData.setNodes(nodes);
        view.showNodes(viewData.getNodes());
    }

}
