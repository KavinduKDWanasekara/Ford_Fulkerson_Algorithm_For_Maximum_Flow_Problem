package com.company;

// Student ID   : 20191067
// Student Name : Kavindu Wanasekara
// UoW Number   : w1790320

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.LinkedList;

class MaximumFlowFinder {
    private static int V;


    boolean breadthFirstSearch(int residualGraph[][], int s, int t, int parent[])
    {

        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;


        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;


        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false
                        && residualGraph[u][v] > 0) {
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }


        return false;
    }


    int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;

        int residualGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                residualGraph[u][v] = graph[u][v];

        int parent[] = new int[V];

        int max_flow = 0;

        while (breadthFirstSearch(residualGraph, s, t, parent)) {

            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }

            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;
            }


            max_flow += path_flow;
            printPath(parent,path_flow);
            System.out.println("");
        }


        return max_flow;
    }

    public void printPath(int[] parent,int flow){

        int mapIndex= parent.length-1;
        int node=V-1;
        while (node!=0){
            System.out.print(node+" <- ");
            node=parent[mapIndex];
            mapIndex=node;
        }
        System.out.print(0+" | flow:"+flow);


    }


    private static int[][] fileReader(String fileName){
        try {

            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            V = Integer.parseInt(myReader.nextLine());

            int[][] dataset=new int[MaximumFlowFinder.V][MaximumFlowFinder.V];
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                List<String> lst = List.of(data.split(" "));
                int capacity=Integer.parseInt(lst.get(2));
                int nodeOne=Integer.parseInt(lst.get(0));
                int nodeTwo=Integer.parseInt(lst.get(1));

                dataset[nodeOne][nodeTwo]=capacity;



            }
            myReader.close();
            return dataset;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws FileNotFoundException

    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file name of the Benchmark(bridge_x or ladder_y): ");
        String fileName = scanner.nextLine();
        //File path should be changed according to your device
        int[][] data=fileReader("D://YEAR 2//SEMESTER 2//Algorithms Theory, Design and Implementation//w1790320//Algo-CW-Final//src//Benchmarks//"+fileName+".txt");
        MaximumFlowFinder m = new MaximumFlowFinder();

        long startTime = System.currentTimeMillis();
        System.out.println("Maximum possible flow : " + m.fordFulkerson(data, 0, V-1));
        long endTime = System.currentTimeMillis();
        double timeElapsed = (endTime - startTime)/1000.0;
        System.out.println("Execution time in milliseconds: " + timeElapsed );    }

}

