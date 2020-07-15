package com.example.cats.home;

public class CatItem {
    public String desc;
    public int id;
    public int id2;

    public CatItem(){

    }

    public CatItem(String desc, int id, int id2) {
        this.desc = desc;
        this.id = id;
        this.id2 = id2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
