package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    
    private final String name;
    private final List<Video> playlistVideos; 
    
    public VideoPlaylist(String name){
        this.name = name;
        playlistVideos = new ArrayList<>();
    }
    
    public void addVideo(Video video){
        playlistVideos.add(video);
    }
    
    public String getName(){
        return name;
    }
    
    public List<Video> getAllPlayListVideo(){
        return playlistVideos;
    }
    
    

}
