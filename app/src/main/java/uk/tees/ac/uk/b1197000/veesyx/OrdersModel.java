package uk.tees.ac.uk.b1197000.veesyx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdersModel implements Serializable {
    String date,address,paymentMethod,phone,city,name,totalAmount,randomUID,status,time,orderId;
    List<String> items;
    public OrdersModel(){}

    public OrdersModel(String aDate, String aTotalAmount, String aStatus, String aTime, String address, String paymentMethod,
                       String phone, String city, String name, List<String> Items,String OrderId){
        this.date = aDate;
        this.totalAmount = aTotalAmount;
        this.status = aStatus;
        this.time = aTime;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.phone = phone;
        this.name = name;
        this.city = city;
        this.items = Items;
        this.orderId = OrderId;

    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }
    public String getOrderId(){
        return orderId;
    }
    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setRandomUID(String randomUID) {
        this.randomUID = randomUID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }


    public String getRandomUID() {
        return randomUID;
    }
    public String getDate(){ return date;}
    public String getTotalPrice(){return totalAmount;}
    public String getStatus(){return status;}
}
