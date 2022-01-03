package dataclass;

public class item {
    private String name,imageID, description, category, kitchenID;
    private int price,pricesale;
    private boolean salestatus;

    public item(String name, String imageID, String description, String category, String kitchenID, int price, int pricesale, boolean salestatus) {
        this.name = name;
        this.imageID = imageID;
        this.description = description;
        this.category = category;
        this.kitchenID = kitchenID;
        this.price = price;
        this.pricesale = pricesale;
        this.salestatus = salestatus;
    }
    public item(){};
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKitchenID() {
        return kitchenID;
    }

    public void setKitchenID(String kitchenID) {
        this.kitchenID = kitchenID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPricesale() {
        return pricesale;
    }

    public void setPricesale(int pricesale) {
        this.pricesale = pricesale;
    }

    public boolean isSalestatus() {
        return salestatus;
    }

    public void setSalestatus(boolean salestatus) {
        this.salestatus = salestatus;
    }

    @Override
    public String toString() {
        return this.name ;
    }
}
