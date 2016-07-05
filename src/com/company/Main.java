package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        graph g = new graph();





        while (true) {
            System.out.println("2.Add task");
            System.out.println("3.outbound=task that depend on X");
            System.out.println("4.inbound=the tasks X depends on");
            System.out.println("5.duration of the task");
            System.out.println("6.toposort(returns an empty list if there are cycles)");
            System.out.println("7.print the tasks and their outbound and inbound dependencies");
            System.out.println("8.the minimum time at which the task can start");
            System.out.println("9.the maximum time at which the task can start");
            System.out.println("10.get the lists of critical tasks");
            Scanner in = new Scanner(System.in);
            int comm = in.nextInt();
            int vertex;
            if (comm==0)
                break;
            switch (comm) {
                case 1:
                    g.addVertex();
                    break;
                case 2:
                    g.addEdge();
                    break;
                case 3:
                    System.out.println("enter vertex");
                    vertex = in.nextInt();
                    System.out.println( g.outbound(vertex));
                    break;
                case 4: {
                    System.out.println("enter vertex");
                    vertex = in.nextInt();
                    System.out.println( g.inbound(vertex));
                    break;
                }
                case 5: {
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
                case 6: {
                    System.out.println(g.topoSort());
                    break;
                }
                case 7: {
                     g.print();
                     break;
                 }
                case 8: {
                    g.getMinStartTimeUser();
                    break;
                }
                case 9: {
                    g.getMaxStartTimeUser();
                    break;
                }
                case 10: {
                    System.out.println(g.getCriticals());
                    break;
                }


            }


        }
    }
}
