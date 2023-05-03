package uk.tees.ac.uk.b1197000.veesyx.entities;

import java.io.Serializable;

public class Food implements Serializable {
    private Integer mDbid;
    private String mType;
    private Integer mPrice;
    private String mName;
    private String mImage;
    private int NumberInCart;

    public Food() {
    }

    public Food(Integer aDbid, Integer aPrice, String aName, String aType, String aImage) {

        mDbid = aDbid;
        mPrice = aPrice;
        mName = aName;
        mType = aType;
        mImage = aImage;
    }

    public Food(Integer aDbid, Integer aPrice, String aName, String aType, String aImage, Integer aNumberInCart) {

        mDbid = aDbid;
        mPrice = aPrice;
        mName = aName;
        mType = aType;
        mImage = aImage;
        NumberInCart = aNumberInCart;
    }

    public Integer getDbid() {
        return mDbid;
    }

    public void setDbid(Integer mDbid) {
        this.mDbid = mDbid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Integer getPrice() {
        return mPrice;
    }

    public void setPrice(Integer mPrice) {
        this.mPrice = mPrice;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }


    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getNumberInCart() {
        return NumberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        NumberInCart = numberInCart;
    }
}
