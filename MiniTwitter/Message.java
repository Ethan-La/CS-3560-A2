public class Message{
    
    private static int totalMessageCounter = 0;
    private String message;
    private User author;
    private int msgIndex;

    public Message(User user, String text) {
        message = text;
        author = user;
        msgIndex = totalMessageCounter;
        totalMessageCounter++;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return author;
    }

    public int getTotalMessages() {
        return totalMessageCounter;
    }

    public int getMessageIndex() {
        return msgIndex;
    }

    public String toString() {
        String temp = "@" + getUser() + ": " + getMessage();
        return temp;
    }
    
}
