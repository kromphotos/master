package com.kristina.treecrud.mapper;
import java.util.List;

import com.kristina.treecrud.bean.TreeNode;

public interface NodeMapper {
    List<TreeNode> findAll();
    TreeNode findById(Integer id);
    void deleteById(Integer id);
    void updateNode(TreeNode node);
    void insertNode(TreeNode node);
}
