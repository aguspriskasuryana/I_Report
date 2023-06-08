package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2022.
 */
public class PerhitunganInvestasiEntity {


    private long idPerhitunganInvestasi = 0;
    private String sumberDana = "";
    private long tahun = 0;
    private long nominal = 0;
    private long modalinvestasi = 0;
    private long modalkerja = 0;


    public long getIdPerhitunganInvestasi() {
        return idPerhitunganInvestasi;
    }

    public void setIdPerhitunganInvestasi(long idPerhitunganInvestasi) {
        this.idPerhitunganInvestasi = idPerhitunganInvestasi;
    }

    public String getSumberDana() {
        return sumberDana;
    }

    public void setSumberDana(String sumberDana) {
        this.sumberDana = sumberDana;
    }

    public long getTahun() {
        return tahun;
    }

    public void setTahun(long tahun) {
        this.tahun = tahun;
    }


    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    public long getModalinvestasi() {
        return modalinvestasi;
    }

    public void setModalinvestasi(long modalinvestasi) {
        this.modalinvestasi = modalinvestasi;
    }

    public long getModalkerja() {
        return modalkerja;
    }

    public void setModalkerja(long modalkerja) {
        this.modalkerja = modalkerja;
    }
}
