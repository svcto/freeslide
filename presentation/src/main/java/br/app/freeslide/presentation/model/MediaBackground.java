package br.app.freeslide.presentation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MediaBackground {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  private String url;

  private String type;

  public Integer getId() {
    return id;
  }

  public void setId( Integer id ) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl( String url ) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType( String type ) {
    this.type = type;
  }
}
