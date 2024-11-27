package org.rostislav.models;

public class CarModel {
    private int id;
    private String modelName;
    private int makeId;

    public CarModel() {
    }

    public CarModel(String modelName, int makeId) {
        this.modelName = modelName;
        this.makeId = makeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getMakeId() {
        return makeId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
