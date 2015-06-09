/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.model;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Gaëtan
 */


public class GameBoard{
    private final Case[][] Grille;

    private final int tailleX;
    private final int tailleY;
    private int minesRestantes;
    private int jeu;
    private int caseDecouverte;
    private int nombreMines;

    public GameBoard(int tailleX, int tailleY, int minesRestantes) {
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        this.minesRestantes = minesRestantes;
        this.nombreMines = minesRestantes;
        this.Grille = new Case[tailleX][tailleY];
        jeu = 0;
        caseDecouverte = 0;
        Random rand;
        int alea;
        
        for(int j=0;j<tailleX;j++){
            for(int k=0;k<tailleY;k++){
                Grille[j][k] = new Case(j,k,0);
            }
        }
        
        for(int i=0;i<minesRestantes;i++){
            do{
                rand = new Random();

                alea = rand.nextInt(tailleX*tailleY);
            }while(this.Grille[alea%tailleX][alea/tailleX].bombe());
            Grille[alea%tailleX][alea/tailleX].setBombe();
            for(Case C : this.voisines(alea%tailleX, alea/tailleX)){
                System.out.println("lol");
                C.incContenu();
            }
        }
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
        ArrayList<Case> Cases = voisines(x,y);
        for(Case C : Cases){
            if(C != null && C.bombe()){
                compteur ++;
            }
        }
        return compteur;
    }
    
    public void reveleCase(int x, int y){
        caseDecouverte ++;
        if(Grille[x][y].reveleCase() == 0){//s'il n'y a pas de bombe
            for(Case C : voisines(x, y)){
                reveleCase(C.getX(), C.getY());
            }
            if(caseDecouverte + this.nombreMines == this.tailleX*this.tailleY){
                this.jeu = 2;
            }
        }
        else{
            if(Grille[x][y].reveleCase() == 9){
                jeu = 1;
                reveleGrille();
            }
        }
    }
    
    public final ArrayList<Case> voisines(int x, int y){
        int compteur=0;
        ArrayList<Case> voisines = new ArrayList<>();
        for(int i=-1;i<2;i++)
        {
            for(int j=-1;j<2;j++)
            {
                if(j+y>=0 && i+x>=0 && i+x<tailleX && j+y<tailleY && !(j==0 && i==0))
                {
                    voisines.add(Grille[x+i][y+j]);
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
