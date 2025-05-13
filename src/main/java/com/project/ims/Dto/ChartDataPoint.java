package com.project.ims.Dto;

public class ChartDataPoint {

    private String label;
    private double value;
    // Constructors, getters, setters
    public ChartDataPoint(String label, double value) { this.label = label; this.value = value; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

}
