package com.example.alessandro.testbottom;

// Clase para modelizar los problemas

import java.io.Serializable;

public class Problema implements Serializable{
    String nombre;
    String desc;

    public Problema(String nom, String desc){
        this.nombre = nom;
        this.desc = desc;
    }

    public String getName(){
        return this.nombre;
    }

    public String getDesc(){
        return this.desc;
    }

}
