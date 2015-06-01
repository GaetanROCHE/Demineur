/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.model;

/**
 *
 * @author Gaëtan
 */
public class Case {
    private Case[] voisines;
    private int etat;
    private int contenu;
    private int x;
    private int y;

    public Case(Case[] voisines) {
        etat = 0;
        this.voisines = voisines;
        
    }
    
    public Case(int contenu) {
        this.contenu = contenu;
        etat = 0;
    }


    public Case(int j, int k, int contenue){
        contenu=0;
        etat = 0;
        this.contenu=contenue;
        x=j;
        y=k;
    }
    
    public int reveleCase(){
        etat = 1;
        return contenu;
    }
    
    public void marqueCase(){
        etat = 2;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    
    public boolean bombe(){
        if(contenu == 9){
            return true;
        }
        else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        return "|"+this.contenu +"|";
    }
}
