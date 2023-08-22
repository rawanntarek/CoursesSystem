package trainee;

public class trainee {
    String name;
    int id;
    String email;
    int phonenumber;
    String password;
    trainee(String n,int id_,String mail,int number,String pw)
    {
        name=n;
        id=id_;
        email=mail;
        phonenumber=number;
        password=pw;

    }
    public trainee()
    {

    }
    public void setname(String n)
    {
        name=n;
    }
    public String getname()
    {
        return name;
    }
    public void setmail(String mail)
    {
        email=mail;
    }
    public String getmail()
    {
        return email;
    }
    public void setid(int id_)
    {
        id=id_;
    }
    public int getid()
    {
        return id;
    }
    public void setPhonenumber(int number)
    {
        phonenumber=number;
    }
    public int getPhonenumber()
    {
        return phonenumber;
    }
    public void setPassword(String pw)
    {
        password=pw;
    }
    public String getPassword()
    {
        return password;
    }

}