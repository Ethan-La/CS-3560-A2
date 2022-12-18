public class PositiveRatioCalculator implements AdminVisitor {

    @Override
    public int visit(User user) {
        return user.getNewsFeed().getPositiveCount();
    }
    
}
