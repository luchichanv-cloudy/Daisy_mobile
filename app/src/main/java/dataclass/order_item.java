package dataclass;

import java.io.Serializable;

public class order_item implements Serializable {
    private String name,itemid;
    private Integer quantity,price;

    public order_item(String name, String itemid, Integer quantity, Integer price) {
        this.name = name;
        this.itemid = itemid;
        this.quantity = quantity;
        this.price = price;
    }

    public order_item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
