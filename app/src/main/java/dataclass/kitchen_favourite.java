package dataclass;

public class kitchen_favourite {
    private String kitchenid, userid;
    public kitchen_favourite(String kitchenid, String userid){
        this.kitchenid=kitchenid;
        this.userid=userid;
    }

    public kitchen_favourite(){

    }
    public void setKitchenid(String kitchenid) {
        this.kitchenid = kitchenid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getKitchenid() {
        return kitchenid;
    }

    public String getUserid() {
        return userid;
    }
}
