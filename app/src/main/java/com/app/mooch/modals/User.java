package com.app.mooch.modals;

import android.graphics.Bitmap;

import android.net.Uri;

public class User {

    private boolean registered;

    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    private Uri photoURI;

    private boolean selected;

    public User(){

    }

    public User( String id ){
        this.id = id;
    }

    public User( String id, String username){
        this.id = id;
        this.username = username;
    }

    public User( String username, String phoneNumber, Uri profilePic){
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.photoURI = profilePic;
    }

    public User(String id, String username, String email, String phoneNumber, String photoUrl ){
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
    }

    public User(String id ,String username, String email, String photoUrl , boolean registered) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.photoUrl = photoUrl;
        this.registered = registered;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setPhotoURI( Uri photoUri ){
        this.photoURI = photoUri;
    }
    public Uri getPhotoUri(){
        return this.photoURI;
    }


    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSelected( boolean selected ) {
        this.selected = selected;
    }
    public boolean isSelected(){
        return this.selected;
    }

}
