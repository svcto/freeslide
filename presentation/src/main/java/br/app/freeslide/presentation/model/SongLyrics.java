package br.app.freeslide.presentation.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class SongLyrics {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String title;

  private String author;

  private String singer;

  @Lob
  @Type( type = "text" )
  private String lyrics;

  private String urlMusic;

  private Boolean useTheme;

  private Boolean showTitle;

  public Integer getId() {
    return id;
  }

  public void setId( Integer id ) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor( String author ) {
    this.author = author;
  }

  public String getSinger() {
    return singer;
  }

  public void setSinger( String singer ) {
    this.singer = singer;
  }

  public String getLyrics() {
    return lyrics;
  }

  public void setLyrics( String lyrics ) {
    this.lyrics = lyrics;
  }

  public String getUrlMusic() {
    return urlMusic;
  }

  public void setUrlMusic( String urlMusic ) {
    this.urlMusic = urlMusic;
  }

  public Boolean getUseTheme() {
    return useTheme;
  }

  public void setUseTheme( Boolean useTheme ) {
    this.useTheme = useTheme;
  }

  public Boolean getShowTitle() {
    return showTitle;
  }

  public void setShowTitle( Boolean showTitle ) {
    this.showTitle = showTitle;
  }
}
