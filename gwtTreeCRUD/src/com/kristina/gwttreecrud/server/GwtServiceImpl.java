package com.kristina.gwttreecrud.server;

import java.util.List;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.shared.TreeNode;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GwtServiceImpl extends RemoteServiceServlet implements
        GwtService {

    @Override
    public String processCall(String payload) throws SerializationException {
        try {
            System.out.println("===============STAAAAAAAAAAAAAAAAART================");
            return super.processCall(payload);
        } catch (Throwable e) {
            // TODO: handle exception
            System.out.println("===============ERRRROOOOOORRRRR================");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<TreeNode> getAllNodes() throws IllegalArgumentException {
        TreeNodeServiceImp service = new TreeNodeServiceImp();
        return service.findAll();
    }
    
    @Override
    public TreeNode findById(Integer id) throws IllegalArgumentException {
        TreeNodeServiceImp service = new TreeNodeServiceImp();
        try {
            return service.findById(id);
        }   catch (Exception e) {
            throw new IllegalArgumentException("Ошибка поиска TreeNode", e);
        }
    }
    
    @Override
    public void updateNode(TreeNode node) throws IllegalArgumentException {
        TreeNodeServiceImp service = new TreeNodeServiceImp();
        service.updateNode(node);
    }
    
    @Override
    public void insertNode(TreeNode node) throws IllegalArgumentException {
        TreeNodeServiceImp service = new TreeNodeServiceImp();
        service.insertNode(node);
    }
    
    @Override
    public void deleteById(Integer id) throws IllegalArgumentException {
        TreeNodeServiceImp service = new TreeNodeServiceImp();
        service.deleteById(id);
    }
}
