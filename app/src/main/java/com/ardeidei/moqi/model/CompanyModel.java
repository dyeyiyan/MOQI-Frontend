package com.ardeidei.moqi.model;

public class CompanyModel {
    private String name;
    private String address;
    private String id;

    public CompanyModel(String id, String cname, String caddress) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
