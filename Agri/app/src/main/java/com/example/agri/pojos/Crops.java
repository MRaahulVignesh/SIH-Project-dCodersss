package com.example.agri.pojos;

import com.google.type.Date;

public class Crops {
    String cropName, cropId;
    Integer totalQuantity, remainingQuantity, price;
    Boolean organic;
    String sellerId;
    Date expectedDate;
    Boolean delivered;
    String imageURL;

    public Crops() {
    }

    public Crops(String cropName, String cropId, Integer totalQuantity, Integer remainingQuantity, Integer price, Boolean organic, String sellerId, Date expectedDate, Boolean delivered, String imageURL) {
        this.cropName = cropName;
        this.cropId = cropId;
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = remainingQuantity;
        this.price = price;
        this.organic = organic;
        this.sellerId = sellerId;
        this.expectedDate = expectedDate;
        this.delivered = delivered;
        this.imageURL = imageURL;
    }

    public Crops(String cropName, String cropId, Integer totalQuantity, Integer remainingQuantity, Boolean organic, String sellerId, Date expectedDate, Boolean delivered, String imageURL) {
        this.cropName = cropName;
        this.cropId = cropId;
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = remainingQuantity;
        this.organic = organic;
        this.sellerId = sellerId;
        this.expectedDate = expectedDate;
        this.delivered = delivered;
        this.imageURL = imageURL;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public Boolean getOrganic() {
        return organic;
    }

    public void setOrganic(Boolean organic) {
        this.organic = organic;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}