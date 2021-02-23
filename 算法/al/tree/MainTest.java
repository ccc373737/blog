package com.ccc.fizz.al.tree;

public class MainTest {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5, new TreeNode(7), new TreeNode(8))), new TreeNode(3, new TreeNode(6, new TreeNode(9), new TreeNode(10)), null));
        TreeNode node2 = new TreeNode(1, new TreeNode(2, null, new TreeNode(4)), new TreeNode(3));
        PathTest test = new PathTest();
        test.pathSum6(new TreeNode(1), 0);
        //test.buildTree2(new int[]{1, 2, 4, 5, 7, 10, 11, 14, 16 ,17 ,18},new int[]{1 ,4, 7, 5, 2, 11, 16, 18, 17, 14, 10});
        //TreeNode treeNode = test.recoverFromPreorder("1-2--3--4-5--6--7");


        //SeriaTest.Node root = new SeriaTest.Node(1, Lists.newArrayList(new SeriaTest.Node(3, Lists.newArrayList(new SeriaTest.Node(5), new SeriaTest.Node(6))), new SeriaTest.Node(2), new SeriaTest.Node(4)));

        //SeriaTest.Node node1 = test.deserialize2(test.serialize2(root));
        //System.out.println(test.distanceK(node1, ));

        System.out.println(Math.pow(2, 5));
    }
}
