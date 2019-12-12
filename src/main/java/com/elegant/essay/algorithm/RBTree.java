package com.elegant.essay.algorithm;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-16 14:30
 */
public class RBTree {

    /**
     * 根节点
     */
    private static Node root;

    /**
     * nil节点是红黑树的叶子节点不同于二叉树的叶子节点
     * 颜色为黑色，key、left、right、parent可以是任意允许的值
     * 这里key设置为0，left、right、parent为null
     */
    private Node nil = new Node(true);

    static class Node {
        int key;
        Node left, right, parent;
        /*** true黑，false红*/
        boolean color;

        public Node(int key) {
            this.key = key;
        }

        public Node(boolean color) {
            this.color = color;
        }

        public boolean equals(Node node) {
            return this.key == node.key;
        }
    }

    public RBTree(int key) {
        root = new Node(key);
    }

    /**
     * 中序遍历
     *
     * @param node 根节点
     */
    public void inOrderTreeWalk(Node node) {
        if (node != null && !node.equals(nil)) {
            inOrderTreeWalk(node.left);
            System.out.print((node.color == true ? "黑" : "红") + node.key + ",");
            inOrderTreeWalk(node.right);
        }
    }

    /**
     * 查找
     *
     * @param node 根节点
     * @param key  查找值
     * @return
     */
    public Node treeSearch(Node node, int key) {
        while (node != null && key != node.key) {
            if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    /**
     * 最小值
     *
     * @param node 根节点
     * @return
     */
    public Node treeMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 最大值
     *
     * @param node 根节点
     * @return
     */
    public Node treeMaximum(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * 前驱
     *
     * @param node 根节点
     * @return
     */
    public Node treePredecessor(Node node) {
        // 如果存在左子树，返回左子树的最大值
        if (node.left != null) {
            return treeMaximum(node.left);
        }
        Node y = node.parent;
        // 当不存在左子树时，返回最低祖先节点
        while (y != null && node == y.left) {
            node = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * 后继
     *
     * @param node 根节点
     * @return
     */
    public Node treeSuccessor(Node node) {
        // 如果存在右子树，返回右子树的最小值
        if (node.right != null) {
            return treeMinimum(node.right);
        }
        Node y = node.parent;
        // 当不存在右子树时，返回最低祖先节点
        while (y != null && node == y.right) {
            node = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * 左旋（node节点必有右孩子）
     *
     * @param node
     */
    public void leftTotate(Node node) {
        Node y = node.right;
        node.right = y.left;
        if (y.left != null) {
            y.left.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            root = y;
        } else if (node == node.parent.left) {
            node.parent.left = y;
        } else {
            node.parent.right = y;
        }
        node.parent = y;
        y.left = node;
    }

    /**
     * 右旋（node节点必有左孩子）
     *
     * @param node
     */
    public void rightTotate(Node node) {
        Node y = node.left;
        node.left = y.right;
        if (y.right != null) {
            y.right.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            root = y;
        } else if (node == node.parent.left) {
            node.parent.left = y;
        } else {
            node.parent.right = y;
        }
        node.parent = y;
        y.right = node;
    }

    /**
     * 插入
     *
     * @param key 插入节点的关键值
     */
    public void RBTreeInsert(int key) {
        // 创建插入节点
        Node node = new Node(key);
        // 定义插入节点的父节点变量
        Node y = null;
        // 定义临时变量存根节点
        Node x = root;
        // 在根节点的左、右子树中查找插入位置
        while (x != null) {
            y = x;
            if (key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (key < y.key) {
            y.left = node;
        } else {
            y.right = node;
        }
        RBTreeInsertFixup(node);
    }

    /**
     * 插入后修复
     *
     * @param node 插入节点
     */
    public void RBTreeInsertFixup(Node node) {
        // 当插入节点的父节点为红色时，执行循环
        while (node.parent != null && !node.parent.color && node.parent.parent != null) {
            // 当插入节点的父节点为其爷爷节点的左孩子时
            if (node.parent == node.parent.parent.left) {
                // 定义y存叔叔节点
                Node y = node.parent.parent.right;
                // 如果叔叔节点为红色，将父节点与叔叔节点变成黑色，爷爷节点变成红色，将插入节点升级为爷爷节点
                if (y != null && !y.color) {
                    node.parent.color = true;
                    y.color = true;
                    node.parent.parent.color = false;
                    node = node.parent.parent;
                    //如果叔叔节点为黑色，插入节点是父节点的右孩子，将插入节点升级为父节点，左旋插入节点
                } else if (node == node.parent.right) {
                    node = node.parent;
                    leftTotate(node);
                } else {
                    //如果叔叔节点为黑色，插入节点是父节点的左孩子，将父节点变成黑色，爷爷节点变成红色，右旋爷爷节点
                    node.parent.color = true;
                    node.parent.parent.color = false;
                    rightTotate(node.parent.parent);
                }
            } else {// 当插入节点的父节点为其爷爷节点的右孩子时
                // 定义y存叔叔节点
                Node y = node.parent.parent.left;
                // 如果叔叔节点为红色，将父节点与叔叔节点变成黑色，爷爷节点变成红色，将插入节点升级为爷爷节点
                if (y != null && !y.color) {
                    node.parent.color = true;
                    y.color = true;
                    node.parent.parent.color = false;
                    node = node.parent.parent;
                    //如果叔叔节点为黑色，插入节点是父节点的左孩子，将插入节点升级为父节点，右旋插入节点
                } else if (node == node.parent.left) {
                    node = node.parent;
                    rightTotate(node);
                } else {//如果叔叔节点为黑色，插入节点是父节点的右孩子，将父节点变成黑色，爷爷节点变成红色，左旋爷爷节点
                    node.parent.color = true;
                    node.parent.parent.color = false;
                    leftTotate(node.parent.parent);
                }
            }
        }
        // 将根节点变成黑色
        if (root.parent != null) {
            root = root.parent;
        }
        root.color = true;
    }

    /**
     * 删除
     *
     * @param node 删除节点
     * @return
     */
    public Node RBTreeDelete(Node node) {
        // 定义临时变量存删除节点或后继节点
        Node y;
        // 当删除节点至多有一个孩子时
        if (node.left == null || node.right == null) {
            y = node;
        } else {// 当删除节点有两个孩子时，y存后继节点
            y = treeSuccessor(node);
        }
        // 定义临时变量存删除节点的孩子节点
        Node x;
        if (y.left != null) {
            x = y.left;
        } else {
            x = y.right;
        }
        if (x != null) {
            x.parent = y.parent;
        } else {
            x = nil;
            x.parent = y.parent;
        }
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        // 当y为后继节点时，将y的关键值赋给删除节点
        if (y != node) {
            node.key = y.key;
        }
        // 当y为黑色时，需要修复红黑树
        if (y.color) {
            RBTreeDeleteFixup(x);
        }
        return y;
    }

    /**
     * 删除后修复
     *
     * @param node 删除节点的孩子节点
     */
    public void RBTreeDeleteFixup(Node node) {
        // 当node不等于根节点并且为黑色时，执行循环
        while (node != root && (node == nil || node.color)) {
            // 如果node节点为父节点的左孩子
            if (node == node.parent.left) {
                // 定义w存兄弟节点
                Node w = node.parent.right;
                // 当兄弟节点为红色时，将兄弟节点变成黑色，父节点变成红色，左旋父节点，更新兄弟节点
                if (!w.color) {
                    w.color = true;
                    node.parent.color = false;
                    leftTotate(node.parent);
                    w = node.parent.right;
                    //当兄弟节点为黑色且其两个孩子都为黑色时，将兄弟节点变成红色，将node节点升级为父节点
                } else if (w.left.color && w.right.color) {
                    w.color = false;
                    node = node.parent;
                    //当兄弟节点为黑色且其左孩子为红色、其右孩子为黑色时，将其左孩子变成黑色、兄弟节点变成红色，右旋兄弟节点，更新兄弟节点
                } else if (w.right.color) {
                    w.left.color = true;
                    w.color = false;
                    rightTotate(w);
                    w = node.parent.right;
                } else {//当兄弟节点为黑色且其右孩子为红色时，将父节点的颜色赋给兄弟节点，父节点变成黑色，兄弟节点的右孩子变成黑色，左旋父节点
                    w.color = node.parent.color;
                    node.parent.color = true;
                    w.right.color = true;
                    leftTotate(node.parent);
                    // 将根节点赋给node
                    if (root.parent != null) {
                        root = root.parent;
                    }
                    node = root;
                }
            } else {// 如果node节点为父节点的右孩子
                // 定义w存兄弟节点
                Node w = node.parent.left;
                // 当兄弟节点为红色时，将兄弟节点变成黑色，父节点变成红色，右旋父节点，更新兄弟节点
                if (!w.color) {
                    w.color = true;
                    node.parent.color = false;
                    rightTotate(node.parent);
                    w = node.parent.left;
                    //当兄弟节点为黑色且其两个孩子都为黑色时，将兄弟节点变成红色，将node节点升级为父节点
                } else if (w.left.color && w.right.color) {
                    w.color = false;
                    node = node.parent;
                    //当兄弟节点为黑色且其左孩子为黑色、其右孩子为红色时，将其右孩子变成黑色、兄弟节点变成红色，左旋兄弟节点，更新兄弟节点
                } else if (w.left.color) {
                    w.right.color = true;
                    w.color = false;
                    leftTotate(w);
                    w = node.parent.left;
                } else {
                    //当兄弟节点为黑色且其左孩子为红色时，将父节点的颜色赋给兄弟节点，父节点变成黑色，兄弟节点的左孩子变成黑色，右旋父节点
                    w.color = node.parent.color;
                    node.parent.color = true;
                    w.left.color = true;
                    rightTotate(node.parent);
                    // 将根节点赋给node
                    if (root.parent != null) {
                        root = root.parent;
                    }
                    node = root;
                }
            }
        }
        // 将node节点变成黑色
        node.color = true;
    }

    public static void main(String[] args) {
        int[] arr = {21, 3, 6, 7, 12, 25, 17, 8, 15};
        RBTree rb = new RBTree(21);
        for (int i = 1; i < arr.length; i++) {
            rb.RBTreeInsert(arr[i]);
        }
        rb.inOrderTreeWalk(root);
        rb.RBTreeDelete(rb.treeSearch(root, 21));
        System.out.println();
        rb.inOrderTreeWalk(root);
    }

}