package com.sinhvien.finalproject.DTO;

public class FoodDTO {

    int Foodid, Typeid;
    String Foodname,Price,Status;
    byte[] Image;

    public int getFoodid() {
        return Foodid;
    }

    public void setFoodid(int foodid) {
        Foodid = foodid;
    }

    public int getTypeid() {
        return Typeid;
    }

    public void setTypeid(int typeid) {
        Typeid= typeid;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        Status = status;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

}
