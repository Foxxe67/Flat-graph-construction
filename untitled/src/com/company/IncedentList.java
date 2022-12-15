package com.company;
import java.util.ArrayList;
import java.util.List;

public class IncedentList {
    private List<String> rebra = new ArrayList();
    private List<Integer> asAPartOfFigure = new ArrayList<>();

    public void incrementNumAsApartOfFigure(String str){
        for(int i = 0; i < rebra.size(); i++){
            if(compareRebra(str, rebra.get(i))){
                asAPartOfFigure.set(i, (asAPartOfFigure.get(i) + 1));
            }
        }
    }
    public int getNumAsAPartOfFigure(String str){
        for(int i = 0; i < rebra.size(); i++){
            if(compareRebra(str, rebra.get(i))){
                return asAPartOfFigure.get(i);
            }
        }
        return 0;
    }
    public void resetAll(){
        for(int i = 0; i < asAPartOfFigure.size(); i++){
            asAPartOfFigure.set(i, 0);
        }
    }

    public void removeRebro(String str){
        for(int i = 0; i < rebra.size(); i++){
            if(compareRebra(str, rebra.get(i))){
                rebra.remove(i);
                asAPartOfFigure.remove(i);
                break;
            }
        }
    }

    public IncedentList(String string){
        rebra.add(string);
        asAPartOfFigure.add(0);
    }
    public IncedentList(){};

    public int kolReber(){
        return rebra.size();
    }
    public String getRebro(int i){
        return rebra.get(i);
    }
    public void setRebro(String str){
        rebra.add(str);
        asAPartOfFigure.add(0);
    }

    public static boolean compareRebra(String s1, String s2){
        String half1s1 = "";
        String half2s1 = "";
        String half1s2 = "";
        String half2s2 = "";

        boolean cheaker1 = true;
        for(int i = 0; i < s1.length(); i++){
            if(cheaker1 && (s1.charAt(i) != '-')){
                half1s1 += s1.charAt(i);
            }
            if(s1.charAt(i) == '-'){
                cheaker1 = false;
                continue;
            }
            if(!cheaker1){
                half2s1 += s1.charAt(i);
            }
        }

        boolean cheaker2 = true;
        for(int i = 0; i < s2.length(); i++){
            if(cheaker2 && (s2.charAt(i) != '-')){
                half1s2 += s2.charAt(i);
            }
            if(s2.charAt(i) == '-'){
                cheaker2 = false;
                continue;
            }
            if(!cheaker2){
                half2s2 += s2.charAt(i);
            }
        }

        //System.out.println(half1s1 + " " + half2s1 + " \n" + half1s2 + " " + half2s2);

        if(half1s1.equals(half1s2) && half2s1.equals(half2s2)){
            return true;
        }

        if(half1s1.equals(half2s2) && half2s1.equals(half1s2)){
            return true;
        }
        return false;
    }
}