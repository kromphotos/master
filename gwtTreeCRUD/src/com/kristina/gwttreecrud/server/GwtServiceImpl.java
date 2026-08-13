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

}
