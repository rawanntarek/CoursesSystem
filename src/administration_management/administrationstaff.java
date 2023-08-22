package administration_management;

public class administrationstaff {
    String name;
    String email;

    String password;
    administrationstaff(String n,String mail,String pw)
    {
        name=n;
        email=mail;
        password=pw;

    }
    administrationstaff()
    {

    }
    public void setname(String n)
    {
        name=n;
    }
    String getname()
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


    public void setPassword(String pw)
    {
        password=pw;
    }
    public String getPassword()
    {
        return password;
    }

}