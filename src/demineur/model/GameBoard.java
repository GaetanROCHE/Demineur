/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.model;

import java.util.Observable;
import java.util.Random;

/**
 *
 * @author Gaëtan
 */
public class GameBoard extends Observable{
    private Case[][] Grille;
    private final int tailleX;
    private final int tailleY;
    private int minesRestantes;

    public GameBoard(int tailleX, int tailleY, int minesRestantes) {
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        this.minesRestantes = minesRestantes;
        
        Random rand;
        int alea;
        for(int i=0;i<minesRestantes;i++){
            do{
                rand = new Random(tailleX*tailleY);
                alea = rand.nextInt();
            }while(bombe(alea%tailleX,alea/tailleX));
            Grille[alea%tailleX][alea/tailleX] = new Case(alea%tailleX,alea/tailleX,9);
        }
        
        for(int j=0;j<tailleX;j++){
            for(int k=0;k<tailleY;k++){
                if(!this.Grille[j][k].bombe()){
                    Grille[j][k] = new Case(j,k,voisines(j, k));
                }
            }
        }
        
    }
    
    public final boolean bombe(int x, int y){
        return this.Grille[x][y].bombe();
    }
    
    public void poseDrapeau(int x,int y){
        minesRestantes --;
    }
    
    public final Case[] voisines(int x, int y){
        int compteur=0;
        Case[] voisines = new Case[8];
        for(int i=-1;i<2;i++)
        {
            for(int j=-1;j<2;j++)
            {
                if(j+y>=0 && i+x>=0 && i+x<tailleX && j+y<tailleY && !(j==0 && i==0))
                {
                    voisines[compteur]=Grille[x+i][y+j];
                    compteur++;
                }
            }
        }
        return voisines;
    }
    
}
