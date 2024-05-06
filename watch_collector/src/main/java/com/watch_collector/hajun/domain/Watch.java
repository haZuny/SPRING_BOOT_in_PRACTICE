package com.watch_collector.hajun.domain;

import java.util.List;

public class Watch {
    int id = Integer.MIN_VALUE;
    String userId;
    // 이미지
    String model;
    int caseSize;
    String movement;
    int lugToLug;
    String glass;
    List<String> function;

    public Watch(String userId, String model, int caseSize, String movement, int lugToLug, String glass, List<String> function) {
        this.userId = userId;
        this.model = model;
        this.caseSize = caseSize;
        this.movement = movement;
        this.lugToLug = lugToLug;
        this.glass = glass;
        this.function = function;
    }

    public Watch(String model, int caseSize, String movement, int lugToLug, String glass, List<String> function) {
        this.model = model;
        this.caseSize = caseSize;
        this.movement = movement;
        this.lugToLug = lugToLug;
        this.glass = glass;
        this.function = function;
    }

    public List<String> getFunction() {
        return function;
    }

    public void setFunction(List<String> function) {
        this.function = function;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCaseSize() {
        return caseSize;
    }

    public void setCaseSize(int caseSize) {
        this.caseSize = caseSize;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public int getLugToLug() {
        return lugToLug;
    }

    public void setLugToLug(int lugToLug) {
        this.lugToLug = lugToLug;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }


}
