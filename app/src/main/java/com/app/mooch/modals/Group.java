package com.app.mooch.modals;

import java.util.ArrayList;
import java.util.List;

public class Group {


    private String groupName;
    private String groupPic;
    private List<User> groupMembers;
    private String mooch;


    public Group(){
        this.groupMembers = new ArrayList<>();
    }

    public Group(String groupName, String groupPic, List<User> groupMembers) {
        this.groupName = groupName;
        this.groupPic = groupPic;
        this.groupMembers = groupMembers;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(String groupPic) {
        this.groupPic = groupPic;
    }

    public List<User> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void addGroupMember( User user ){
        boolean alreadyExists = false;
        for( int x = 0; x < this.groupMembers.size(); x++ ) {
            if( this.groupMembers.get(x).equals(user) ) {
                alreadyExists = true;
            }
        }
        if( !alreadyExists ) {
            this.groupMembers.add( user );
        }
    }
    public void removeGroupMember( User user ){
        this.groupMembers.remove(user);
    }

    public String getMooch() {
        return mooch;
    }

    public void setMooch(String mooch) {
        this.mooch = mooch;
    }
}
