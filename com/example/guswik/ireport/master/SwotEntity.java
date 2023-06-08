package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2022.
 */
public class SwotEntity {


    private long idSwot = 0;
    //1 = Strength ; 2 = Weaknesses; 3 = Opportunities; 4 = Threats
    private int kategori = 0;
    private String keterangan = "";

    public long getIdSwot() {
        return idSwot;
    }

    public void setIdSwot(long idSwot) {
        this.idSwot = idSwot;
    }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
