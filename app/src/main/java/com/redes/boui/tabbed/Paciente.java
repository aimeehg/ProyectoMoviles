package com.redes.boui.tabbed;

/**
 * Created by Boui on 26/03/2017.
 */

public class Paciente {
    private String nom, pat, mat, dir;
    private int edad, peso, id;
    private float alt;
    private String usu, pass;
    private int gen, med;

    public Paciente(int id, String nom, String pat, String mat, String dir, int edad, int peso,
                    float alt, String usu, String pass, int gen, int med){
        this.id = id;
        this.nom = nom;
        this.pat = pat;
        this.mat = mat;
        this.dir = dir;
        this.edad = edad;
        this.peso = peso;
        this.alt = alt;
        this.usu = usu;
        this.pass = pass;
        this.gen = gen;
        this.med = med;
    }
    public Paciente(int id, String nom, String pat, String mat){
        this.id = id;
        this.nom = nom;
        this.pat = pat;
        this.mat = mat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPat() {
        return pat;
    }

    public void setPat(String pat) {
        this.pat = pat;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public float getAlt() {
        return alt;
    }

    public void setAlt(float alt) {
        this.alt = alt;
    }

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getGen() {
        return gen;
    }

    public void setGen(int gen) {
        this.gen = gen;
    }

    public int getMed() {
        return med;
    }

    public void setMed(int med) {
        this.med = med;
    }

}
