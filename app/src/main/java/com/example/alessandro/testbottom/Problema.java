package com.example.alessandro.testbottom;

import java.io.Serializable;

public class Problema implements Serializable{
    int ID;
    String Area;
    String Tema;
    String Nivel;
    String Pregunta;
    String Imagen;
    String Solucion;
    String Tip;
    String Origen;
    String Siguiente;
    String Anterior;
    String Comentario;
    String Puntaje;

    public Problema(String tema, String preg){
        this.Tema = tema;
        this.Pregunta = preg;
    }

    public Problema(String area,String tema,String nivel){
        this.Area = area;
        this.Tema = tema;
        this.Nivel = nivel;
    }

    public Problema(String area, String tema, String pregunta, String nivel){
        this.Area = area;
        this.Tema = tema;
        this.Pregunta = pregunta;
        this.Nivel = nivel;
    }
    
    public Problema(//int id,
            String area,
            String tema,
            String nivel,
            String pregunta,
            String imagen,
            String solucion,
            String tip,
            String origen,
            String siguiente,
            String anterior,
            String comentario,
            String puntaje){
        //this.ID = id;
        this.Tema = tema;
        this.Nivel = nivel;
        this.Pregunta = pregunta;
        this.Imagen = imagen;
        this.Solucion = solucion;
        this.Tip = tip;
        this.Origen = origen;
        this.Siguiente = siguiente;
        this.Anterior = anterior;
        this.Comentario = comentario;
        this.Puntaje = puntaje;
        
    }


}
