ALTER TABLE tree_node DROP CONSTRAINT tree_node_parentId_fk;
ALTER TABLE tree_node DROP CONSTRAINT tree_node_pk;


DROP TABLE tree_node;

DROP SEQUENCE tree_node_SEQ;

CREATE SEQUENCE tree_node_SEQ START 1 INCREMENT BY 1;

CREATE TABLE tree_node(
    id BIGINT DEFAULT NEXTVAL('tree_node_SEQ'),
    parent_id BIGINT, 
    name VARCHAR(30) NOT NULL,
    ip VARCHAR(15) NOT NULL,
    port INTEGER NOT NULL
);

ALTER TABLE tree_node ADD CONSTRAINT tree_node_pk PRIMARY KEY(id);
ALTER TABLE tree_node ADD CONSTRAINT tree_node_parentId_fk FOREIGN KEY(parent_id) REFERENCES tree_node(id) ON DELETE CASCADE;

INSERT INTO tree_node(parent_id,name,ip,port) 
VALUES (NULL,'rootNode','127.0.0.1',1111),
(1,'childNode1','127.0.5.1',1001),
(1,'childNode2','127.0.0.1',1411),
(2,'childNode3','127.6.0.1',1111),
(3,'childNode4','127.0.9.1',7711),
(1,'childNode5','127.0.12.1',9911);

SELECT * FROM tree_node WHERE id = :id;

SELECT * FROM tree_node;

DELETE FROM tree_node WHERE id = :id;

UPDATE tree_node
SET name = :name, ip = :ip, port = :port
WHERE id = :id;

INSERT INTO tree_node(parent_id,name,ip,port) VALUES (:parent_id, :name, :ip, :port);