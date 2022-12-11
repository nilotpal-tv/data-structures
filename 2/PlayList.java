import java.util.Scanner;

class SongNode {
  String name;
  SongNode prev;
  SongNode next;
  String[] playListNames;

  SongNode(String name) {
    this.name = name;
    this.prev = null;
    this.next = null;
  }
}

class PlaylistNode {
  String name;
  int totalSongs;
  Boolean isLooped;
  Boolean isPlaying;
  SongNode currentPlaying;
  SongNode songListHead;
  SongNode songListTail;
  PlaylistNode next;
  PlaylistNode prev;

  PlaylistNode(String name) {
    this.name = name;
    this.totalSongs = 0;
    this.isLooped = false;
    this.isPlaying = false;
    this.songListHead = null;
    this.songListTail = null;
    this.currentPlaying = null;
    this.next = null;
    this.prev = null;
  }

  private SongNode findSong(String songName) {
    SongNode currentSong = this.songListHead;

    while (currentSong != null) {
      if (currentSong.name.toLowerCase().equals(songName.toLowerCase()))
        return currentSong;
      currentSong = currentSong.next;
    }

    return null;
  }

  PlaylistNode addSongToPlaylist(String songName) {
    SongNode existsSong = this.findSong(songName);

    if (existsSong != null) {
      System.out.println(songName + " already exists in the playlist.");
      return this;
    }

    SongNode newSong = new SongNode(songName);
    if (this.songListHead == null && this.songListTail == null) {
      this.songListHead = newSong;
      this.songListTail = newSong;
    } else {
      this.songListTail.next = newSong;
      newSong.prev = this.songListTail;
      this.songListTail = newSong;
    }

    this.totalSongs++;
    System.out.println(songName + " has been added to your playlist.");
    return this;
  }

  PlaylistNode removeSong(String songName) {
    if (this.songListHead.name.toLowerCase().equals(songName.toLowerCase())) {
      System.out.println("\n" + songName + " has been deleted from playlist " + this.name);
      this.songListHead = this.songListHead.next;
      this.totalSongs--;
    } else {
      SongNode song = this.findSong(songName);

      if (song == null)
        System.out.println(songName + " doesn't exist in playlist " + this.name);
      else {
        System.out.println(songName + " has been deleted from playlist " + this.name);
        this.totalSongs--;
        song.prev.next = song.next;
      }
    }

    return this;
  }

  PlaylistNode setLooped() {
    this.isLooped = !this.isLooped;
    return this;
  }

  PlaylistNode playSong(String song) {
    SongNode existsSong = this.findSong(song);
    if (existsSong == null) {
      System.out.println(song + " doesn't exist in playlist " + this.name);
      return this;
    }

    this.isPlaying = true;
    this.currentPlaying = existsSong;
    System.out.println("Now playing \"" + song + "\" from playlist " + this.name);
    return this;
  }

  PlaylistNode pause() {
    if (!this.isPlaying)
      System.out.println("You're not playing anything currently.");
    else {
      System.out.println("Paused " + this.currentPlaying.name + " from playlist " + this.name);
      this.isPlaying = false;
    }
    return this;
  }

  PlaylistNode playNext() {
    this.isPlaying = true;

    if (this.currentPlaying == null && this.isLooped)
      this.currentPlaying = this.songListHead;
    else if (this.currentPlaying == null && !this.isLooped) {
      System.out
          .println("To play next song either set the playlist in loop or select a song and play and then hit next.");
      this.isPlaying = false;
      return this;
    } else {
      if (this.currentPlaying.next == null && this.isLooped)
        this.currentPlaying = this.songListHead;
      else if (this.currentPlaying.next == null && !this.isLooped) {
        System.out.println("Oops! No more songs to play.");
        this.isPlaying = false;
        this.currentPlaying = null;
        return this;
      } else
        this.currentPlaying = this.currentPlaying.next;
    }

    System.out.println("Now playing " + this.currentPlaying.name + " from playlist " + this.name);
    return this;
  }

  PlaylistNode playPrevious() {
    this.isPlaying = true;

    if (this.currentPlaying == null && this.isLooped)
      this.currentPlaying = this.songListHead;
    else if (this.currentPlaying == null && !this.isLooped) {
      System.out
          .println("To play next song either set the playlist in loop or select a song and play and then hit next.");
      this.isPlaying = false;
      return this;
    } else {
      if (this.currentPlaying.prev == null && this.isLooped)
        this.currentPlaying = this.songListTail;
      else if (this.currentPlaying.prev == null && !this.isLooped) {
        System.out.println("Oops! You have reached the end.");
        this.isPlaying = false;
        this.currentPlaying = null;
        return this;
      } else
        this.currentPlaying = this.currentPlaying.prev;
    }

    System.out.println("Now playing " + this.currentPlaying.name + " from playlist " + this.name);
    return this;
  }

  PlaylistNode seekSongs(int seekNum) {
    seekNum = Math.abs(seekNum);

    if (this.totalSongs == 0) {
      System.out.println("You don't have any songs added to this playlist.");
    } else if (seekNum > this.totalSongs && !this.isLooped) {
      System.out.println("Seek number can't be greater than total songs in your playlist.");
    } else {
      int currentIdx = 1;
      SongNode currentSong = this.songListHead;

      int songsToSeekMore = seekNum - this.totalSongs;
      Boolean shouldSeekMore = songsToSeekMore <= 0 ? false : true;
      this.isPlaying = true;

      if (!shouldSeekMore) {
        while (currentIdx != seekNum) {
          currentIdx++;
          currentSong = currentSong.next;
        }
        this.currentPlaying = currentSong;
      } else {

      }
    }

    return this;
  }

  void printPlaylist() {
    if (this.totalSongs == 0) {
      System.out.println("No songs in the playlist yet. Try adding one.");
      return;
    }
    System.out.print("Displaying songs in \"" + this.name + "\" playlist : ");
    SongNode temp = this.songListHead;
    while (temp != null) {
      System.out.print(temp.name + ", ");
      temp = temp.next;
    }
  }
}

class StoredPlayLists {
  int totalPlayLists;
  PlaylistNode playListHead;
  PlaylistNode playListTail;
  PlaylistNode selectedPlayList;

  StoredPlayLists() {
    this.totalPlayLists = 0;
    this.playListHead = null;
    this.playListTail = null;
    this.selectedPlayList = null;
  }

  private PlaylistNode findPlayList(String playListName) {
    PlaylistNode currentPlayList = this.playListHead;

    while (currentPlayList != null) {
      if (currentPlayList.name.toLowerCase().equals(playListName.toLowerCase()))
        return currentPlayList;
      currentPlayList = currentPlayList.next;
    }

    return null;
  }

  StoredPlayLists addPlayList(String playListName) {
    PlaylistNode newPlayList = new PlaylistNode(playListName);
    this.selectedPlayList = newPlayList;
    this.totalPlayLists++;

    if (this.playListHead == null) {
      this.playListHead = newPlayList;
      this.playListTail = newPlayList;
    } else {
      this.playListTail.next = newPlayList;
      newPlayList.prev = this.playListTail;
      this.playListTail = newPlayList;
    }

    System.out.println("\n" + playListName + " has been added to your library.");
    return this;
  }

  StoredPlayLists removePlayList(String playListName) {
    if (this.playListHead == null) {
      System.out.println("You don't have any playlists.");
      return this;
    }

    if (this.playListHead.name.toLowerCase().equals(playListName.toLowerCase())) {
      System.out.println("\n" + playListName + " has been deleted from your library.");
      this.playListHead = this.playListHead.next;
      this.totalPlayLists--;
    } else {
      PlaylistNode exist = this.findPlayList(playListName);

      if (exist == null)
        System.out.println("\n" + playListName + " doesn't exist in your library.");
      else {
        exist.prev.next = exist.next;
        this.totalPlayLists--;
        System.out.println("\n" + playListName + " has been deleted from your library.");
      }
    }

    return this;
  }

  StoredPlayLists setCurrentPlayList(String playListName) {
    if (this.playListHead == null)
      System.out.println("You don't  have any playlist. First create one and then it will be automatically selected.");
    else {
      PlaylistNode playlist = this.findPlayList(playListName);
      if (playlist == null)
        System.out.println("\n No playlist exists with this name.");
      else
        this.selectedPlayList = playlist;
    }
    return this;
  }

  void printPlaylist() {
    if (this.totalPlayLists == 0) {
      System.out.println("You don't have any playlists yet.");
      return;
    }

    System.out.print("Displaying your playlists : ");
    PlaylistNode temp = this.playListHead;
    while (temp != null) {
      System.out.print(temp.name + ", ");
      temp = temp.next;
    }
  }

  void printSongsOfPlaylist(String playListName) {
    PlaylistNode playlist = this.findPlayList(playListName);

    if (playlist == null)
      System.out.println("No playlist exists with this name.");
    else
      playlist.printPlaylist();
  }
}

public class PlayList {
  public static void main(String[] args) {
    int option;
    Scanner sc = new Scanner(System.in);
    StoredPlayLists userPlayLists = new StoredPlayLists();

    while (true) {
      System.out.println("\n\t******************************");
      System.out.println("\t1. Create a new playlist.");
      System.out.println("\t2. Delete a playlist.");
      System.out.println("\t3. Select a playlist");
      System.out.println("\t4. Show my playlists.");
      System.out.println("\t5. Display songs of a playlist.");
      System.out.println("\t6. Add song to a playlist.");
      System.out.println("\t5. Delete song from a playlist.");
      System.out.println("\t******************************");

      System.out.print("\nEnter your choice : ");
      option = sc.nextInt();

      if (option == 1) {
        System.out.print("\nWhat do you want to call your playlist : ");
        sc.skip("[\r\n]+");
        String name = sc.nextLine();
        userPlayLists = userPlayLists.addPlayList(name);
      } else if (option == 2) {
        System.out.print("\nName the playlist you want to delete : ");
        sc.skip("[\r\n]+");
        String name = sc.nextLine();
        userPlayLists = userPlayLists.removePlayList(name);
      } else if (option == 3) {
        System.out.print("\nName of the playlist : ");
        sc.skip("[\r\n]+");
        String name = sc.nextLine();
        userPlayLists = userPlayLists.setCurrentPlayList(name);
      } else if (option == 4)
        userPlayLists.printPlaylist();
      else if (option == 5) {
        System.out.print("\nName of the playlist : ");
        sc.skip("[\r\n]+");
        String name = sc.nextLine();
        userPlayLists.printSongsOfPlaylist(name);
      } else
        break;
    }
    sc.close();
  }
}