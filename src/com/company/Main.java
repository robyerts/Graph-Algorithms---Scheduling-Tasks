package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        graph g = new graph();


        while (true) {
            System.out.println("1. Tasks and their outbound and inbound dependencies");
            System.out.println("2. Outbound = tasks that depend on X");
            System.out.println("3. Inbound = the tasks X depends on");
            System.out.println("4. Duration of task");
            System.out.println("5. Toposort (returns an empty list if there are cycles)");
            System.out.println("6. The minimum time at which each task can start");
            System.out.println("7. The maximum time at which each task can start");
            System.out.println("8. The list of critical tasks");
            System.out.println("9. Add task");
            Scanner in = new Scanner(System.in);
            int comm = in.nextInt();
            int vertex;
            if (comm==0)
                break;
            switch (comm) {
                case 9:
                    g.addEdge();
                    break;
                case 2:
                    System.out.println("enter vertex");
                    vertex = in.nextInt();
                    System.out.println( g.outbound(vertex));
                    break;
                case 3: {
                    System.out.println("enter vertex");
                    vertex = in.nextInt();
                    System.out.println( g.inbound(vertex));
                    break;
                }
                case 4: {
                    int minutes=g.EdgeCost();
                    int hours;
                    if(minutes>60)
                    {
                        hours=minutes/60;
                        minutes=minutes%60;
                        System.out.println( hours+"h:"+minutes+"min");
                        break;
                    }
                    System.out.println(minutes+"min");
                    break;
                }
                case 5: {
                    System.out.println(g.topoSort());
                    break;
                }
                case 1: {
                     g.print();
                     break;
                 }
                case 6: {
                    System.out.println(g.getMinStartTime());
                    break;
                }
                case 7: {
                    System.out.println(g.getMaxStartTime());
                    break;
                }
                case 8: {
                    System.out.println(g.getCriticals());
                    break;
                }


            }


        }
    }
}
