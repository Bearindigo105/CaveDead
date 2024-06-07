package main.java;

import javafx.scene.Group;

public class Inventory extends Group{

    private Item[] items;

    public Inventory(int size){
        items = new Item[size];
    }
}
