package com.citi.piggybank.bo;

/**
 * Items
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class Items {
    public int count;
    public int index;
    public int amount;
    public int goal;
    public String name;

    public Items(int count, int index, int goal, String name, int amount) {
        this.count = count;
        this.index = index;
        this.goal = goal;
        this.name = name;
        this.amount = amount;
    }
}
