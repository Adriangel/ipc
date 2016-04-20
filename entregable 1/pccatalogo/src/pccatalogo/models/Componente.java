/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccatalogo.models;

import es.upv.inf.Product;

/**
 *
 * @author angel
 */
public class Componente{
    private int q;
    private final Product p;
    
    public Componente(Product pr, int qu){
        this.p = pr;
        this.q = (qu > 0)? qu : 1;
    }
    
    public void setQuantity(int newQ){
        this.q = (newQ > 0)? newQ : this.q;
    }
    
    public int getQuantity(){
        return this.q;
    }
    
    public Product getProduct(){
        return this.p;
    }
    
    public double getPrice() {
        return q*p.getPrice();
    }
    
    public boolean equals(Componente c){
        return (c == null)? false : this.p.getDescription().equals(c.getProduct().getDescription());
    }
}
