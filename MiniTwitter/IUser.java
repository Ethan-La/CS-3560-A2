import java.util.ArrayList;

public interface IUser {
    
    public String getUID();

    public ArrayList<User> getFollowerList();

    public ArrayList<User> getFollowingList();

    public void followUser(User uName);

}
