package com.example.android.connectechome3.model;

/**
 * Created by caglar on 28.05.2017.
 */

public class Category {

    private String categoryID; //kategorinin idsi
    private String ustCatID; // üst kategorisinin idsi
    private String userID;  //kullanicisinin idsi
    private String categoryName; //kategorinin adı
    private boolean aygitMi= true; //ev oda gibi bir kategori (true) mi yoksa lamba kattle gibi aygit mi (false)

    //getter ve setterler
    public Category(){

    }
    public String getCategoryID(){
        return categoryID;
    }
    public void setCategoryIDId(String categoryIDId) {
        this.categoryID = categoryIDId;
    }

    public String getUstCatID(){
        return ustCatID;
    }

    public void setUstCatID(String ustCatID){
        this.ustCatID= ustCatID;
    }

    public String getUserID(){
        return userID;
    }

    public void setUserID(String userID){
        this.userID=userID;
    }

    public boolean getAygitMi(){
        return aygitMi;
    }

    public void setAygitMi(boolean aygitMi){
        this.aygitMi=aygitMi;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public void setCategoryName(String categoryName){
        this.categoryName=categoryName;
    }


    public Category(String categoryID, String ustCatID, String userID, String categoryName, boolean aygitMi){
        this.categoryID=categoryID;
        this.ustCatID=ustCatID;
        this.userID=ustCatID;
        this.categoryName=categoryName;
        this.aygitMi=aygitMi;

    }


}
