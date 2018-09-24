package com.mp.jnotes;

import java.io.Serializable;

public class Nota implements Serializable {

    private int id;
    private String gruppo;
    private String titolo;
    private String testo;
    private String aggiunta;
    private String modifica;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGruppo() {
        return gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getAggiunta() {
        return aggiunta;
    }

    public void setAggiunta(String aggiunta) {
        this.aggiunta = aggiunta;
    }

    public String getModifica() {
        return modifica;
    }

    public void setModifica(String modifica) {
        this.modifica = modifica;
    }
}
