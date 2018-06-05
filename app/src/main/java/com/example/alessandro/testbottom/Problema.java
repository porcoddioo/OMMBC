package com.example.alessandro.testbottom;

import java.io.Serializable;

public class Problema implements Serializable{
    String ID;
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

    public Problema(String ID, String area, String tema, String pregunta, String nivel){
        this.ID = ID;
        this.Area = area;
        this.Tema = tema;
        this.Pregunta = pregunta;
        this.Nivel = nivel;
    }


}
