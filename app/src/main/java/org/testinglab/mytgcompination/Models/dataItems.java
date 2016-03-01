package org.testinglab.mytgcompination.Models;

import android.graphics.Bitmap;

public class dataItems {

    private int productID;
    private String productNm;
    private String productCat;
    private String productDetails;
    private double productPrice;
    private String productImg;
    private Bitmap productPhoto;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductNm() {
        return productNm;
    }

    public void setProductNm(String productNm) {
        this.productNm = productNm;
    }

    public String getProductCat() {
        return productCat;
    }

    public void setProductCat(String productCat) {
        this.productCat = productCat;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Bitmap getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(Bitmap productPhoto) {
        this.productPhoto = productPhoto;
    }
}
