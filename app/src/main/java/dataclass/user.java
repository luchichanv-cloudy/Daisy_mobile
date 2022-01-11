package dataclass;

public class user {
    private String Name,Address,Email,Phonenumber,ImageID, ID;


    public user(){

    }
    public user(String name, String addres, String email, String phonenumber,String imageID) {
        Name = name;
        Address = addres;
        Email = email;
        Phonenumber = phonenumber;
        ImageID= imageID;
    }
    public user(String id, String name, String phoneNumber, String email, String address, String imageID) {
        this.ID = id;
        this.Name = name;
        this.Phonenumber = phoneNumber;
        this.Email = email;
        this.Address = address;
        this.ImageID = imageID;
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


}
