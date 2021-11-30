package dataclass;

public class user {
    private String Name,Address,Email,Phonenumber;


    public user(){

    }
    public user(String name, String addres, String email, String phonenumber) {
        Name = name;
        Address = addres;
        Email = email;
        Phonenumber = phonenumber;
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
