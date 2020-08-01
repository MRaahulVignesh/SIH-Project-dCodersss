package com.example.agri.pojos;

import com.google.type.Date;

public class Crops {
    String name;
    Integer quantity;
    Boolean organic;
    String OwnerId;
    Date expectedDate;
    Boolean delivered;
    String imageURL;

    public Crops(String name, Integer quantity, Boolean organic, String ownerId, Date expectedDate, Boolean delivered, String imageURL) {
        this.name = name;
        this.quantity = quantity;
        this.organic = organic;
        OwnerId = ownerId;
        this.expectedDate = expectedDate;
        this.delivered = delivered;
        this.imageURL = imageURL;
    }

    public Crops() {
    }

    public Crops(String name, Integer quantity, Boolean organic, String ownerId, Date expectedDate, Boolean delivered) {
        this.name = name;
        this.quantity = quantity;
        this.organic = organic;
        OwnerId = ownerId;
        this.expectedDate = expectedDate;
        this.delivered = delivered;
    }

    public Crops(String name, int quantity, Boolean organic, String ownerId) {
        this.name = name;
        this.quantity = quantity;
        this.organic = organic;
        OwnerId = ownerId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getOrganic() {
        return organic;
    }

    public void setOrganic(Boolean organic) {
        this.organic = organic;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }
}
