package uk.tees.ac.uk.b1197000.veesyx;

import androidx.annotation.IntegerRes;

import java.io.Serializable;

public class UpdateDishModel implements Serializable {
    String Dishes,RandomUID,Description,Quantity,ImageURL,ChefId;
    private int NumberInCart;
    Integer Price;

    public UpdateDishModel(){

    }
    public UpdateDishModel(Integer aDbid, Integer aPrice, String aName, String aType, String aImage) {
        Price = aPrice;
        Dishes = aName;
        ImageURL = aImage;
    }

    public UpdateDishModel(Integer aDbid, Integer aPrice, String aName, String aType, String aImage, Integer aNumberInCart) {
        Price = aPrice;
        Dishes = aName;
        ImageURL = aImage;
        NumberInCart = aNumberInCart;
    }
    public String getDishes() {
        return Dishes;
    }

    public void setDishes(String dishes) {
        Dishes = dishes;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }
    public int getNumberInCart() {
        return NumberInCart;
    }
    public void setNumberInCart(int numberInCart) {
        NumberInCart = numberInCart;
    }
}
