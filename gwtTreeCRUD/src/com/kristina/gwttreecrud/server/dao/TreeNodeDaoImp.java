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
    public TreeNode findById(Integer id) {
        return mapper.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
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
}
