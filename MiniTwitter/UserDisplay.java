import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class UserDisplay extends JFrame {
    
    // Upper Panel
    private JPanel userFollowPanel;
    private JPanel displayPane;

    private JTextPane userIDArea;
    private JTextField followUserTextField;
    private StyledDocument doc;
    private SimpleAttributeSet center;

    private JButton followButton;

    private JList<User> followerViewer;
    private DefaultListModel<User> followModel = new DefaultListModel<>();
    private JScrollPane followPane;

    // Lower Panel
    private JPanel messageTweetPanel;
    private JList<String> viewNewsFeed;
    private DefaultListModel<String> newsModel = new DefaultListModel<>();
    private JScrollPane newsPane;

    private JTextField messageInput;
    private JButton postMessageButton;

    
    private User user;

    public UserDisplay(User person) {
        user = person;

        initComponents();

    }

    private void initComponents() {
        // Initialize panels w items in same row
        userFollowPanel = new JPanel(new FlowLayout());
        messageTweetPanel = new JPanel(new FlowLayout());
        displayPane = new JPanel();

        // Initilize  JFrame
        displayPane.setLayout(new BoxLayout(displayPane, BoxLayout.Y_AXIS));
        this.setSize(800, 600);
        this.setResizable(false);
        this.setVisible(true);

        // Initialize text & buttons for header
        // Contains info on user & text field to enter name to follow
        userIDArea = new JTextPane();
        userIDArea.setText("User: " + user.getUID() + "   |||   Group: " + user.getGroupID());
        userIDArea.setBackground(getBackground());
        userIDArea.setEditable(false);
        doc = userIDArea.getStyledDocument();
        center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        followUserTextField = new JTextField();
        followUserTextField.setText("Input user ID to follow.");
        followButton = new JButton("Follow User");

        // Establishes JList to display Followers on JFrame
        followerViewer = new JList<>();
        followerViewer.setModel(followModel);

        followPane = new JScrollPane(followerViewer,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        followPane.setBounds(0, 0, 300, 0);
        
        followerViewer.setBackground(Color.WHITE);
        followPane.setBackground(Color.WHITE);

        crowdFollow();

        // Takes user String from textField & searchs for user
        // Updates Jlist field to display users followed by current user
        followButton.addActionListener(e -> {
            String userToSearch = followUserTextField.getText();
            User toFollow = search(userToSearch);
            user.followUser(toFollow);

            followModel.addElement(toFollow);
        });
        
        // When opening user feed, load info on users followed by current user
        crowdFeed();

        // Start Lower Half of JFrame

        // Initializes textField for user to enter msg to send
        messageInput = new JTextField(50);
        messageInput.setText("Input Message");
        postMessageButton = new JButton("Post Message");

        // Creates new Message Object
        // Sends msg to user's feed
        // Updates followers' feeds 
        postMessageButton.addActionListener(e -> {
            String tweet = messageInput.getText();
            Message newText = new Message(user, tweet);
            user.getNewsFeed().sendMessage(newText);

            newsModel.addElement(newText.toString());

            updateFeed(newText);

        });


        // Creates JList to display msgs
        viewNewsFeed = new JList<>();
        viewNewsFeed.setModel(newsModel);

        newsPane = new JScrollPane(viewNewsFeed, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newsPane.setBounds(0, 0, 300, 0);

        viewNewsFeed.setBackground(Color.WHITE);

        // Create first row of items on JFrame

        userFollowPanel.add(userIDArea);
        userFollowPanel.add(followUserTextField);
        userFollowPanel.add(followButton);

        displayPane.add(userFollowPanel);

        displayPane.add(followPane);
        // Create Next Row

        messageTweetPanel.add(messageInput);
        messageTweetPanel.add(postMessageButton);

        displayPane.add(messageTweetPanel);
        displayPane.add(newsPane);

        this.add(displayPane); 
    }

    // Calls Admin to search for user inputted to JTextField
    private User search(String userToSearch) {
        User tempPerson = Admin.getInstance().searchUser(userToSearch);

        return tempPerson;
    }

    // Creates list of users followed by selected user when "View User" is pressed
    private void crowdFeed() {
        int temp = user.getNewsFeed().getFeedList().size();
        for(int i = 0; i < temp; i++)
            {
                newsModel.addElement(user.getNewsFeed().getFeedList().get(temp - i - 1).toString());
            }
    }

    // Creates list of recent msgs to & from selected user when "View User" is pressed
    private void crowdFollow() {
        int temp = user.getFollowingList().size();
        for(int i = 0; i < temp; i++)
            {
                followModel.addElement(user.getFollowingList().get(i));
            }

    }
    
    // Conducts live updates to feedList of sender's followers
    private void updateFeed(Message text) {
        for(int i = 0; i < Admin.getInstance().getOpenPanels().size(); i++){
            if(user.getFollowerList().contains(((UserDisplay)Admin.getInstance().getOpenPanels().get(i)).getUser())) {
                ((UserDisplay)Admin.getInstance().getOpenPanels().get(i)).getNewsModel().addElement(text.toString());
            }
        }
    } 

    public User getUser() {
        return user;
    }

    // Gets newModel & followModel to update other feeds
    public DefaultListModel<String> getNewsModel() {
        return newsModel;
    }
    public DefaultListModel<User> getFollowModel() {
        return followModel;
    }

}
