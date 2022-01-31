package br.app.freeslide.presentation.dto;

public class SlideDTO {
  private Integer idMedia;
  private Integer idStyle;
  private String title;
  private String text;
  private String footer;

  public Integer getIdMedia() {
    return idMedia;
  }

  public void setIdMedia( Integer idMedia ) {
    this.idMedia = idMedia;
  }

  public Integer getIdStyle() {
    return idStyle;
  }

  public void setIdStyle( Integer idStyle ) {
    this.idStyle = idStyle;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText( String text ) {
    this.text = text;
  }

  public String getFooter() {
    return footer;
  }

  public void setFooter( String footer ) {
    this.footer = footer;
  }
}
