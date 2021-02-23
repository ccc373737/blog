package com.ccc.fizz.al;

public class Al17 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
    //
    //B是A的子结构， 即 A中有出现和B相同的结构和节点值。
    public static void main(String[] args) {
        TreeNode A = new TreeNode(4);
        A.right = new TreeNode(9);
        A.left = new TreeNode(8);

        TreeNode B = new TreeNode(4);
        B.right = new TreeNode(9);
        B.left = new TreeNode(8);

        System.out.println(isSubStructure(A, B));
    }

    public static boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }

        return isSame(A,B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
        /*if (A.val == B.val && isSame(A.left, B.left) && isSame(A.right, B.right)) {//相等 向下遍历
            return true;
        } else {
            return isSubStructure(A.left, B) || isSubStructure(A.right, B);
        }*/
    }

    public static boolean isSame(TreeNode A, TreeNode B) {
        //B遍历完成 返回true
        if (B == null) {
            return true;
        }

        //B未遍历完成而A完成，或者AB值不同，返回false
        if (A == null || A.val != B.val) {
            return false;
        }

        //比较AB的子节点
        return isSame(A.left, B.left) && isSame(A.right, B.right);
        /*else if (B != null && A != null) {//A和B都存在
            return A.val == B.val && isSame(A.left, B.left) && isSame(A.right, B.right);
        } else {
            return false;
        }*/
    }


}
