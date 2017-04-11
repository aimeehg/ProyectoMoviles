package com.redes.boui.tabbed;

/**
 * Created by Boui on 26/03/2017.
 */

public class RegistroGlucosa {
   protected String yyyy, mm, dd, hh, cuando;
    protected int nivel, idPaciente;

    public RegistroGlucosa(String yyyy, String mm, String dd, String hh, String cuando, int nivel,
                           int idPaciente){
        this.yyyy = yyyy;
        this.mm = mm;
        this.dd = dd;
        this.hh = hh;
        this.cuando = cuando;
        this.nivel = nivel;
        this.idPaciente = idPaciente;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getCuando() {
        return cuando;
    }

    public void setCuando(String cuando) {
        this.cuando = cuando;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
}
