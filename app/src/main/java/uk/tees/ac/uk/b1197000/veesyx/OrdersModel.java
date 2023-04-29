package uk.tees.ac.uk.b1197000.veesyx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrdersModel implements Serializable {
    String date,customerId,chefId,totalPrice,randomUID,status;
    ArrayList<UpdateDishModel> items;
    public OrdersModel(){}

    public OrdersModel(String date, String RandomUID, ArrayList<UpdateDishModel> Items,String Status){
        this.date = date;
        this.randomUID = RandomUID;
        this.items = Items;
        this.status = Status;
    }
    public String getRandomUID() {
        return randomUID;
    }
}
