package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserLogin
{
    @Id
    @Column(name = "user_Id")
    private int userId;

    private String user_username;

    private byte[] password;
    private byte[] salt;

    public int getUserId()
    {
        return userId;
    }

    public byte[] getPassword()
    {
        return password;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public String getUserusername()
    {
        return user_username;
    }
}
