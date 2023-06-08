package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/23/2017.
 */
public class CompanyEntity {

    private long companyId = 0;
    private String companyName = "";
    private long  telp = 0;
    private String email = "";
    private String fax = "";
    private String alamat = "";
    private String namaBidangUsaha = "";
    private String direktur = "";
    private String sekretaris = "";
    private String bendahara = "";

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getTelp() {
        return telp;
    }

    public void setTelp(long telp) {
        this.telp = telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaBidangUsaha() {
        return namaBidangUsaha;
    }

    public void setNamaBidangUsaha(String namaBidangUsaha) {
        this.namaBidangUsaha = namaBidangUsaha;
    }

    public String getDirektur() {
        return direktur;
    }

    public void setDirektur(String direktur) {
        this.direktur = direktur;
    }

    public String getSekretaris() {
        return sekretaris;
    }

    public void setSekretaris(String sekretaris) {
        this.sekretaris = sekretaris;
    }

    public String getBendahara() {
        return bendahara;
    }

    public void setBendahara(String bendahara) {
        this.bendahara = bendahara;
    }
}
