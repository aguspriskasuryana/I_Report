package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */
public class ProductEntity {


    private long idProduct = 0;
    private String noProduct = "";
    private String namaProduct = "";
    private long idProductType = 0;
    private long productStatusBB = 0;
    private long productStatusSDM = 0;
    private long productStatusPEM = 0;
    private long productStatusPP = 0;
    private long productStatusNPV = 0;
    private long productStatusPI = 0;

    public String getNoProduct() {
        return noProduct;
    }

    public void setNoProduct(String noProduct) {
        this.noProduct = noProduct;
    }

    public String getNamaProduct() {
        return namaProduct;
    }

    public void setNamaProduct(String namaProduct) {
        this.namaProduct = namaProduct;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public long getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(long idProductType) {
        this.idProductType = idProductType;
    }

    public long getProductStatusBB() {
        return productStatusBB;
    }

    public void setProductStatusBB(long productStatusBB) {
        this.productStatusBB = productStatusBB;
    }

    public long getProductStatusSDM() {
        return productStatusSDM;
    }

    public void setProductStatusSDM(long productStatusSDM) {
        this.productStatusSDM = productStatusSDM;
    }

    public long getProductStatusPEM() {
        return productStatusPEM;
    }

    public void setProductStatusPEM(long productStatusPEM) {
        this.productStatusPEM = productStatusPEM;
    }

    public long getProductStatusPP() {
        return productStatusPP;
    }

    public void setProductStatusPP(long productStatusPP) {
        this.productStatusPP = productStatusPP;
    }

    public long getProductStatusNPV() {
        return productStatusNPV;
    }

    public void setProductStatusNPV(long productStatusNPV) {
        this.productStatusNPV = productStatusNPV;
    }

    public long getProductStatusPI() {
        return productStatusPI;
    }

    public void setProductStatusPI(long productStatusPI) {
        this.productStatusPI = productStatusPI;
    }
}
