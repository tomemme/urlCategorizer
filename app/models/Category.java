package models;

import javax.persistence.*;

@Entity
@Table(name = "Category")
public class Category
{
    public static final int CATEGORY_NAME_MAX_LENGTH = 40;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Category_Id")
    private int categoryId;

    @Column(name = "User_id")
    private int userId;

    @Column(name = "Category_Name")
    private String categoryName;

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
}
