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
    int x;
    int y;
    private int etat;
    private int contenu;

    public Case(int j, int k, Case[] voisines){
        contenu=0;
        etat = 0;
        x=j;
        y=k;
        this.voisines=voisines;
        for(int i = 0; i<8;i++){
            if(voisines[i].bombe()){
                contenu++;
            }
        }
    }
    
    public Case(int x, int y, int bombe){
        etat = 0;
        contenu = bombe;
    }

    
    public void setEtat(int etat){
        this.etat=etat;
    }
    
    public boolean bombe(){
        return contenu == 9;
    }
}
