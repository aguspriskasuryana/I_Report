package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */
public class AkunEntity {


    private long idAkun = 0;
    private String noAkun = "";
    private String namaAkun = "";
    private long idAkunType = 0;

    public String getNoAkun() {
        return noAkun;
    }

    public void setNoAkun(String noAkun) {
        this.noAkun = noAkun;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public long getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(long idAkun) {
        this.idAkun = idAkun;
    }

    public long getIdAkunType() {
        return idAkunType;
    }

    public void setIdAkunType(long idAkunType) {
        this.idAkunType = idAkunType;
    }
}
