/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.estaticas;

/**
 *
 * @author Brunot
 */
public class Cola {
    
    private  int frente;
    private int fin;
    private Object[] arre;

    public Object[] getArre() {
        return arre;
    }
    private final int TAMANIO=5;
    public Cola()
    {
       this.frente=0;
       this.fin=0;       
       this.arre=new Object[TAMANIO];
    }
    public boolean esVacia()
    {
      return this.fin==this.frente;
    }
    public boolean estaLlena()
    {
     return this.fin+1%TAMANIO==frente;
    }
    public boolean poner2(Object nuevoElem)
{
        boolean exito;
   
 if((this.fin)==TAMANIO-1)
 {
     
   exito=false;
 }
 else
 {
   //enganchamos el nuevo nodo al final de cola
     this.arre[this.fin+1%this.TAMANIO]=nuevoElem;
   //modificamos el fin para que apunte al ultimo nodo
     this.fin++;
exito= true;
 }
 return exito;
    

 
 
}
    public boolean poner(Object elem)
    {
        boolean exito;    
        
        if(this.esVacia())
        {
        this.arre[frente]=elem;
        this.fin++;
        exito=true;
        }
        else
        { 
        if(this.esVacia())
        {
          exito=false;
        }
        else{
            if(this.fin%TAMANIO!=frente)
            {
              //posicion
              int pos;
              pos=this.fin%(TAMANIO);  
              this.arre[pos]=elem;
              
              System.out.println(pos+" val:"+(this.arre[pos]));
              this.fin++;
              exito=true;
            }
            else
                exito=false;
        }   
       
        }
        return exito;
    }
    public boolean sacar()
    {
        boolean exito;
        if(this.esVacia())
        {
            exito=false;
        }
        else{
            arre[this.frente]=null;
            this.frente=(this.frente+1)%TAMANIO;
            exito=true;
        }
    
      return exito;
    }
    public void mostrarArre(Object[] arre)
    {
        for(int i =0;i<TAMANIO;i++)
        {
        
        }
    
    }
    @Override
    public String toString()
    {
     String cadena="---------\n";
     int i;
     i=0;
     while(i<TAMANIO)
     {
        cadena+="Pos:"+i+"["+arre[i]+"]";
        i=i+1%TAMANIO;
     }
     
     
        
     return cadena;
    }
    
    
    
}
