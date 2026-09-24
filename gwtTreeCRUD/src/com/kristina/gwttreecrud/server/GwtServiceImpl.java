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
public class GwtServiceImpl extends RemoteServiceServlet implements GwtService {
    private final TreeNodeService service = new TreeNodeServiceImp();

    @Override
    public String processCall(String payload) throws SerializationException {
        try {
            System.out.println("===============");
            return super.processCall(payload);
        } catch (Throwable e) {
            // TODO: handle exception
            System.out.println("===============");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<TreeNode> getAllNodes() throws IllegalArgumentException {
        return service.findAll();
    }

    @Override
    public TreeNode findById(Integer id) throws IllegalArgumentException {
        try {
            return service.findById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка поиска TreeNode", e);
        }
    }

    @Override
    public void updateNode(TreeNode node) throws IllegalArgumentException {
        service.updateNode(node);
    }

    @Override
    public TreeNode insertNode(TreeNode node) throws IllegalArgumentException {
        return service.insertNode(node);
    }

    @Override
    public void deleteById(Integer id) throws IllegalArgumentException {
        service.deleteById(id);
    }

    @Override
    public List<TreeNode> getAllChildById(Integer parentId) throws IllegalArgumentException {
        return service.getAllChildById(parentId);
    }
    
    @Override
    public List<TreeNode> getAllRoots() throws IllegalArgumentException {
        return service.getAllRoots();
    }

}
