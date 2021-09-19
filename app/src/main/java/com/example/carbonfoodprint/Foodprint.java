package com.example.carbonfoodprint;

public class Foodprint {
    private String commodity;
    private double footprint;

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public double getFootprint() {
        return footprint;
    }

    public void setFootprint(double footprint) {
        this.footprint = footprint;
    }

    @Override
    public String toString() {
        return "Foodprint{" +
                "commodity='" + commodity + '\'' +
                ", footprint=" + footprint +
                '}';
    }
}