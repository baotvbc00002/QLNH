package com.sinhvien.finalproject.DTO;

public class OrderdetailDTO {

    int Orderid, Foodid, Quantity;

    public int getOrderid() {
        return Orderid;
    }

    public void setOrderid(int orderid) {
        Orderid = orderid;
    }

    public int getFoodid() {
        return Foodid;
    }

    public void setFoodid(int foodid) {
        Foodid = foodid;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
