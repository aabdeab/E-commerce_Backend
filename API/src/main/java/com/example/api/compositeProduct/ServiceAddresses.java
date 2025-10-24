package com.example.api.compositeProduct;

public class ServiceAddresses {
    private String compositeAddress;
    private String productAddress;
    private String recommendationAddress;
    private String reviewAddress;

    public ServiceAddresses() {
    }

    public ServiceAddresses(String compositeAddress, String productAddress,
                           String recommendationAddress, String reviewAddress) {
        this.compositeAddress = compositeAddress;
        this.productAddress = productAddress;
        this.recommendationAddress = recommendationAddress;
        this.reviewAddress = reviewAddress;
    }

    public String getCompositeAddress() {
        return compositeAddress;
    }

    public void setCompositeAddress(String compositeAddress) {
        this.compositeAddress = compositeAddress;
    }

    public String getProductAddress() {
        return productAddress;
    }

    public void setProductAddress(String productAddress) {
        this.productAddress = productAddress;
    }

    public String getRecommendationAddress() {
        return recommendationAddress;
    }

    public void setRecommendationAddress(String recommendationAddress) {
        this.recommendationAddress = recommendationAddress;
    }

    public String getReviewAddress() {
        return reviewAddress;
    }

    public void setReviewAddress(String reviewAddress) {
        this.reviewAddress = reviewAddress;
    }
}
