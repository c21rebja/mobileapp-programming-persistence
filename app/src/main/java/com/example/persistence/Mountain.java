package com.example.persistence;

public class Mountain {

    private long id;
    private String name;
    private int height;

    public Mountain(long id, String name, int height) {
        this.id = id;
        this.name = name;
        this.height = height;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }
}
