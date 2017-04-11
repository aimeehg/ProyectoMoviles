package com.redes.boui.tabbed;

/**
 * Created by Boui on 26/03/2017.
 */

public class Medico {
    protected String nombre, paterno, materno, dirConsultorio, celular, email;
    protected int idPaciente;
    public Medico(String nombre, String paterno, String materno, String dirConsultorio, String celular,
    String email, int idPaciente){
        this.nombre = nombre;
        this.paterno = materno;
        this.materno = materno;
        this.dirConsultorio = dirConsultorio;
        this.celular = celular;
        this.email = email;
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getDirConsultorio() {
        return dirConsultorio;
    }

    public void setDirConsultorio(String dirConsultorio) {
        this.dirConsultorio = dirConsultorio;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
}
