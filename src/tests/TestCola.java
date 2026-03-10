/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import lineales.estaticas.Cola;



/**
 *
 * @author Usuario
 */
public class TestCola {
    public static void main(String[] arg)
    {
	Cola c1=new Cola();	
        System.out.println(c1.poner(1));
        System.out.println(c1.poner(3));
        System.out.println(c1.poner(5));
        System.out.println(c1.poner(7));
        //System.out.println(c1.poner(9));
            System.out.println(c1.toString());
        System.out.println("se saco:"+c1.sacar());
            System.out.println(c1.toString());
      /*   System.out.println("se saco:"+c1.sacar());
          System.out.println("se saco:"+c1.sacar());
           System.out.println("se saco:"+c1.sacar());
            System.out.println("se saco:"+c1.sacar());
    */
       System.out.println("pone:11 ->"+c1.poner(11));
       
        System.out.println(c1.toString());
              System.out.println("se saco:"+c1.sacar());
              
        System.out.println("pone:11 ->"+c1.toString());
           System.out.println("pone:9 ->"+c1.poner(9));
        System.out.println(c1.toString());
             System.out.println("pone:7 ->"+c1.poner(7));
        System.out.println(c1.toString());
             System.out.println("pone:5 ->"+c1.poner(5));
        System.out.println(c1.toString());
    }

}
