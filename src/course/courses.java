package course;
import java.io.File;
import java.sql.*;
import java.sql.SQLException;
public class courses {
    String name;
    String reviews;
    int ratings;
    File coursematerial;
    String prerequesites;
    String coursematerialname;
    courses(String n, String rev, int rate,File material,String pre,String materialname)
    {
        name=n;
        reviews=rev;
        ratings=rate;
        coursematerial=material;
        prerequesites=pre;
        coursematerialname=materialname;
    }
    public courses()
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
    public void setrating(int rating)
    {
        ratings=rating;
    }
    public int getRatings()
    {
        return ratings;
    }
    public void setReviews(String Review)
    {
        reviews=Review;
    }
    public String getReviews()
    {
        return reviews;
    }
    public void setCoursematerial(File material)
    {
        coursematerial=material;
    }
    public File getCoursematerial()
    {
        return coursematerial;
    }
    public void setprerequesites(String pre){prerequesites=pre;}
    public String getprerequesites(){return prerequesites;}
    public void setCoursematerialname(String materialname)
    {
        coursematerialname=materialname;
    }
    public String getCoursematerialname()
    {
        return coursematerialname;
    }

}
