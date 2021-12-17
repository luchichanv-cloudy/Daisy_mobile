package dataclass;

public class kitchen {
    private String Name,Address,Email,Phonenumber,ImageID;
    private int vote;

    public kitchen(){}
    public kitchen(String name, String address, String email, String phonenumber,String imageID) {
        Name = name;
        Address = address;
        Email = email;
        Phonenumber = phonenumber;
        ImageID= imageID;
        this.vote=0;
    }

    public String getImageID() {
        return ImageID;
    }

    public void setImageID(String imageID) {
        ImageID = imageID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
