package com.sinhvien.finalproject.DTO;

public class StaffDTO {

    String FULLNAME,USERNAME,PASSWORD,EMAIL,PHONE,SEX,DATEOFBIRTH;
    int STAFFID,POSITIONID;

    public int getPOSITIONID() {
        return POSITIONID;
    }

    public void setPOSITIONID(int POSITIONID) {
        this.POSITIONID = POSITIONID;
    }

    public int getSTAFFID() {
        return STAFFID;
    }

    public void setSTAFFID(int STAFFID) {
        this.STAFFID = STAFFID;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String TENDN) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getDATEOFBIRTH() {
        return DATEOFBIRTH;
    }

    public void setDATEOFBIRTH(String DATEOFBIRTH) {
        this.DATEOFBIRTH = DATEOFBIRTH;
    }


}
