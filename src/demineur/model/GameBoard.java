/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.model;


import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private int jeu;
    /*
    * 0 jeu en cours
    * 1 le joueur à perdu 
    * 2 le joueur à gagné
    */
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
                C.incContenu();
            }
        }
    }
    
    /*
    * Renvoi des exceptions que je ne pouvais pas gérer dans la fonction
    */
    public GameBoard(String nomFichier) throws FileNotFoundException, IOException {
            int i;
            int j;
            byte[] Data;
            FileInputStream fichier = new FileInputStream(new File("sauvegarde.txt"));
            Data = new byte[5];
            fichier.read(Data);
            this.caseDecouverte=Data[0];
            this.minesRestantes=Data[1];
            this.nombreMines=Data[2];
            this.tailleX=Data[3];
            this.tailleY=Data[4];
            this.Grille = new Case[tailleX][tailleY];
            for(i=0; i<tailleX; i++){
                for(j=0;j<tailleY;j++){
                    fichier.read(Data);
                    Grille[i][j]=new Case(Data[3], Data[2], Data[0], Data[1], Data[4]);
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
    
    public int getCasedecouverte(){
        return this.caseDecouverte;
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
        this.callUpdate(); 
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
                if(C.getEtat()==0){
                    reveleCase(C.getX(), C.getY());
                }
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
        this.callUpdate();
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

    public void reveleGrille() {
        for(int i = 0; i<tailleX; i++){
            for(int j = 0; j<tailleY; j++){
                Grille[i][j].reveleCase();
            }
        }
    }
    
    public int[] chercheMine(){
        int[] res = new int[2];
        for(int i = 0; i<tailleX; i++){
            for(int j = 0; j<tailleY; j++){
                if(Grille[i][j].getEtat()!=0 && Grille[i][j].getContenu() ==9){
                    res[0]=i;
                    res[1]=j;
                    return res;
                }
                    
            }
        }
        return null;
    }
    
    
    public void sauvegarder(){
        try{
            byte[] Data;
            FileOutputStream fichier = new FileOutputStream(new File("sauvegarde.txt"));
            Data = new byte[this.tailleX*this.tailleY*5+5];
            Data[0]=(byte)this.caseDecouverte;
            Data[1]=(byte)this.minesRestantes;
            Data[2]=(byte)this.nombreMines;
            Data[3]=(byte)this.tailleX;
            Data[4]=(byte)this.tailleY;
            for(int i = 0; i<this.tailleX; i++){
                for(int j = 0; j<this.tailleY; j++){
                    Data[(i*this.tailleX+j)*5+5]=(byte)Grille[i][j].getX();
                    Data[(i*this.tailleX+j)*5+6]=(byte)Grille[i][j].getY();
                    Data[(i*this.tailleX+j)*5+7]=(byte)Grille[i][j].getContenu();
                    Data[(i*this.tailleX+j)*5+8]=(byte)Grille[i][j].getEtat();
                    Data[(i*this.tailleX+j)*5+9]=(byte)Grille[i][j].getDrapeau();
                }
            }
            fichier.write(Data);
        }
        catch(FileNotFoundException e){
        }
        catch(IOException e){
        }
    }

    
    public void callUpdate(){
        this.setChanged();
        this.notifyObservers();
    }
    
    public int getJeu() {
        return this.jeu;
    }

    
}
