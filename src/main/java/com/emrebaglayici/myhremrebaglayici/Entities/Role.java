package com.emrebaglayici.myhremrebaglayici.Entities;

public enum Role {
    HR("Hr"),
    CANDIDATES("Candidates");
    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
