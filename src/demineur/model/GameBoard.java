/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.model;


import java.util.Random;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Gaëtan
 */


public class GameBoard extends Observable{
    private final Case[][] Grille;

    private final int tailleX;
    private final int tailleY;
    private int minesRestantes;
    private boolean jeu;

    public GameBoard(int tailleX, int tailleY, int minesRestantes) {
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        this.minesRestantes = minesRestantes;
        this.Grille = new Case[tailleX][tailleY];
        jeu = true;
        Random rand;
        int alea;
        for(int i=0;i<minesRestantes;i++){
            do{
                rand = new Random();

                alea = rand.nextInt(tailleX*tailleY);
            }while(this.Grille[alea%tailleX][alea/tailleX] != null);
            Grille[alea%tailleX][alea/tailleX] = new Case(alea%tailleX,alea/tailleX,9);
        }
        
        
        for(int j=0;j<tailleX;j++){
            for(int k=0;k<tailleY;k++){
                if(this.Grille[j][k] == null) {
                    Grille[j][k] = new Case(j,k,compteMine(j,k));

                }
            }
        }
    }
    
    public int getMines(){
        return this.minesRestantes;
    }
    
    public int getTailleX(){
        return this.tailleX;
    }
    
    public int getTailleY(){
        return this.tailleY;
    }
    
    public Case getCase(int[] id){
        return Grille[id[0]][id[1]];
    }
    public Case getCase(int x, int y){
        return Grille[x][y];
    }
    
    public final boolean bombe(int x, int y){
        return this.Grille[x][y].bombe();
    }
    
    public void poseDrapeau(int x,int y){
        if(Grille[x][y].setDrapeau()){
            this.minesRestantes --;
        }else{
            this.minesRestantes ++;
        }
    }
    
    public final int compteMine(int x,int y){
        int compteur = 0;
        Case Cases[] = voisines(x,y);
        for(Case C : Cases){
            if(C != null && C.bombe()){
                compteur ++;
            }
        }
        return compteur;
    }
    
    public void reveleCase(int x, int y){
        if(Grille[x][y].reveleCase() == 0){//s'il n'y a pas de bombe
            for(Case C : voisines(x, y)){
                reveleCase(C.getX(), C.getY());
            }
        }
        else{
            if(Grille[x][y].reveleCase() == 9){
                jeu = false;
                reveleGrille();
            }
        }
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

    @Override
    public String toString() {
        String res = "";
        for(int i = 0; i<this.tailleX;i++){
            for(int j = 0; j<this.tailleY;j++){
                res = res + this.Grille[i][j].toString();
            }
            res = res + "\n";
        }
        return res;
    }

    private void reveleGrille() {
        for(int i = 0; i<tailleX; i++){
            for(int j = 0; j<tailleY; j++){
                Grille[i][j].reveleCase();
            }
        }
    }
    
    
}
