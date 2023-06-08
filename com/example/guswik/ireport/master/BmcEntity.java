package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2022.
 */
public class BmcEntity {


    private long idBMC = 0;
    /*
    1 = Key Partner ;
    2 = Key Activities;
    3 = Value Proposition;
    4 = Customer Relationship;
    5 = Customer Segmen;
    6 = Key Resources;
    7 = Channels;
    8 = Cost Structure;
    9 = Revenue Streams;
    */
    private int kategoriBMC = 0;
    private String keterangan = "";

    public long getIdBMC() {
        return idBMC;
    }

    public void setIdBMC(long idBMC) {
        this.idBMC = idBMC;
    }

    public int getKategoriBMC() {
        return kategoriBMC;
    }

    public void setKategoriBMC(int kategoriBMC) {
        this.kategoriBMC = kategoriBMC;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
