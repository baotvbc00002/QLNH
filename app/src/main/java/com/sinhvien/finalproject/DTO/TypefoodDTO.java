package com.sinhvien.finalproject.DTO;

public class TypefoodDTO {

    int Typeid;
    String Typename;
    byte[] Image;


    public int getTypeid() {
        return Typeid;
    }

    public void setTypeid(int typeid) {
        Typeid = typeid;
    }

    public String getTypename() {
        return Typename;
    }

    public void setTypename(String typename) {
        Typename = typename;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

}
