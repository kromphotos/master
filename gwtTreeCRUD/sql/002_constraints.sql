ALTER TABLE tree_node ADD CONSTRAINT tree_node_pk PRIMARY KEY(id);
ALTER TABLE tree_node ADD CONSTRAINT tree_node_parentId_fk FOREIGN KEY(parent_id) REFERENCES tree_node(id) ON DELETE CASCADE;
