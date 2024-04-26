package com.watch_collector.hajun.domain;

public class Watch {
    int id;
    String userId;
    // 이미지
    String model;
    int caseSize;
    String movement;
    int lugToLug;
    String glass;
    // function

    
    public Watch(int id, String userId, String model, int caseSize, String movement, int lugToLug, String glass) {
        this.id = id;
        this.userId = userId;
        this.model = model;
        this.caseSize = caseSize;
        this.movement = movement;
        this.lugToLug = lugToLug;
        this.glass = glass;
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
