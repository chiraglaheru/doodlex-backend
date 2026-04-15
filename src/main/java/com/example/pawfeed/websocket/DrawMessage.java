package com.example.pawfeed.websocket;

public class DrawMessage {
    private double x;
    private double y;
    private String type;

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}