package com.kristina.treecrud.service;
import java.util.List;

import com.kristina.treecrud.bean.TreeNode;

public interface TreeNodeService {
    List<TreeNode> findAll();
    TreeNode findById(Integer id) throws Exception;
    void deleteById(Integer id);
    void updateNode(TreeNode node);
    void insertNode(TreeNode node);
}
