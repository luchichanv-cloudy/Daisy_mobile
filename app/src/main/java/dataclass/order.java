package dataclass;


import java.sql.Timestamp;
import java.util.ArrayList;

public class order {
    private String kitchen_id, user_id;
    private String created;
    private ArrayList<order_item> list;
    private int status,totalprice;
    public order(String kitchen_id, String user_id, String created, int status,int totalprice,ArrayList<order_item> list) {
        this.kitchen_id = kitchen_id;
        this.user_id = user_id;
        this.created = created;
        this.list = list;
        this.status=status;
        this.totalprice=totalprice;
    }

    public order() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKitchen_id() {
        return kitchen_id;
    }

    public void setKitchen_id(String kitchen_id) {
        this.kitchen_id = kitchen_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public ArrayList<order_item> getList() {
        return list;
    }

    public void setList(ArrayList<order_item> list) {
        this.list = list;
    }
}

