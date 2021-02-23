package com.ccc.fizz.al.tree;

public class FindAndUnionTest {
    //684. 冗余连接
    /*输入: [[1,2], [1,3], [2,3]]
    输出: [2,3]
    解释: 给定的无向图为:
            1
            / \
            2 - 3*/
    //查并集问题，两个节点不属于一个联通向量，那么合并，如果属于表示这条边就是冗余连接
    public int[] findRedundantConnection(int[][] edges) {
        init(edges);

        for (int[] edge : edges) {
            if (find(edge[0]) == find(edge[1])) {
                return edge;
            } else {
                union(edge[0], edge[1]);
            }
        }
        return null;
    }

    int[] numbers;

    public void init(int[][] edges) {
        //初始化
        numbers = new int[edges.length + 1];
        for (int i = 1; i < numbers.length; i++) {
            numbers[i] = i;
        }
    }

    public int find(int target) {
        while (target != numbers[target]) {
            numbers[target] = numbers[numbers[target]];
            target = numbers[target];
        }
        return target;
    }

    public void union(int p, int q) {
        p = find(p);
        q = find(q);
        if (p == q) {
            return;
        }
        numbers[p] = q;
    }

    //685. 冗余连接 II
    //输入: [[1,2], [2,3], [3,4], [4,1], [1,5]]
    //输出: [4,1]
    //解释: 给定的有向图如下:
    //5 <- 1 -> 2
    //     ^    |
    //     |    v
    //     4 <- 3
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int length = edges.length;
        //初始化度数
        int[] degree = new int[length + 1];
        for (int[] edge : edges) {
            degree[edge[1]]++;
        }
        //第一种情况 有入度为2的节点 这里要倒序，因为多个符合条件，则删除靠后的
        for (int i = length - 1; i >= 0; i--) {
            if (degree[edges[i][1]] == 2 && isTree(edges, edges[i])) {
                return edges[i];
            }
        }

        //第二种情况 没有入度为2的节点
        for (int i = length - 1; i > 0; i--) {
            if (degree[edges[i][1]] == 1 && isTree(edges, edges[i])) {
                return edges[i];
            }
        }
        return null;
    }

    public boolean isTree(int[][] edges, int[] deleteEdge) {
        init(edges);
        for (int[] edge : edges) {
            if (edge == deleteEdge) {//跳过要删除的
                continue;
            }

            if (find(edge[0]) == find(edge[1])) {
                return false;
            } else {
                union(edge[0], edge[1]);
            }
        }
        return true;
    }
}
