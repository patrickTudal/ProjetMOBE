package com.m2dl.projetmobe.Firebase;

public class HighScoreDAO {
    private String id;
    private String pseudo;
    private String score;

    public HighScoreDAO() {
    }

    public HighScoreDAO(String id, String pseudo, String score) {
        this.id = id;
        this.pseudo = pseudo;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
