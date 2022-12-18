import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class User extends DefaultMutableTreeNode implements Subject, Observer, Visitor {

    private String userID;
    private ArrayList<Observer> followerList;
    private ArrayList<User> followingList;

    private long creationTime;
    private long lastUpdateTime;
    private Timestamp timeCreation;
    private Timestamp timeUpdate;
    private FeedList newsFeed;
    private String groupID;
    private Message newMessage;
    


    public User(String uid) {
        userID = uid;
        followerList = new ArrayList<Observer>();
        followingList = new ArrayList<User>();
        newsFeed = new FeedList(this);
        groupID = null;
        newMessage = null;
        creationTime = System.currentTimeMillis();
        lastUpdateTime = System.currentTimeMillis();
        timeCreation = new Timestamp(creationTime);
        timeUpdate = new Timestamp(lastUpdateTime);
        System.out.println(printCreationTimeToConsole());
    }

    public String getUID() {
        return userID;
    }

    public String getGroupID() {
        return groupID;
    }

    public ArrayList<Observer> getFollowerList() {
        return followerList;
    }

    public ArrayList<User> getFollowingList() {
        return followingList;
    }

    public FeedList getNewsFeed() {
        return newsFeed;
    }
    
    public long getCreationTime() {
        return creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void refreshLastUpdateTime() {
        lastUpdateTime = System.currentTimeMillis();
        timeUpdate.setTime(lastUpdateTime);
    }

    // When user presses follow button,
    // Adds typed name to following list of current user
    // Adds current user to entered user's follower list
    public void followUser(User uName) {
        this.getFollowingList().add(uName);
        attach(uName);
    }

    // Updates followers' feeds w user's msg
    public void post(String string) {
        newMessage = new Message(this, string);
        newsFeed.sendMessage(newMessage); 
        notifyAllObservers();
    }

    public void setGroupName(String gid) {
        groupID = gid;
    }

    public String toString() {
        return getUID();
    }

    public String printCreationTimeToConsole() {
        String temp = "User \"" + getUID() + "\" has been created @ " + timeCreation.toString();
        return temp;
    }

    public String printUserUpdateToConsole() {
        String temp = "User \"" + getUID() + "\" has created a new post @ " + timeUpdate.toString() + "!";
        return temp;
    }

    // Allows various types of Visitor classes & conducts calcs
    @Override
    public void accept(AdminVisitor adminVisitor) {
        adminVisitor.visit(this);
    }

    // Attaches user to other user's follower list
    @Override
    public void attach(Observer observe) {
        ((User) observe).getFollowerList().add(this);
        
    }

    // Notifies all users of new post
    @Override
    public void notifyAllObservers() {
        for(Observer observe : followerList) {
            observe.update(newMessage);
        }
        
    }

    // Updates feeds of other users
    @Override
    public void update(Message message) {
        newsFeed.addToFeed(message);
        
    }
    
}
