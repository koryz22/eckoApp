public class User {
    private final String username;
    private final int userid;
    public User(String username, int userid) {
        this.userid = userid;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userid=" + userid +
                '}';
    }
    public String getUsername() {
        return username;
    }

    public int getUserid() {
        return userid;
    }

}
