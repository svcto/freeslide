package br.app.freeslide.presentation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TextStyle {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  private String propertiesText;

  private String propertiesHeader;

  private String propertiesFooter;

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

  public String getPropertiesText() {
    return propertiesText;
  }

  public void setPropertiesText( String propertiesText ) {
    this.propertiesText = propertiesText;
  }

  public String getPropertiesHeader() {
    return propertiesHeader;
  }

  public void setPropertiesHeader( String propertiesHeader ) {
    this.propertiesHeader = propertiesHeader;
  }

  public String getPropertiesFooter() {
    return propertiesFooter;
  }

  public void setPropertiesFooter( String propertiesFooter ) {
    this.propertiesFooter = propertiesFooter;
  }
}
