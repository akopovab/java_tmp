package ru.stqa.tmp.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {

       Square s=new Square(5);

       System.out.println("Площадь квадрата со стороной " +s.l+ "=" + s.area());

        Rectangle r= new Rectangle(4,6);

       System.out.println("Площадь прямоугольника со сторонами" + r.a+ " и "+ r.b+ "=" + r.area());


          Point p1=new Point(1,1);
          Point p2=new Point(4,4);

        System.out.println("Расстояние = " + distance(p1,p2));


    }

    public static double distance(Point p1, Point p2){

         double s;
         s= Math.sqrt((p2.x - p1.x)*2 + (p2.y-p1.y)*2);
         return s;
         }

    }

