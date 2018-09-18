package edu.neu.practice.ibm;

import edu.princeton.cs.algs4.*;

import java.lang.reflect.Array;
import java.util.*;

public class CitySquares {
    public static int findBestPath(int n, int m, int max_t,
                                   List<Integer> beauty,
                                   List<Integer> u,
                                   List<Integer> v,
                                   List<Integer> t){
        int[][] map = new int[n][n];
        for(int i = 0; i < m; i ++){
            map[u.get(i)][v.get(i)] = t.get(i);
            map[v.get(i)][u.get(i)] = t.get(i);
        }
        Map<Integer, Integer> distFromHotelTo = dijskra(map, 0);
        System.out.println(distFromHotelTo.size());
        int[] distFromHotel = new int[n];
        for(int i = 0; i < n; i ++){
            distFromHotel[i] = distFromHotelTo.getOrDefault(i, 0);
            System.out.println(distFromHotel[i]);
        }
        int[] res = new int[1];
        dfs(map, distFromHotel, new boolean[n],0, max_t, 0, res, beauty);
        return res[0];
    }

    public static void dfs(int[][] map, int[] distFromHotel, boolean[] visited,
                           int curr,int max_t, int max_b, int[] res,
                           List<Integer> beauty){
        if(distFromHotel[curr] > max_t){
            return;
        }
        res[0] = Math.max(max_b, res[0]);
        for(int i = 0; i < map.length; i ++) {
            if (map[curr][i] == 0)
                continue;
            if (visited[i]) {
                dfs(map, distFromHotel, visited, i, max_t - map[curr][i], max_b, res, beauty);
            } else {
                visited[i] = true;
                dfs(map, distFromHotel, visited, i, max_t - map[curr][i], max_b + beauty.get(i), res, beauty);
                visited[i] = false;
            }
        }
    }

    private static Map<Integer, Integer> dijskra(int[][] map, int start){
        PriorityQueue<int[]> q = new PriorityQueue<int[]>(new Comparator<int[]>(){
            @Override
            public int compare(int[] i1, int[] i2){
                return i1[0] - i2[0];
            }
        });
        q.offer(new int[]{0, start});
        Map<Integer, Integer> distTo = new HashMap();
        while(!q.isEmpty()){
            int[] edge = q.poll();
            int dist = edge[0];
            int v = edge[1];
            if (distTo.containsKey(v)) continue;
            distTo.put(v, dist);
            for(int i = 0; i < map.length;  i++) {
                if (map[v][i] == 0)
                    continue;
                if (!distTo.containsKey(i))
                    q.offer(new int[]{dist + map[v][i], i});
            }
        }
        return distTo;
    }

    public static void main(String[] args) {
        System.out.println(findBestPath(4,3,49, Arrays.asList(0,32,10,43),
                Arrays.asList(0, 2, 0),
                Arrays.asList(1, 0, 3),
                Arrays.asList(10,13,17)));
        System.out.println(findBestPath(4,3,49, Arrays.asList(5,10,15,20),
                Arrays.asList(0, 2, 0),
                Arrays.asList(1, 1, 3),
                Arrays.asList(6, 7,20)));
    }
}
