package com.google;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentPlayingVideo;
  private String videoPlayingStatus;
  private final Map<String, VideoPlaylist> videoPlaylist;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videoPlaylist = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videos = videoLibrary.getVideos();
    videos.sort(Comparator.comparing(Video::getTitle));
    for(var video: videos){
        String tags = video.getTags().toString();
       System.out.println(video.getTitle() + " ("+ video.getVideoId()+") " + tags.replaceAll(",", "")); 
    }
  }

  public void playVideo(String videoId) {
     if(videoLibrary.getVideo(videoId)==null){
         System.out.println("Cannot play video: Video does not exist");
         return;
     }
    if(this.currentPlayingVideo!=null){
        System.out.println("Stopping video: "+ this.currentPlayingVideo.getTitle());
    }
     this.currentPlayingVideo = videoLibrary.getVideo(videoId);
     this.videoPlayingStatus = "playing";
    System.out.println("Playing video: "+ videoLibrary.getVideo(videoId).getTitle());
  }

  public void stopVideo() {
      if(this.currentPlayingVideo==null){
         System.out.println("Cannot stop video: No video is currently playing");
         return;
      }
      System.out.println("Stopping video: "+ this.currentPlayingVideo.getTitle());
      this.currentPlayingVideo = null;
      this.videoPlayingStatus = "stopped";
   
  }

  public void playRandomVideo() {
      int randomVideoPosition =(int) Math.floor(Math.random() * videoLibrary.getVideos().size());
      Video randomSelecetedVideo = videoLibrary.getVideos().get(randomVideoPosition);
      playVideo(randomSelecetedVideo.getVideoId());
  }

  public void pauseVideo() {
    if(this.currentPlayingVideo==null){
        System.out.println("Cannot pause video: No video is currently playing");
        return;
    }
    if(this.currentPlayingVideo!=null && this.videoPlayingStatus.equalsIgnoreCase("paused")){
        System.out.println("Video already paused: "+ this.currentPlayingVideo.getTitle());
        return;
    }
    this.videoPlayingStatus= "paused";
    System.out.println("Pausing video: "+ this.currentPlayingVideo.getTitle());
  }

  public void continueVideo() {
     if(this.currentPlayingVideo==null){
        System.out.println("Cannot continue video: No video is currently playing");
        return;
    }
      if(!this.videoPlayingStatus.equalsIgnoreCase("paused")){
        System.out.println("Cannot continue video: Video is not paused");
        return;
    }
      this.videoPlayingStatus="playing";
      System.out.println("Continuing video: "+ this.currentPlayingVideo.getTitle());
      
     
  }

  public void showPlaying() {
        if(this.currentPlayingVideo==null){
        System.out.println("No video is currently playing");
        return;
    }
        String tags = this.currentPlayingVideo.getTags().toString();
    if(this.videoPlayingStatus.equalsIgnoreCase("paused")){
        
        System.out.println("Currently playing: "+this.currentPlayingVideo.getTitle() + " ("+ this.currentPlayingVideo.getVideoId()+") " + tags.replaceAll(",", "") + " - PAUSED");
        return;
    }
     System.out.println("Currently playing: "+this.currentPlayingVideo.getTitle() + " ("+ this.currentPlayingVideo.getVideoId()+") " + tags.replaceAll(",", ""));
  }

  public void createPlaylist(String playlistName) {
      if(this.videoPlaylist.containsKey(playlistName)){
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
        return;
      }
    VideoPlaylist newPlaylist = new VideoPlaylist(playlistName);
    this.videoPlaylist.put(playlistName, newPlaylist);
    System.out.println("Successfully created new playlist: "+ playlistName);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
      Map<String, Video> videos = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
      VideoPlaylist playlist = this.videoPlaylist.get(playlistName);
    for(var video: videoLibrary.getVideos()){
        videos.put(video.getVideoId(), video);
    }
    
    if(!this.videoPlaylist.containsKey(playlistName)){
        System.out.println("Cannot add video to "+ playlistName +": Playlist does not exist ");
        return;
      }else if(!videos.containsKey(videoId)){
         System.out.println("Cannot add video to "+ playlistName +": Video does not exist "); 
         return;
      }else if(playlist.getAllPlayListVideo().contains(videos.get(videoId))){
         System.out.println("Cannot add video to "+ playlistName +": Video already added ");  
      }else{
          
          playlist.addVideo(videos.get(videoId));
          System.out.println("Added video to "+ playlistName +": "+ videos.get(videoId).getTitle());  
      }

  }

  public void showAllPlaylists() {
    if(this.videoPlaylist.isEmpty()){
         System.out.println("No playlists exist yet");
         return;
    }
    System.out.println("Showing all playlists:");
    for(var playlistKey: this.videoPlaylist.keySet()){
       System.out.println(playlistKey);
    }
  }

  public void showPlaylist(String playlistName) {
      var selectedPlaylist = this.videoPlaylist.get(playlistName);
       if(selectedPlaylist==null){
        System.out.println("Cannot show playlist "+ playlistName +": Playlist does not exist ");
        return;
      }
       if(selectedPlaylist.getAllPlayListVideo().isEmpty()){
           System.out.println("Showing playlist: "+playlistName);
         System.out.println("No videos here yet");  
       }else{
           System.out.println("Showing playlist: "+playlistName);
           selectedPlaylist.getAllPlayListVideo();
          for(var video: selectedPlaylist.getAllPlayListVideo()){
        String tags = video.getTags().toString();
       System.out.println(video.getTitle() + " ("+ video.getVideoId()+") " + tags.replaceAll(",", "")); 
    } 
       }
   
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
      var selectedPlaylist = this.videoPlaylist.get(playlistName);
      Map<String, Video> videos = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
      VideoPlaylist playlist = this.videoPlaylist.get(playlistName);
        for(var video: videoLibrary.getVideos()){
        videos.put(video.getVideoId(), video);
    }
       if(selectedPlaylist==null){
        System.out.println("Cannot remove video from "+ playlistName +": Playlist does not exist ");
        return;
      }else if(!videos.containsKey(videoId)){
         System.out.println("Cannot remove video from "+ playlistName +": Video does not exist "); 
         return;
       }else if(!selectedPlaylist.getAllPlayListVideo().contains(videos.get(videoId))){
          System.out.println("Cannot remove video from "+ playlistName +": Video is not in playlist"); 
         return; 
       }else{
           Video removedVideo = videos.get(videoId);
           selectedPlaylist.getAllPlayListVideo().remove(removedVideo);
           System.out.println("Removed video from "+ playlistName +": "+ removedVideo.getTitle()); 
         return;
       }
  
  }

  public void clearPlaylist(String playlistName) {
     var selectedPlaylist = this.videoPlaylist.get(playlistName);
     if(selectedPlaylist==null){
        System.out.println("Cannot clear playlist "+ playlistName +": Playlist does not exist ");
        return;
      }
     selectedPlaylist.getAllPlayListVideo().removeAll(selectedPlaylist.getAllPlayListVideo());
     System.out.println("Successfully removed all videos from "+ playlistName );
  }

  public void deletePlaylist(String playlistName) {
      var selectedPlaylist = this.videoPlaylist.get(playlistName);
      if(!this.videoPlaylist.containsKey(playlistName)){
         System.out.println("Cannot delete playlist "+ playlistName +": Playlist does not exist ");
        return; 
      }
      this.videoPlaylist.remove(playlistName);
      System.out.println("Deleted playlist: "+ playlistName );
  }

  public void searchVideos(String searchTerm) {
      Map<Integer, Video> searchedResults = new HashMap<>();
      int videoPosition = 0;
      List<Video> videos = this.videoLibrary.getVideos();
      videos.sort(Comparator.comparing(Video::getTitle));
      for(var video: videos){
         if(video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
             videoPosition++;
             searchedResults.put(videoPosition, video);
         } 
      }
    if(searchedResults.isEmpty()){
       System.out.println("No search results for "+searchTerm);
       return;
    }
        System.out.println("Here are the results for "+searchTerm+":");
        for(var videoKey: searchedResults.keySet()){
            String tags = searchedResults.get(videoKey).getTags().toString();
            var video = searchedResults.get(videoKey);
            System.out.println(videoKey+") "+video.getTitle() + " ("+ video.getVideoId()+") " + tags.replaceAll(",", "")); 
        }
       System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
       System.out.println("If your answer is not a valid number, we will assume it's a no.");
    
    boolean isRunning=true;
    Scanner inputResponse = new Scanner(System.in);
    while(true){
      String input;
      if (!inputResponse.hasNextInt()) {
        input = inputResponse.nextLine();
        return;
      }
      input = inputResponse.nextLine();
          if(!searchedResults.containsKey(Integer.parseInt(input))){
         
          return;
      }else{
        this.playVideo(searchedResults.get(Integer.parseInt(input)).getVideoId());
      return;
      }
    }
  }

  public void searchVideosWithTag(String videoTag) {
      Map<Integer, Video> searchedResults = new HashMap<>();
      int videoPosition = 0;
      
      List<Video> videos = this.videoLibrary.getVideos();
      videos.sort(Comparator.comparing(Video::getTitle));
      for(var video: videos){
          String tags = video.getTags().toString().toLowerCase();
         if(tags.contains(videoTag.toLowerCase())){
             videoPosition++;
             searchedResults.put(videoPosition, video);
         } 
      }
       if(searchedResults.isEmpty()){
       System.out.println("No search results for "+videoTag);
       return;
    }
        System.out.println("Here are the results for "+videoTag+":");
        for(var videoKey: searchedResults.keySet()){
            String tags = searchedResults.get(videoKey).getTags().toString();
            var video = searchedResults.get(videoKey);
            System.out.println(videoKey+") "+video.getTitle() + " ("+ video.getVideoId()+") " + tags.replaceAll(",", "")); 
        }
       System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
       System.out.println("If your answer is not a valid number, we will assume it's a no.");
    
    boolean isRunning=true;
    Scanner inputResponse = new Scanner(System.in);
    while(true){
      String input;
      if (!inputResponse.hasNextInt()) {
        input = inputResponse.nextLine();
        return;
      }
      input = inputResponse.nextLine();
          if(!searchedResults.containsKey(Integer.parseInt(input))){
         
          return;
      }else{
        this.playVideo(searchedResults.get(Integer.parseInt(input)).getVideoId());
      return;
      }
    }
      
    
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}