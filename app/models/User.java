package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
public class User
{
    public static final int FIRST_NAME_MAX_LENGTH = 30;
    public static final int LAST_NAME_MAX_LENGTH = 30;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private int userId;

    @Column(name = "First_Name")
    @Constraints.MinLength(1)
    @Constraints.MaxLength(10)
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "User_Username")
    private String userUsername;

    @Column(name = "Password")
    private byte[] password;

    //TODO add salt to program
    @Column(name = "Salt")
    private byte[] salt;

    @Column(name = "User_Created")
    private LocalDateTime userCreated;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getFirstName()
    {
        return firstName;
    }


    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public LocalDateTime getUserCreated()
    {
        return userCreated;
    }

    public void setUserCreated(LocalDateTime userCreated)
    {
        this.userCreated = userCreated;
    }

    public String getUserUsername()
    {
        return userUsername;
    }

    public void setUserUsername(String userUsername)
    {
        this.userUsername = userUsername;
    }

    public byte[] getPassword()
    {
        return password;
    }

    public void setPassword(byte[] password)
    {
        this.password = password;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public void setSalt(byte[] salt)
    {
        this.salt = salt;
    }

    public String getFullName()
    {
        final String fullName;

        if (firstName == null)
        {
            fullName = lastName;
        }
        else if ( firstName != null && lastName == null)
        {
            fullName = firstName;
        }
        else
        {
            fullName = lastName + ", " + firstName;
        }

        return fullName;
    }



}
