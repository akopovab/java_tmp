package ru.stqa.tmp.sandbox;

/**
 * Created with IntelliJ IDEA.
 * User: 3
 * Date: 24.02.16
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle {

    public double a;
    public double b;

    public Rectangle( double a, double b){
        this.a=a;
        this.b=b;
    }

    public  double area (){
        return this.a*this.b;
    }
}
