public class Main {
    
    // Driver
    public static void main(String[] args) {
        
        // Create Singleton instance of Admin. Prevents duplicates.
        Admin.getInstance();
        System.out.println("Mini Twitter Loaded!");
    }
    
}
