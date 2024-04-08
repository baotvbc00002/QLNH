package com.sinhvien.finalproject.DTO;

public class DinnertableDTO {

    int Tableid;
    String Tablename;
    boolean Selected;


    public int getTableid() {
        return Tableid;
    }

    public void setTableid(int tableid) {
        Tableid = tableid;
    }

    public String getTablename() {
        return Tablename;
    }

    public void setTablename(String tablename) {
        Tablename = tablename;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
