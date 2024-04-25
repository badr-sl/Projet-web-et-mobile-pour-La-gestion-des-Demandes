package com.example.projetmobile;

public class Demande {
    private long idDemande;
    private String title;
    private String sujet;
    private String date;
    private String etat;

    public Demande(long idDemande , String title, String sujet, String date, String etat) {
        this.idDemande = idDemande;
        this.title = title;
        this.sujet = sujet;
        this.date = date;
        this.etat = etat;
    }
    public Demande( String title, String sujet, String date, String etat) {

        this.title = title;
        this.sujet = sujet;
        this.date = date;
        this.etat = etat;
    }

    public long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(long idDemande) {
        this.idDemande = idDemande;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

}
