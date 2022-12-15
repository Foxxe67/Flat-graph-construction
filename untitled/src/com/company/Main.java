package com.company;

import com.company.IncedentList;
import com.sun.org.apache.xpath.internal.SourceTree;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    static List<IncedentList> list;
    static int numOfForbidden5 = 0;
    static int numOfForbidden33 = 0;

    public static void main(String[] args) {
        clearList();
        try {
            menu();
        }
        catch(Exception ex){
            System.out.println("Ошибка");
        }
    }

    private static void menu() throws Exception{
        boolean cheaker = true;
        while(cheaker){
            System.out.println("Choose one of the paragraphs:");
            System.out.println("1) Test 1");
            System.out.println("2) Test 2");
            System.out.println("3) Test 3");
            System.out.println("4) Test 4");
            System.out.println("5) Test 5");
            System.out.println("6) Test 6");
            System.out.println("7) Test 7");
            System.out.println("8) Exit\n");
            Scanner vvod = new Scanner(System.in);
            int choose = vvod.nextInt();
            switch(choose){
                case 1:
                    helpForMenu(5, "test1.txt");
                    break;
                case 2:
                    helpForMenu(6, "test2.txt");
                    break;
                case 3:
                    helpForMenu(6, "test3.txt");
                    break;
                case 4:
                    helpForMenu(11, "test4.txt");
                    break;
                case 5:
                    helpForMenu(9, "test5.txt");
                    break;
                case 6:
                    helpForMenu(11, "test6.txt");
                    break;
                case 7:
                    helpForMenu(11, "test7.txt");
                    break;
                case 8:
                    System.out.println(" End of the program");
                    cheaker = false;
                    break;
                default:
                    System.out.println("Ошибка");
                    break;
            }
        }
    }
    private static void helpForMenu(int n, String nameTest){
        clearList();
        try {
            fill(nameTest);
        }
        catch(Exception ex){
            System.out.println();
        }
        show();

        if(isPlanaren(n)){
            System.out.println("Граф изначально планарен!");
        }

        boolean oneShowing = true;
        while(!isPlanaren(n)){
            cheakPlanarnost5(n);
            cheakPlanarnost33(n);
            if(oneShowing){
                System.out.print("Фигур типа 5 : " + numOfForbidden5 + "\n");
                System.out.print("Фигур типа 3*3 : " + numOfForbidden33 + "\n");
                System.out.print("Минимальное кол-во рёбёр, при удалении которых граф станет планарным : ");
                oneShowing = false;
            }
            String remove = RebroWithPartALotOfFigures();
            for(int i = 0; i < list.size(); i++){
                for(int j = 0; j < list.get(i).kolReber(); j++){
                    list.get(i).removeRebro(remove);
                }
            }
            for(int i = 0; i < list.size(); i++){
                list.get(i).resetAll();
            }
            System.out.print(remove + "  ");
        }
        System.out.println("\n");
    }

    private static void clearList(){
        list = new ArrayList<>();
        list.add(new IncedentList("0-oi element exist"));
        numOfForbidden5 = 0;
        numOfForbidden33 = 0;
        System.out.println();
    }

    private static void fill(String failname) throws Exception{
        Scanner reader = new Scanner(new File(failname));
        IncedentList incedentList = new IncedentList();

        while(reader.hasNext()){
            String value = reader.next();
            if(value.equals("-1")){
                list.add(incedentList);
                incedentList = new IncedentList();
                value = reader.next();
            }
            incedentList.setRebro(value);
        }
        reader.close();
    }
    private static void show(){
        System.out.println("Values of incident list");
        for(int i = 1; i < list.size(); i++){
            System.out.print(i + ") ");
            IncedentList help = list.get(i);
            for(int j = 0; j < help.kolReber(); j++){
                System.out.print(help.getRebro(j) + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }

    private static void cheakPlanarnost5(int n){
        final int m = 5;
        Combinations.getDiapazon(m, n);
        int[] mass = null;

        while((mass = Combinations.generateCombinations(mass)) != null){
            int count = 0;
            for(int i = 0; i < mass.length; i++){
                for(int j = 0; (j < mass.length) && (j != i); j++){
                    for(int ii = 0; ii < list.get(mass[i]).kolReber(); ii++){
                        for(int jj = 0; jj < list.get(mass[j]).kolReber(); jj++){
                            if(IncedentList.compareRebra(list.get(mass[i]).getRebro(ii), list.get(mass[j]).getRebro(jj))){
                                count++;
                            }
                        }
                    }
                }
            }
            if(count == 10){
                numOfForbidden5++;

                for(int i = 0; i < mass.length; i++){
                    for(int j = 0; (j < mass.length) && (j != i); j++){
                        for(int ii = 0; ii < list.get(mass[i]).kolReber(); ii++){
                            for(int jj = 0; jj < list.get(mass[j]).kolReber(); jj++){
                                if(IncedentList.compareRebra(list.get(mass[i]).getRebro(ii), list.get(mass[j]).getRebro(jj))){
                                    list.get(mass[i]).incrementNumAsApartOfFigure(list.get(mass[i]).getRebro(ii));
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private static void cheakPlanarnost33(int n){
        int counter = 0;
        final int m = 3;
        Combinations.getDiapazon(m, n);
        int count = 0;
        boolean condition = true;

        int[] mass1 = null;
        while((mass1 = Combinations.generateCombinations(mass1)) != null){
            int[] mass2 = null;
            while((mass2 = Combinations.generateCombinations(mass2)) != null){
                if(!cheakComplience(mass1, mass2)){
                    continue;
                }
                count = 0;
                for(int i = 0; i < mass1.length; i++){
                    for(int j = 0; j < mass2.length; j++){
                        if(pereborReber(list.get(mass1[i]), list.get(mass2[j]))){
                            count++;
                        }
                    }
                }
                if(count == 9){
                    counter++;
                    condition = !condition;
                    if(condition){
                        for(int i = 0; i < mass1.length; i++){
                            for(int j = 0; j < mass2.length; j++){
                                for(int ii = 0; ii < list.get(mass1[i]).kolReber(); ii++){
                                    for(int jj = 0; jj < list.get(mass2[j]).kolReber(); jj++){
                                        if(IncedentList.compareRebra(list.get(mass1[i]).getRebro(ii), list.get(mass2[j]).getRebro(jj))){
                                            list.get(mass1[i]).incrementNumAsApartOfFigure(list.get(mass1[i]).getRebro(ii));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        numOfForbidden33 = counter/2;
    }

    private static boolean isPlanaren(int n){
        final int m = 3;
        Combinations.getDiapazon(m, n);
        int count = 0;
        boolean condition = true;

        int[] mass1 = null;
        while((mass1 = Combinations.generateCombinations(mass1)) != null){
            int[] mass2 = null;
            while((mass2 = Combinations.generateCombinations(mass2)) != null){
                if(!cheakComplience(mass1, mass2)){
                    continue;
                }
                count = 0;
                for(int i = 0; i < mass1.length; i++){
                    for(int j = 0; j < mass2.length; j++){
                        if(pereborReber(list.get(mass1[i]), list.get(mass2[j]))){
                            count++;
                        }
                    }
                }
                if(count == 9) {
                    return false;
                }
            }
        }

        final int m1 = 5;
        Combinations.getDiapazon(m1, n);
        int[] mass = null;

        while((mass = Combinations.generateCombinations(mass)) != null){
            int count1 = 0;
            for(int i = 0; i < mass.length; i++){
                for(int j = 0; (j < mass.length) && (j != i); j++){
                    for(int ii = 0; ii < list.get(mass[i]).kolReber(); ii++){
                        for(int jj = 0; jj < list.get(mass[j]).kolReber(); jj++){
                            if(IncedentList.compareRebra(list.get(mass[i]).getRebro(ii), list.get(mass[j]).getRebro(jj))){
                                count1++;
                                //System.out.println("true");
                            }
                        }
                    }
                }
            }
            if(count1 == 10){
                return false;
            }
        }
        return true;
    }

    private static boolean cheakComplience(int mass1[], int[] mass2){
        for(int i = 0; i < mass1.length; i++){
            for(int j = 0; j < mass2.length; j++){
                if(mass1[i] == mass2[j]){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean pereborReber(IncedentList l1, IncedentList l2){
        for(int j = 0; j < l1.kolReber(); j++){
            for(int jj = 0; jj < l2.kolReber(); jj++){
                if(IncedentList.compareRebra(l1.getRebro(j), l2.getRebro(jj))){
                    return true;
                }
            }
        }
        return false;
    }

    private static String RebroWithPartALotOfFigures(){
        int nomber = 0;
        String rebro = "";
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.get(i).kolReber(); j++){
                if(list.get(i).getNumAsAPartOfFigure(list.get(i).getRebro(j)) > nomber){
                    nomber = list.get(i).getNumAsAPartOfFigure(list.get(i).getRebro(j));
                    rebro = list.get(i).getRebro(j);
                }
//                System.out.println(list.get(i).getNumAsAPartOfFigure(list.get(i).getRebro(j)));
            }
        }
        return rebro;
    }
}