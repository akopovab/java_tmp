package ru.stqa.tmp.sandbox;

/**
 * Created with IntelliJ IDEA.
 * User: 3
 * Date: 24.02.16
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class Point {

    public double x,y;
    public double x1,y1;

    public Point(double x, double y, double x1, double y1) {

        this.x=x;
        this.y=y;
        this.x1=x1;
        this.y1=y1;

    }

    public double distance(){

        double s;
        s= Math.sqrt((this.x1 - this.x)*2 + (this.y1-this.y)*2);
        return s;
    }

}
