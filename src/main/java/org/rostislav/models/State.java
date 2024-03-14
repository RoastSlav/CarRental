package org.rostislav.models;

public class State {
    private int id;
    private String stateName;

    public State(String stateName) {
        this.stateName = stateName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
