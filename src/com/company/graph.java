package com.company;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
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
        for(int i:vertices)
        {
           minStartTime. put(i,0);  // OR 8*60 if
            computeDuration(i);
        }
        //computeMAXDuration();
    }


    public void addVertex() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the vertex");
        int c = in.nextInt();
        if (!vertices.contains(c))
            vertices.add(c);
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
        for(int i:vertices)
        {
            minStartTime. put(i,0);
            computeDuration(i);
        }
        //computeMAXDuration();




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
    public void getMinStartTimeUser()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("enter task number");
        int task = in.nextInt();
        int minutes=minStartTime.get(task);
        //int minutes=computeDuration(task); // in order to recalculate the value

        int hours;
        if(minutes>60)
        {
            hours=minutes/60;
            minutes=minutes%60;
            System.out.println( hours+"h:"+minutes+"min");
            return;
        }
        System.out.println(minutes+"min");
    }
    public void getMaxStartTimeUser()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("enter task number");
        int task = in.nextInt();
        int minutes=maxStartTime.get(task);
        int hours;
        if(minutes>60)
        {
            hours=minutes/60;
            minutes=minutes%60;
            System.out.println( hours+"h:"+minutes+"min");
            return;
        }
        System.out.println(minutes+"min");
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
    public int computeDuration(int c)
    {

        ArrayList<Integer> durations=new ArrayList<Integer>();

        innerComputeDuration(c,durations,0);

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
        minStartTime.replace(c,max+minStartTime.get(c));
        return max;

    }
    public void innerComputeDuration(int task, ArrayList<Integer> durations,int curent)
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
            innerComputeDuration(j,durations,curent);
        }

    }
    public void computeMAXDuration()
    {
        ArrayList<Integer> MAXDurations = new ArrayList<Integer>();
        //int max=16*60;     // this is in case we go for 16:00 as the ending hour
        //and this value will dictate how late a task can be started

        //or, for the general case , when we don't have a final time limit ;
        //a task will have minStartTime!=maxStartTime only when the task depends on multiple tasks that have different values minStartTime
        int max=0;
        for(int i:vertices)
        {
            int inter=maxStartTime.get(i);
            if(inter>max)
                max=inter;
        }
        for(int i:vertices)
        {
            maxStartTime.put(i,max);
        }
        for(int i:vertices)
        {
            if(outbound(i).size()==0)
            {
                max=minStartTime.get(i)+EdgeCostAdmin(i);
                innerComputeMAXDuration(i,max);
            }
        }




    }
    public void innerComputeMAXDuration(int task,int max)
    {
        for(int i=0;i<inbound(task).size();i++)
        {
            int j=inbound(task).get(i);
            if(i==0) // meant to be executed only once per task
            {
                Pair<Integer, Integer> costEdge = new Pair<Integer, Integer>(j, task);
                int durr= costs.get(costEdge);
                int afterTaskTime = durr + minStartTime.get(task);
                int inter = max - afterTaskTime;
                if((minStartTime.get(task) + inter)<maxStartTime.get(task))
                    maxStartTime.put(task, minStartTime.get(task) + inter);
                max = max - durr;
            }
            if(j!=task)
                innerComputeMAXDuration(j, max);

        }

    }



}