package com.example.crearbdsqlite.entidades;

import java.security.PublicKey;

public class Pais { //esta es la JAVA CLASS del objeto que quiera guardar en la BD

    //columnas
   private String nombrePais;
    private int poblacion;
    private int PIB;


    //constructor vacio para crear paises mas adelante en los metodos
    public Pais(){

    }

    //constructor para dar valores a las columnas
    public Pais(String nombrePais, int poblacion, int PIB) {
        this.nombrePais = nombrePais;
        this.poblacion = poblacion;
        this.PIB = PIB;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public int getPIB() {
        return PIB;
    }

    public void setPIB(int PIB) {
        this.PIB = PIB;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "nombrePais='" + nombrePais + '\'' +
                ", poblacion=" + poblacion +
                ", PIB=" + PIB +
                '}';
    }
}
