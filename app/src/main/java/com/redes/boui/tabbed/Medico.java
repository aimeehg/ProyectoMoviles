package com.redes.boui.tabbed;

/**
 * Created by Boui on 26/03/2017.
 */

public class Medico {
    protected String nombre, apellidos, dirConsultorio, celular, email;
    protected int id_med,idPaciente;
   // public Medico(){}
    public Medico(int id_med, String nombre, String apellidos, String dirConsultorio, String celular,
    String email, int idPaciente){
        this.id_med = id_med;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dirConsultorio = dirConsultorio;
        this.celular = celular;
        this.email = email;
        this.idPaciente = idPaciente;
    }

    public int getId_med() {
        return id_med;
    }

    public void setId_med(int id_med) {
        this.id_med = id_med;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
