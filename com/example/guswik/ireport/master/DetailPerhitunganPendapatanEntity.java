package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2022.
 */
public class DetailPerhitunganPendapatanEntity {

    private long idDetailPerhitunganPendapatan = 0;
    private String RBA = "";
    private long tahun = 0;
    private long volume = 0;
    private long harga = 0;
    private long totalharga = 0;


    public long getIdDetailPerhitunganPendapatan() {
        return idDetailPerhitunganPendapatan;
    }

    public void setIdDetailPerhitunganPendapatan(long idDetailPerhitunganPendapatan) {
        this.idDetailPerhitunganPendapatan = idDetailPerhitunganPendapatan;
    }

    public String getRBA() {
        return RBA;
    }

    public void setRBA(String RBA) {
        this.RBA = RBA;
    }

    public long getTahun() {
        return tahun;
    }

    public void setTahun(long tahun) {
        this.tahun = tahun;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public long getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(long totalharga) {
        this.totalharga = totalharga;
    }
}
