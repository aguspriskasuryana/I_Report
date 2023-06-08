package com.example.guswik.ireport.master;

import java.util.Date;

/**
 * Created by GUSWIK on 7/17/2017.
 */
public class TransactionEntity {
    private long idTransaction = 0;
    private String noTransaction = "";
    private long idAkun = 0;
    private String noteTransaction = "";
    private double valueTransaction = 0;
    private int typeTransaction = 0;
    private Date dateTransaction = new Date();

    public long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getNoTransaction() {
        return noTransaction;
    }

    public void setNoTransaction(String noTransaction) {
        this.noTransaction = noTransaction;
    }

    public long getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(long idAkun) {
        this.idAkun = idAkun;
    }

    public String getNoteTransaction() {
        return noteTransaction;
    }

    public void setNoteTransaction(String noteTransaction) {
        this.noteTransaction = noteTransaction;
    }

    public double getValueTransaction() {
        return valueTransaction;
    }

    public void setValueTransaction(double valueTransaction) {
        this.valueTransaction = valueTransaction;
    }

    public int getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(int typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}
