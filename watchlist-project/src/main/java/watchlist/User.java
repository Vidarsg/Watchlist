package watchlist;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    private int userID;
    private String name;
    private int age;
    private Collection<String> movies = new ArrayList<String>();

    public User(String name, int age) {
        this.name = name;
        if (age<0) {throw new IllegalArgumentException("Age must be a positive integer.");}
        this.age = age;
    }

    public int getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public Collection<String> getMovies() {
        return new ArrayList<String>(movies);
    }
}
