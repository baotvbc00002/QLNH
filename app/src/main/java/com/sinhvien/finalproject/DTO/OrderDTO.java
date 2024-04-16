package com.sinhvien.finalproject.DTO;

public class OrderDTO {

    int Orderid,Tableid ,Staffid;
    String Status,Orderdate,Total;

    public int getOrderid() {
        return Orderid;
    }

    public void setOrderid(int orderid) {
        Orderid = orderid;
    }

    public int getTableid() {
        return Tableid;
    }

    public void setTableid(int tableid) {
        Tableid = tableid;
    }

    public int getStaffid() {
        return Staffid;
    }

    public void setStaffid(int staffid) {
        Staffid = staffid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderdate() {
        return Orderdate;
    }

    public void setOrderdate(String orderdate) {
        Orderdate = orderdate;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
