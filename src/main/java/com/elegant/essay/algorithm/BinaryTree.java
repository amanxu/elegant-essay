package com.elegant.essay.algorithm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-15 19:54
 */
public class BinaryTree<T> {

    /**
     * 先序创建二叉树
     * 返回：根节点
     *
     * @param treeData
     * @return
     */
    public TreeNode<T> createBinaryPre(LinkedList<T> treeData) {
        TreeNode<T> root = null;
        T data = treeData.removeFirst();
        if (data != null) {
            root = new TreeNode<T>(data, null, null);
            root.left = createBinaryPre(treeData);
            root.right = createBinaryPre(treeData);
        }
        return root;
    }

    /**
     * 先序遍历二叉树（递归）
     *
     * @param root
     */
    public void PrintBinaryTreePreRecur(TreeNode<T> root) {
        if (root != null) {
            System.out.print(root.data);
            PrintBinaryTreePreRecur(root.left);
            PrintBinaryTreePreRecur(root.right);
        }
    }

    /**
     * 中序遍历二叉树（递归）
     *
     * @param root
     */
    public void PrintBinaryTreeMidRecur(TreeNode<T> root) {
        if (root != null) {
            PrintBinaryTreeMidRecur(root.left);
            System.out.print(root.data);
            PrintBinaryTreeMidRecur(root.right);
        }
    }

    /**
     * 后序遍历二叉树（递归）
     *
     * @param root
     */
    public void PrintBinaryTreeBacRecur(TreeNode<T> root) {
        if (root != null) {
            PrintBinaryTreeBacRecur(root.left);
            PrintBinaryTreeBacRecur(root.right);
            System.out.print(root.data);
        }
    }

    /**
     * 先序遍历二叉树（非递归）
     * 思路：对于任意节点T，访问这个节点并压入栈中，然后访问节点的左子树，
     * 遍历完左子树后，取出栈顶的节点T，再先序遍历T的右子树
     *
     * @param root
     */
    public void PrintBinaryTreePreUnrecur(TreeNode<T> root) {
        //p为当前节点
        TreeNode<T> p = root;
        //栈不为空时，或者p不为空时循环
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            //当前节点不为空。访问并压入栈中。并将当前节点赋值为左儿子
            if (p != null) {
                stack.push(p);
                System.out.print(p.data);
                p = p.left;
            }
            //当前节点为空：
            //  1、当p指向的左儿子时，此时栈顶元素必然是它的父节点
            //  2、当p指向的右儿子时，此时栈顶元素必然是它的爷爷节点 //取出栈顶元素，赋值为right
            else {
                p = stack.pop();
                p = p.right;
            }
        }
    }

    /**
     * 中序遍历二叉树（非递归）
     * <p>
     * 思路：先将T入栈，遍历左子树；遍历完左子树返回时，栈顶元素应为T，
     * 出栈，访问T->data，再中序遍历T的右子树。
     *
     * @param root
     */
    public void PrintBinaryTreeMidUnrecur(TreeNode<T> root) {
        //p为当前节点
        TreeNode<T> p = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 栈不为空时，或者p不为空时循环
        while (p != null || !stack.isEmpty()) {
            //当前节点不为空。压入栈中。并将当前节点赋值为左儿子
            if (p != null) {
                stack.push(p);
                p = p.left;
            }
            //当前节点为空：
            //  1、当p指向的左儿子时，此时栈顶元素必然是它的父节点
            //  2、当p指向的右儿子时，此时栈顶元素必然是它的爷爷节点
            // 取出并访问栈顶元素，赋值为right
            else {
                p = stack.pop();
                System.out.print(p.data);
                p = p.right;
            }
        }
    }

    /**
     * 后序遍历二叉树（非递归）
     *
     * @param root
     */
    public void PrintBinaryTreeBacUnrecur(TreeNode<T> root) {
        class NodeFlag<T> {
            TreeNode<T> node;
            char tag;

            public NodeFlag(TreeNode<T> node, char tag) {
                super();
                this.node = node;
                this.tag = tag;
            }
        }
        LinkedList<NodeFlag<T>> stack = new LinkedList<>();
        TreeNode<T> p = root;
        NodeFlag<T> bt; //栈不空或者p不空时循环
        while (p != null || !stack.isEmpty()) {
            //遍历左子树
            while (p != null) {
                bt = new NodeFlag(p, 'L');
                stack.push(bt);
                p = p.left;
            }
            //左右子树访问完毕访问根节点
            while (!stack.isEmpty() && stack.getFirst().tag == 'R') {
                bt = stack.pop();
                System.out.print(bt.node.data);
            }
            //遍历右子树
            if (!stack.isEmpty()) {
                bt = stack.peek();
                bt.tag = 'R';
                p = bt.node;
                p = p.right;
            }
        }
    }

    /**
     * 层次遍历二叉树（非递归）
     *
     * @param root
     */
    public void PrintBinaryTreeLayerUnrecur(TreeNode<T> root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        TreeNode<T> p;
        queue.push(root);
        while (!queue.isEmpty()) {
            p = queue.removeFirst();
            System.out.print(p.data);
            if (p.left != null) {
                queue.addLast(p.left);
            }
            if (p.right != null) {
                queue.addLast(p.right);
            }
        }
    }

    public static void main(String[] args) {
        //当天零点
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        String startTime = todayStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(startTime);
        //当天23点59分59秒
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        BinaryTree<Character> binaryTree = new BinaryTree<>();
        //输入ABDH##I##E##CF#J##G##（#用null代替）
        LinkedList<Character> tree = new LinkedList<>();
        tree.add('A');
        tree.add('B');
        tree.add('D');
        tree.add('H');
        tree.add(null);
        tree.add(null);
        tree.add('I');
        tree.add(null);
        tree.add(null);
        tree.add('E');
        tree.add(null);
        tree.add(null);
        tree.add('C');
        tree.add('F');
        tree.add(null);
        tree.add('J');
        tree.add(null);
        tree.add(null);
        tree.add('G');
        tree.add(null);
        tree.add(null);
        TreeNode<Character> root = binaryTree.createBinaryPre(tree);
        //先序遍历（递归）
        binaryTree.PrintBinaryTreePreRecur(root);
        System.out.println();
        //中序遍历（递归）
        binaryTree.PrintBinaryTreeMidRecur(root);
        System.out.println();
        //后序遍历（递归）
        binaryTree.PrintBinaryTreeBacRecur(root);
        System.out.println();
        //先序遍历（非递归）
        binaryTree.PrintBinaryTreePreUnrecur(root);
        System.out.println();
        //中序遍历（非递归）
        binaryTree.PrintBinaryTreeMidUnrecur(root);
        System.out.println();
        //后序遍历（非递归）
        binaryTree.PrintBinaryTreeBacUnrecur(root);
        System.out.println();
        //层次遍历（非递归）
        binaryTree.PrintBinaryTreeLayerUnrecur(root);
        System.out.println();
    }
}