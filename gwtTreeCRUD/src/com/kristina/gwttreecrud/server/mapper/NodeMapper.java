package com.kristina.gwttreecrud.server.mapper;
import java.util.List;

import com.kristina.gwttreecrud.shared.TreeNode;

public interface NodeMapper {
    List<TreeNode> findAll();
    TreeNode findById(Integer id);
    void deleteById(Integer id);
    void updateNode(TreeNode node);
    void insertNode(TreeNode node);
}
