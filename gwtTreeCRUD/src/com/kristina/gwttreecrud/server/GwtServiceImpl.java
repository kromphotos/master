package com.kristina.gwttreecrud.server;

import java.util.List;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
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
    public List<TreeNode> getAllNodes() throws TreeCrudProgramException {
        return service.findAll();
    }

    @Override
    public TreeNode findById(Integer id) throws TreeCrudProgramException {
        try {
            return service.findById(id);
        } catch (Exception e) {
            throw new TreeCrudProgramException("Ошибка поиска TreeNode", e);
        }
    }

    @Override
    public TreeNode updateNode(TreeNode node) throws TreeCrudProgramException {
        return service.updateNode(node);
    }

    @Override
    public TreeNode insertNode(TreeNode node) throws TreeCrudProgramException {
        return service.insertNode(node);
    }

    @Override
    public void deleteById(Integer id) throws TreeCrudProgramException {
        service.deleteById(id);
    }

    @Override
    public List<TreeNode> getAllChildById(Integer parentId) throws TreeCrudProgramException {
        return service.getAllChildById(parentId);
    }
    
    @Override
    public List<TreeNode> getAllRoots() throws TreeCrudProgramException {
        return service.getAllRoots();
    }

}
