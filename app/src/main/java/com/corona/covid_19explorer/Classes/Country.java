package com.corona.covid_19explorer.Classes;

public class Country {
    private String name,total, death, cured, active;

    public Country() {}

    public Country(String name, String total, String death, String cured, String active) {
        this.name = name;
        this.total = total;
        this.death = death;
        this.cured = cured;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getCured() {
        return cured;
    }

    public void setCured(String cured) {
        this.cured = cured;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}