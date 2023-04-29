package uk.tees.ac.uk.b1197000.veesyx.customerFoodPanel;

public class Order {
    String date,totalAmount,randomUID,status,time;

    public Order(String date, String totalAmount,String status,String time){
        this.date = date;
        this.totalAmount = totalAmount;
        this.status = status;
        this.time = time;

    }
    public String getRandomUID() {
        return randomUID;
    }
    public String getDate(){ return date;}
    public String getTotalPrice(){return totalAmount;}
    public String getStatus(){return status;}
}
