package com.example.phuotogether.dto;
import android.net.Uri;

import java.io.Serializable;
public class Photo implements Serializable{
    private int id;
    private int albumID;
    private String time;
    private Uri uri;
    private String note;

    // Constructor
    public Photo(int id, int albumID, String time, Uri uri, String note) {
        this.id = id;
        this.albumID = albumID;
        this.time = time;
        this.uri = uri;
        this.note = note;
    }

    // Getter
    public int getId() {
        return id;
    }
    public int getAlbumID() {
        return albumID;
    }
    public String getTime() {
        return time;
    }
    public Uri getUri() {
        return uri;
    }
    public String getNote() {
        return note;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
