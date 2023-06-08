package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */
public class PaybackPeriodEntity {


    private long idPaybackPeriod = 0;
    private double nilaiInvestasitotal = 0;
    private double nilaiProceedPerTahun = 0;
    private double kasMasukBersih = 0;
    private long tahunMaximumPaybackPeriod = 0;
    private long idProduct = 0;
    private long productstatus = 0;


    public long getIdPaybackPeriod() {
        return idPaybackPeriod;
    }

    public void setIdPaybackPeriod(long idPaybackPeriod) {
        this.idPaybackPeriod = idPaybackPeriod;
    }

    public double getNilaiInvestasitotal() {
        return nilaiInvestasitotal;
    }

    public void setNilaiInvestasitotal(double nilaiInvestasitotal) {
        this.nilaiInvestasitotal = nilaiInvestasitotal;
    }

    public double getNilaiProceedPerTahun() {
        return nilaiProceedPerTahun;
    }

    public void setNilaiProceedPerTahun(double nilaiProceedPerTahun) {
        this.nilaiProceedPerTahun = nilaiProceedPerTahun;
    }

    public double getKasMasukBersih() {
        return kasMasukBersih;
    }

    public void setKasMasukBersih(double kasMasukBersih) {
        this.kasMasukBersih = kasMasukBersih;
    }


    public long getTahunMaximumPaybackPeriod() {
        return tahunMaximumPaybackPeriod;
    }

    public void setTahunMaximumPaybackPeriod(long tahunMaximumPaybackPeriod) {
        this.tahunMaximumPaybackPeriod = tahunMaximumPaybackPeriod;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public long getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(long productstatus) {
        this.productstatus = productstatus;
    }
}
