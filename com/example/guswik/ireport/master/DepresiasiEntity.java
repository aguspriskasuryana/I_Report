package com.example.guswik.ireport.master;

import java.util.Date;

/**
 * Created by GUSWIK on 7/17/2017.
 */
public class DepresiasiEntity {
    private long idDepresiasi = 0;
    private long akunIdx = 0;
    private String noTransaction = "";
    private double hargaPerolehan = 0;
    private double nilaiSIsa = 0;
    private double umurEkonomi = 0;
    private double valueDepresiasi = 0;


    public long getIdDepresiasi() {
        return idDepresiasi;
    }

    public void setIdDepresiasi(long idDepresiasi) {
        this.idDepresiasi = idDepresiasi;
    }



    public double getHargaPerolehan() {
        return hargaPerolehan;
    }

    public void setHargaPerolehan(double hargaPerolehan) {
        this.hargaPerolehan = hargaPerolehan;
    }

    public double getNilaiSIsa() {
        return nilaiSIsa;
    }

    public void setNilaiSIsa(double nilaiSIsa) {
        this.nilaiSIsa = nilaiSIsa;
    }

    public double getUmurEkonomi() {
        return umurEkonomi;
    }

    public void setUmurEkonomi(double umurEkonomi) {
        this.umurEkonomi = umurEkonomi;
    }

    public double getValueDepresiasi() {
        return valueDepresiasi;
    }

    public void setValueDepresiasi(double valueDepresiasi) {
        this.valueDepresiasi = valueDepresiasi;
    }

    public String getNoTransaction() {
        return noTransaction;
    }

    public void setNoTransaction(String noTransaction) {
        this.noTransaction = noTransaction;
    }

    public long getAkunIdx() {
        return akunIdx;
    }

    public void setAkunIdx(long akunIdx) {
        this.akunIdx = akunIdx;
    }
}
