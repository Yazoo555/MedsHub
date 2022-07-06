package medshub.models;

public class MyCartModel {
    String currentTime;
    String currentDate;
    String productName;
    String productPrice;
    int totalPrice;
    String totalQuantity;

    public MyCartModel() {
    }

    public MyCartModel(String currentTime, String currentDate, String productName, String productPrice, int totalPrice, String totalQuantity) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.totalQuantity= totalQuantity;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
