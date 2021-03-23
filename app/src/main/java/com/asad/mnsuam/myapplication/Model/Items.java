package com.asad.mnsuam.myapplication.Model;

public class Items {
   private String Iname,image,description,price ,itemId,discount;

    public Items() {
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Items(String iName, String Image, String Description, String Price, String ItemId, String Discount) {
        Iname = iName;
        image = Image;
        description = Description;
        price = Price;
        itemId = ItemId;
        discount = Discount;
    }


    public String getIname() {
        return Iname;
    }

    public void setIname(String iname) {
        Iname = iname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
