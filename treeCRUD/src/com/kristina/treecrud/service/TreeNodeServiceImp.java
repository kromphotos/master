package com.kristina.treecrud.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kristina.treecrud.bean.TreeNode;
import com.kristina.treecrud.mapper.NodeMapper;

public class TreeNodeServiceImp implements TreeNodeService {
    private final static String RESOURCE = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try (InputStream inputStream = Resources.getResourceAsStream(RESOURCE)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TreeNode> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            return mapper.findAll();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    @Override
    public TreeNode findById(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            return mapper.findById(id);
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            mapper.deleteById(id);
            session.commit();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void updateNode(TreeNode node) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            mapper.updateNode(node);
            session.commit();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void insertNode(TreeNode node) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            mapper.insertNode(node);
            session.commit();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
