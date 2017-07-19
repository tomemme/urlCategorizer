package models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Link")
public class Link
{
    public static final int URL_LINK_MAX_LENGTH = 900;
    public static final int LINK_COMMENTS_MAX_LENGTH = 250;
    public static final int LINK_RATING_MAX_LENGTH = 6;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Link_Id")
    private int linkId;

    @Column(name = "User_Id")
    private int userId;

    @Column(name = "Category_Id")
    private int categoryId;

    @Column(name = "Link_Counter")
    private int linkCounter;

    @Column(name = "Link_Rating")
    private int linkRating;

    @Column(name = "Link_WatchedDate")
    private LocalDate linkWatchedDate;

    @Column(name = "Link_WatchBy")
    private LocalDate linkWatchBy;

    @Column(name = "Link_Comments")
    private String linkComments;

    @Column(name = "Link_Url")
    private String linkUrl;



    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getLinkId()
    {
        return linkId;
    }

    public void setLinkId(int linkId)
    {
        this.linkId = linkId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getLinkCounter()
    {
        return linkCounter;
    }

    public void setLinkCounter(int linkCounter)
    {
        this.linkCounter = linkCounter;
    }

    public int getLinkRating()
    {
        return linkRating;
    }

    public void setLinkRating(int linkRating)
    {
        this.linkRating = linkRating;
    }

    public LocalDate getLinkWatchedDate()
    {
        return linkWatchedDate;
    }

    public void setLinkWatchedDate(LocalDate linkWatchedDate)
    {
        this.linkWatchedDate = linkWatchedDate;
    }

    public LocalDate getLinkWatchBy()
    {
        return linkWatchBy;
    }

    public void setLinkWatchBy(LocalDate linkWatchBy)
    {
        this.linkWatchBy = linkWatchBy;
    }

    public String getLinkComments()
    {
        return linkComments;
    }

    public void setLinkComments(String linkComments)
    {
        this.linkComments = linkComments;
    }

    public String getLinkUrl()
    {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }
}
