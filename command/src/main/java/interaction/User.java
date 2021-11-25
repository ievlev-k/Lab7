package interaction;


import java.io.Serializable;


public class User implements Serializable {
    private String username;
    private String password;

    public User (String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
    @Override
    public String toString(){
        return username + ":" + password;
    }

    @Override
    public int hashCode(){
        return username.hashCode()+password.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof User) {
            User user = (User) o;
            return username.equals(user.getUsername()) && password.equals(user.getPassword());
        }
        return false;
    }

}
