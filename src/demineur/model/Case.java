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

    public Case(Case[] voisines) {
        etat = 0;
        this.voisines = voisines;
        
    }
    
    public Case(int contenu) {
        this.contenu = contenu;
        etat = 0;
    }
    
    
    
    public boolean bombe(){
        if(contenu == 9){
            return true;
        }
        else{
            return false;
        }
    }
}
