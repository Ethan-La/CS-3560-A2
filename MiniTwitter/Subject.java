public interface Subject {

    // Adds user to follower list of main User
    public void attach(Observer observe);

    // Notifies followers of post to update Feed
    public void notifyAllObservers();

}
