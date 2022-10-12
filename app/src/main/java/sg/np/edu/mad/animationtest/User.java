package sg.np.edu.mad.animationtest;

import java.util.ArrayList;

public class User {

    public String username;
    public String password;
    public int id;
    public String name;
    public String description;
    public ArrayList<String> followedWho;
    public boolean followed; //stores a boolean value which is only "visible" to the user himself

    public User(int id, String name, String description, String username, String password, Boolean followed, ArrayList<String> followedWho){
        this.id = id;
        this.name = name;
        this.description = description;
        this.username = username;
        this.password = password;
        this.followed = followed;
        this.followedWho = followedWho;
    }

    public User(){

    }

    public void setFollowed(boolean followed){ this.followed = followed; }

    public boolean getFollowed() {return this.followed; }

    public void setFollowedWho(ArrayList<String> followedWho) { this.followedWho = followedWho; }

    public ArrayList<String> getFollowedWho(){ return this.followedWho; }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

}
