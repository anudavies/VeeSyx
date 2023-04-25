package uk.tees.ac.uk.b1197000.veesyx.db;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import uk.tees.ac.uk.b1197000.veesyx.UpdateDishModel;
import uk.tees.ac.uk.b1197000.veesyx.entities.ChangeNumberItemsListener;
import uk.tees.ac.uk.b1197000.veesyx.entities.Food;

public class CartManagement {
    private Context context;
    private TinyDB tinyDB;

    public CartManagement(Context context){
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertFood(UpdateDishModel item){
        ArrayList<UpdateDishModel> listFood = getListCarts();
        boolean existAlready = false;
        int n =0;
        for (int i = 0; i< listFood.size(); i++){
            if (listFood.get(i).getDishes().equals(item.getDishes())){
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready){
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        }else {
            listFood.add(item);
        }
        tinyDB.putListObject("CardLists", listFood);
        Toast.makeText(context, "Added to Your Cart",Toast.LENGTH_SHORT).show();
    }
    public void insertFoods(UpdateDishModel item){
        ArrayList<UpdateDishModel> listFood = getListCarts();
        boolean existAlready = false;
        int n =0;
        for (int i = 0; i< listFood.size(); i++){
            if (listFood.get(i).getDishes().equals(item.getDishes())){
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready){
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        }else {
            listFood.add(item);
        }
        tinyDB.putListObject("CardLists", listFood);
        Toast.makeText(context, "Added to Your Cart",Toast.LENGTH_SHORT).show();
    }
//    public ArrayList<Food> getListCart(){
//        return tinyDB.getListObject("CardList");
//    }
    public ArrayList<UpdateDishModel> getListCarts(){
        return tinyDB.getListObject("CardLists");
    }

    public void plusNumberFood(ArrayList<UpdateDishModel> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardLists", listFood);
        changeNumberItemsListener.changed();
    }

    public void minusNumberFood(ArrayList<UpdateDishModel> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if(listFood.get(position).getNumberInCart() == 1){
            listFood.remove(position);
        }else{
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardLists", listFood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee(){
        ArrayList<UpdateDishModel> listFood = getListCarts();
        double fee = 0;
        for (int i = 0; i < listFood.size(); i++){
            fee = fee + (listFood.get(i).getPrice() * listFood.get(i).getNumberInCart());
        }
        return fee;
    }
}
