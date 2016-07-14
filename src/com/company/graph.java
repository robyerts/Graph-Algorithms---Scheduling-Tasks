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
        for(int i:vertices)  // current implementation for computeDuration function is not the great...
        // it takes no advantage of the topological sort, therefore it  ends up doing lots of computations...
        {
            minStartTime. put(i,0);
            computeEarliest(i);
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
        maxStartTime.put(x,minStartTime.get(x));
        int costt=0;
        Collections.reverse(toposorted);
        for (int i: toposorted)
        {
            if (outbound(i).isEmpty()&&i!=x) {
                for(int j:inbound(i))
                {
                    Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, i);
                    costt=costs.get(costEdge);
                    break;
                }
                int inter=max+costm-minStartTime.get(i)-costt;
                maxStartTime.put(i, inter+minStartTime.get(i));
            }
        }
        for(int i:toposorted)
        {
            if (!outbound(i).isEmpty())
                computeLatestDuration(i);
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
    public Integer EdgeCostAdmin(int c ) {
        for(int i:inbound.get(c))
        {
            Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(i, c);
            return costs.get(costEdge);
        }
        return 0;
    }

    public void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/Robert - Tavi/IdeaProjects/lab4-pb2/src/com/company/graph.txt")))) {
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
        q =new ArrayList<Integer>();
        for (int x:vertices)
        {
            pred[x]=0+inbound(x).size();
            if (inbound(x).get(0)==x)
                pred[x]=0;
            if(pred[x]==0)
            {
               q.add(x);
            }
        }
        sorted =new ArrayList<Integer>();
        int x;
        while(!q.isEmpty())
        {
            x=q.get(0);
            q.remove(0);
            sorted.add(x);
            for(int y:outbound(x))
            {
                pred[y]--;
                if(pred[y]==0)
                    q.add(y);
            }
        }

        if (sorted.size()==vertices.size())
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
    public int computeEarliest(int c)
    {

        ArrayList<Integer> durations=new ArrayList<Integer>();

        innerComputeEarliest(c,durations,0);

        int max=0;
        for(int i:durations)
        {
            if (i>max)
                max=i;
        }
        for(int i:inbound(c))
        {
            Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(i, c);
            max=max-costs.get(costEdge);
            break;
        }
        durations.clear();
        minStartTime.replace(c,max);
        return max;

    }
    public void innerComputeEarliest(int task, ArrayList<Integer> durations,int curent)
    {
        for(int i=0;i<inbound(task).size();i++) // I used a classic for loop because we needed the value of  i
        {
            int j=inbound(task).get(i);
            if (j==task)
            {
                Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, task);
                int inter=costs.get(costEdge);
                curent= curent+costs.get(costEdge);
                durations.add(curent);
                return;
            }
            if(i==0) {
                Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, task);
                int inter = costs.get(costEdge);
                curent = curent + costs.get(costEdge);
            }
            innerComputeEarliest(j,durations,curent);
        }

    }
    public void computeLatestDuration(int c)
    {
        int min=100000;  // will hold the minimum latest time from all outbound tasks
        for (int i:outbound(c))
        {
            if(maxStartTime.get(i)<min)
                min=maxStartTime.get(i);
        }
        int cost=0;
        for(int j:inbound(c)) {
            Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, c);
            cost = costs.get(costEdge);
            break;
        }
        min=min-cost;
        maxStartTime.put(c,min);



    }



}