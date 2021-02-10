package com.galvanize.playlist.model;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    @NotNull
    private String playlistName;

    @ElementCollection
    private List<String> songList;

    public Playlist() {
        songList = new ArrayList<String>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(playlistName, playlist.playlistName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistName);
    }

    public void addSong(String nameOfTheSong) {
        songList.add(nameOfTheSong);
    }

    public List<String> removeSong(String nameOfTheSong) {
        songList.remove(nameOfTheSong);
        return  songList;
    }
}
