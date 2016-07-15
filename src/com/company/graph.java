package com.company;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Collections;
import java.io.IOException;
import java.io.FileNotFoundException;
/**
 * Created by Robert - Tavi on 11.04.2016.
 */


public class graph {
    protected ArrayList<Integer> vertices;
    protected HashMap<Integer, ArrayList<Integer>> outbound;
    protected HashMap<Integer, ArrayList<Integer>> inbound;
    protected HashMap<Pair<Integer, Integer>, Integer> costs;
    protected HashMap<Integer, Integer> minStartTime;
    protected HashMap<Integer, Integer> maxStartTime;


    public graph() {
        outbound = new HashMap<Integer, ArrayList<Integer>>();
        inbound = new HashMap<Integer, ArrayList<Integer>>();
        vertices = new ArrayList<Integer>();
        costs = new HashMap<Pair<Integer, Integer>, Integer>();
        minStartTime = new HashMap<Integer, Integer>();
        maxStartTime = new HashMap<Integer, Integer>();
        readFromFile();

        computeDurations();

    }

    private void computeDurations()
    {
        ArrayList<Integer> toposorted=topoSort();
        for(int i:vertices)
        {
            minStartTime. put(i,0);  // all minStartTimes are initialized with 0
        }
        for(int i:toposorted)
        {
            computeEarliest2(i);// this function uses the minStartTime for all inbound tasks
                                // calling the function on the toposorted order of the vertices
                                // will guarantee to the minStartTime for all inbound tasks defined
        }
        int max=0;
        int x=0;
        int costm=0;
        for (int i :vertices)
        {
            if (minStartTime.get(i)>max)
            {
                max=minStartTime.get(i);     // probably the first element in the reversed toposort
                x=i;
                for(int j:inbound(i))
                {
                    Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, i);
                    costm=costs.get(costEdge);
                    break;
                }
            }
        }
        maxStartTime.put(x,minStartTime.get(x)); // for the ending task we have the earliest and latest EQUAL
        int costt=0;
        Collections.reverse(toposorted);
        for (int i: toposorted)
        {
            if (outbound(i).isEmpty()&&i!=x) { // we handle first all the tasks that have no oubound dependencies, except the "x" one
                for(int j:inbound(i))
                {
                    Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, i);
                    costt=costs.get(costEdge);
                    break;
                }
                int inter=max+costm-minStartTime.get(i)-costt; // inter represents the amount of time task "i" can be delayed
                                                                // without delaying the entire project
                                                                //(value computed based on the ending task)
                maxStartTime.put(i, inter+minStartTime.get(i));
            }
        }
        for(int i:toposorted)
        {
            if (!outbound(i).isEmpty())
                computeLatestDuration(i);// this function uses the maxStartTime of the outbound tasks of "i"
                                        //with the reverse toposorted order we are guaranteed to take tasks in corect order
        }
    }
    public void addVertexAdmin(int c) {
        if (!vertices.contains(c))
            vertices.add(c);
    }

    public void addEdge() {
        Scanner in = new Scanner(System.in);
        System.out.println("task number");
        int d = in.nextInt();
        System.out.println(d);   // check
        System.out.println("Enter the duration");
        int cost = in.nextInt();
        while (cost < 0) {
            System.out.println("No negative costs");
            cost = in.nextInt();
        }
        System.out.println(cost);    //  check

        //##################################################
        int quitter=0;
        System.out.println("Does the task depend on other task?");
        System.out.println("1.Yes");
        System.out.println("0.No");
        quitter = in.nextInt();
        if (quitter==0)
            addEdgeAdmin(d,d,cost);
        else
        {
            while (quitter != 0) {
                System.out.println("Enter the task number you depend on");
                int c = in.nextInt();
                System.out.println(c);   // for check
                addEdgeAdmin(c, d, cost);
                System.out.println("more dependencies ?");
                System.out.println("1.Yes");
                System.out.println("0.No");
                quitter = in.nextInt();
            }
        }
        computeDurations();




    }

    public void addEdgeAdmin(int c, int d, int cost) {
        if(c==d)
        {
            if (!vertices.contains(c)) vertices.add(c);
            if (inbound.get(c) == null) inbound.put(c, new ArrayList<Integer>());
            inbound.get(c).add(c);
            Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(c, c);
            costs.put(costEdge, cost);
        }

        else
        {
            if (!vertices.contains(c)) vertices.add(c);
            if (!vertices.contains(d)) vertices.add(d);
            if (outbound.get(c) == null) outbound.put(c, new ArrayList<Integer>());
            outbound.get(c).add(d);
            if (inbound.get(d) == null) inbound.put(d, new ArrayList<Integer>());
            inbound.get(d).add(c);
            Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(c, d);
            costs.put(costEdge, cost);
        }

    }

    public ArrayList<Integer> outbound(int c) {
        if (!outbound.containsKey(c)) {
            ArrayList<Integer> neww = new ArrayList<Integer>();
            return neww;
        }
        return outbound.get(c);
    }

    public ArrayList<Integer> inbound(int c) {
        if (!inbound.containsKey(c)) {
            ArrayList<Integer> neww = new ArrayList<Integer>();
            return neww;
        }
        return inbound.get(c);
    }

    public Integer EdgeCost() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the task number");
        int c = in.nextInt();
        for(int i:inbound.get(c))
        {
            Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(i, c);
            return costs.get(costEdge);
        }
        return 0;
    }


    public void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/Robert - Tavi/IdeaProjects/lab4-pb2/src/com/company/graph2.txt")))) {
            String strLine = br.readLine();
            String[] array = strLine.split(" ");
            int nrVr = Integer.parseInt(array[0]);
            int vertex;
            int i;
            for (i = 1; i <= nrVr; i++) {
                vertex = Integer.parseInt(array[i]);
                addVertexAdmin(vertex);
            }
            while ((strLine = br.readLine()) != null) {
                array = strLine.split(" ");
                int v1 = Integer.parseInt(array[0]);
                int v2 = Integer.parseInt(array[1]);
                int cost = Integer.parseInt(array[2]);
                addEdgeAdmin(v1, v2, cost);
            }
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }
    }
    public void print()
    {
        System.out.print("Tasks:");
        for (int i :vertices) System.out.print(i+",");
        System.out.println();
        System.out.println("outbound dependecies");
        for (int i :outbound.keySet())
        {
            System.out.print(i+" :  ");
            System.out.println(outbound.get(i));
        }
        System.out.println();
        System.out.println("inbound dependecies");
        for (int i :inbound.keySet())
        {
            System.out.print(i+" :  ");
            System.out.println(inbound.get(i));
        }
        System.out.println();
    }

    public ArrayList<Integer> topoSort()
    {
//        ArrayList<Integer> st=new ArrayList<Integer>();
//        ArrayList<Integer> res=new ArrayList<Integer>();
//        st.add(0);
//        int v;
//        int[] discovered;
//        discovered=new int[vertices.size()];         //it's DFS; I wanted to compare it
//        for(int i=0;i<vertices.size();i++)
//            discovered[i]=0;
//        while(!st.isEmpty())
//        {
//            v=st.get(st.size()-1);
//            st.remove(st.size()-1);
//            if (discovered[v]==0)
//            {
//                discovered[v] = 1;
//                res.add(v);
//                for(int i:outbound(v))
//                    st.add(i);
//            }
//        }
//        return res;

        int[] pred;
        ArrayList<Integer> q,sorted;
        pred=new int[vertices.size()];
        int predIndex=0;
        for(int i=0;i<vertices.size();i++)
            pred[i]=0;
        q =new ArrayList<Integer>(); // the queue will contain only the tasks that have no inbound dependencies(excluding the one generated by itself)
        for (int x:vertices)
        {
            pred[x]=0+inbound(x).size();
            if (inbound(x).get(0)==x)
                pred[x]=0; //this edge exists as a choice of implementation, for representing duration (not the best choice)
            if(pred[x]==0)
            {
               q.add(x);
            }
        }
        sorted =new ArrayList<Integer>(); // the final topological sort
        int x;
        while(!q.isEmpty())
        {
            x=q.get(0);
            q.remove(0);
            sorted.add(x);
            for(int y:outbound(x)) // each node removed from the queue("visited") will not be considered by the remaining nodes as inbound anymore
                                    // for the purpose of adding more nodes to the queue(the inbound and outbound hashmap will not be changed
                                    // only the pred[] array)
            {
                pred[y]--;
                if(pred[y]==0)
                    q.add(y);
            }
        }

        if (sorted.size()==vertices.size()) // if it's not equal we have cycles
            return sorted;
        else
        {
            ArrayList<Integer> neww = new ArrayList<Integer>();
            return neww;
        }
    }
    public HashMap<Integer, Integer> getMinStartTime()
    {
        return minStartTime;

    }
    public HashMap<Integer, Integer> getMaxStartTime()
    {
        return maxStartTime;
    }

    public  ArrayList<Integer>  getCriticals()
    {
        ArrayList<Integer> criticals=new ArrayList<Integer>();
        for(int i:vertices)
        {
            if (minStartTime.get(i).equals(maxStartTime.get(i)))
                criticals.add(i);
        }
        return criticals;
    }
    public int computeEarliest2(int c)
    {

        ArrayList<Integer> durations=new ArrayList<Integer>();
        if (inbound(c).get(0)==c)
            return 0;    // this is the case of a starting activity ; the only inbound edge is defined by itself
                        // and as part of my implementation  every vertex had an inbound
        int costt=0;
        // the earliest time at which a task can start is defined as the maximum of minimum starting times of all inbound tasks + their cost/duration
        // Zoltan Kasa - Critical Path method
        // link in the readme.md file

        for(int task: inbound(c))
        {
            for(int j:inbound(task))
            {
                Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, task);
                costt=costs.get(costEdge);   // we take the cost of the inbound task
                break;
            }
            durations.add(costt+minStartTime.get(task)); // we add that cost the the minium starting time of the inbound task
                                                            // and we obtain one possible starting time
        }
        int max=0;
        for(int i:durations)
        {
            if (i>max)
                max=i;
        }
        durations.clear(); // the actual starting time for task c is the MAX from durations
        minStartTime.replace(c,max);
        return max;

    }


    public void computeLatestDuration(int c)
    {
        int min=100000;  // will hold the minimum latest time from all outbound tasks
        for (int i:outbound(c))
        {
            if(maxStartTime.get(i)<min)
                min=maxStartTime.get(i);
        }
        // the latest time at which a task can start is defined as the min of maxStartTime of all outbound tasks - "c" 's cost/duration
        // Zoltan Kasa - Critical Path method
        // link in the readme.md file
        int cost=0;
        for(int j:inbound(c)) {
            Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, c);
            cost = costs.get(costEdge);
            break;
        }
        min=min-cost;    // previously explained
        maxStartTime.put(c,min);



    }



}