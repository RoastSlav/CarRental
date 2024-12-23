package org.rostislav.models;

public class CarMake {
    private int id;
    private String makeName;

    public CarMake(String makeName) {
        this.makeName = makeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    @Override
    public String toString() {
        return makeName;
    }
}
