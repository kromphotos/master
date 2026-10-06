package com.kristina.gwttreecrud.server.dao;

import java.util.List;

import com.kristina.gwttreecrud.server.mapper.NodeMapper;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreeNodeDaoImp implements TreeNodeDao {
    private NodeMapper mapper;

    public TreeNodeDaoImp(NodeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<TreeNode> findAll() {
        return mapper.findAll();
    }

    @Override
    public TreeNode findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public void updateNode(TreeNode node) {
        mapper.updateNode(node);
    }

    @Override
    public void insertNode(TreeNode node) {
        mapper.insertNode(node);
    }
    
    @Override
    public List<TreeNode> getAllChildById(Long parentId) {
        return mapper.getAllChildById(parentId);
    }
    
    @Override
    public List<TreeNode> getAllRoots() {
        return mapper.getAllRoots();
    }
}
