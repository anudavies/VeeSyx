package uk.tees.ac.uk.b1197000.veesyx.chefFoodPanel;

public class FoodDetails {
    public String Dishes,Quantity,Description,ImageURL,RandomUID,Chefid,Price;


    public FoodDetails(String dishes, String quantity, String price, String description, String imageURL, String randomUID, String chefid) {
        Dishes = dishes;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUID = randomUID;
        Chefid = chefid;
    }
}
