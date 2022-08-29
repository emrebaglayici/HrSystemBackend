package com.emrebaglayici.myhremrebaglayici.Entities;

public enum Steps {
    HR(1,"HR"),
    CODETASK(2,"CODETASK"),
    PAIRPROGRAMMING(3,"PAIRPROGRAMMING"),
    MANAGER(4,"MANAGER"),
    OFFER(5,"OFFER");

    private final int id;
    private final String name;

    Steps(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
