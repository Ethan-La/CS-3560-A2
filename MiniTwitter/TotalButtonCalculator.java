public class TotalButtonCalculator implements AdminVisitor {

    // Visitor class calcs total sent msgs
    @Override
    public int visit(User user) {
        return user.getNewsFeed().getMessageCount();
    }

}
