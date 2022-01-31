package br.app.freeslide.presentation.view;

import br.app.freeslide.presentation.model.ETransition;
import br.app.freeslide.presentation.model.TextStyle;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static java.lang.System.exit;


public class Slide extends Application {


  private static ETransition transicao = ETransition.FADE;

  private static String sourceMedia =  "";
  public static Integer duracaoTransicao = 1000;
  private static MediaPlayer mediaPlayer;

  static Stage ps;
  static MediaControl mediaControl;

  private void init(Stage primaryStage) {
    Group root = new Group();
    ps = primaryStage;
    primaryStage.setScene(new Scene(root));
    if (sourceMedia != null && !sourceMedia.equals( "" ) ) {
      try {
        mediaPlayer = new MediaPlayer(new Media(sourceMedia));
        mediaPlayer.setAutoPlay(true);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    mediaControl = new MediaControl( mediaPlayer );
    mediaControl.mediaView.setFitWidth(primaryStage.getWidth());
    mediaControl.mediaView.setFitHeight(primaryStage.getHeight());
    mediaControl.textContainer.setPrefHeight(primaryStage.getScene().getHeight());
    mediaControl.lbl.setLayoutX(0);
    mediaControl.lbl.setLayoutY(100);
    mediaControl.lbl.wrappingWidthProperty().bind(primaryStage.getScene().widthProperty().subtract(15));
    mediaControl.lbl.setTextAlignment(TextAlignment.CENTER);
    mediaControl.img.setPreserveRatio(false);
    mediaControl.img.setFitWidth(primaryStage.getScene().getWidth());
    mediaControl.img.setFitHeight(primaryStage.getScene().getHeight());
    primaryStage.getScene().setFill( Color.BLACK );
    Resource resource = new ClassPathResource("img.jpg");
    try {
      mediaControl.img.setImage( new Image( URLDecoder.decode(
              resource.getURL().toString(), StandardCharsets.UTF_8.name() )
              .replace( "file:/", "file:///" ).replaceAll( " ", "%20" )) );
    }
    catch ( IOException e ) {
      e.printStackTrace();
    }

    Thread t = new Thread(){
      @Override
      public void run(){

        try {
          String msg = "FULL_SCREEN;";

          Platform.runLater(()->{
            ps.hide();
            if (!mediaControl.fullScreen){
              mediaControl.newStage = new Stage();
              mediaControl.newStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
                @Override public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                  mediaControl.onFullScreen();
                }
              });
              final BorderPane borderPane = new BorderPane(){
                @Override protected void layoutChildren(){
                  if (mediaControl.mediaView != null && getBottom() != null) {
                    mediaControl.mediaView.setFitWidth(getWidth());
                    mediaControl.mediaView.setFitHeight(getHeight() - getBottom().prefHeight(-1));
                    mediaControl.textContainer.setPrefHeight(mediaControl.newStage.getScene().getHeight());
                    mediaControl.lbl.setLayoutX(0);
                    mediaControl.lbl.setLayoutY(100);
                    mediaControl.lbl.wrappingWidthProperty().bind(mediaControl.newStage.getScene().widthProperty().subtract(15));
                    mediaControl.lbl.setTextAlignment(TextAlignment.CENTER);
                    mediaControl.img.setPreserveRatio(false);
                    mediaControl.img.setFitWidth(mediaControl.newStage.getScene().getWidth());
                    mediaControl.img.setFitHeight(mediaControl.newStage.getScene().getHeight());

                    mediaControl.setStyle( "-fx-background-color: blue;" );
                  }
                  super.layoutChildren();
                  try{
                    if (mediaControl.mediaView != null) {
                      mediaControl.mediaView.setTranslateX((((Pane)getCenter()).getWidth() - mediaControl.mediaView.prefWidth(-1)) / 2);
                      mediaControl.mediaView.setTranslateY((((Pane)getCenter()).getHeight() - mediaControl.mediaView.prefHeight(-1)) / 2);
                    }
                  }catch(Exception ignored){
                  }
                }
              };

              mediaControl.setCenter(null);
              mediaControl.setBottom(null);
              borderPane.setCenter(mediaControl.mvPane);
              borderPane.setBottom(mediaControl.mediaBar);

              Scene newScene = new Scene(borderPane);
              mediaControl.newStage.setScene(newScene);
              mediaControl.newStage.setX(1920);
              mediaControl.newStage.setY(500);

              mediaControl.newStage.setFullScreen(true);
              mediaControl.fullScreen = true;
              mediaControl.newStage.show();
              mediaControl.newStage.getScene().setFill( Color.BLACK );
              mediaControl.setBackground( new Background(new BackgroundFill(Color.RED, new CornerRadii(0), Insets.EMPTY)) );

            }
            else{
              mediaControl.fullScreen = false;
              mediaControl.newStage.setFullScreen(false);
            }
          });
        } catch (Exception e) {
          System.out.println("IOException: " + e);
        }
      }
    };
    mediaControl.setMinSize(480,280);
    mediaControl.setPrefSize(480,280);
    mediaControl.setMaxSize(480,280);
    root.getChildren().add(mediaControl);
    t.start();

  }

  public static void fade() {
    Platform.runLater( ()->{
      FadeTransition ft = new FadeTransition(Duration.millis(duracaoTransicao), mediaControl.textContainer);
      ft.setFromValue(0.0);
      ft.setToValue(1.0);
      ft.play();
    } );
  }

  public static void hideText() {
    switch (transicao){
      case FADE:
        Platform.runLater( ()->{
          FadeTransition ft = new FadeTransition(Duration.millis(duracaoTransicao), mediaControl.textContainer);
          ft.setFromValue(1.0);
          ft.setToValue(0.0);
          ft.play();
        });
        break;
      case DISABLED:
        Platform.runLater( ()->{
          FadeTransition ft = new FadeTransition(Duration.millis(1), mediaControl.textContainer);
          ft.setFromValue(0.0);
          ft.setToValue(0.0);
          ft.play();
        });
    }
  }

  public static void setSlide( String header, String text, String footer, TextStyle style, String background) {
    if (background != null) setBackground( background );
    if (style != null) {
      if (style.getPropertiesHeader() != null) {
        mediaControl.lblHeader.setStyle( style.getPropertiesHeader() );
      }
      if (style.getPropertiesText() != null) {
        mediaControl.lbl.setStyle( style.getPropertiesText() );
      }
      if (style.getPropertiesFooter() != null) {
        mediaControl.lblFooter.setStyle( style.getPropertiesFooter() );
      }
    }
    switch (transicao) {
      case FADE:
        Platform.runLater( ()->{
          FadeTransition ft = new FadeTransition(Duration.millis(duracaoTransicao), mediaControl.textContainer);
          ft.setFromValue(0.0);
          mediaControl.lblHeader.setText( header );
          mediaControl.lbl.setText( text );
          mediaControl.lblFooter.setText( footer );
          ft.setToValue(1.0);
          ft.play();
        });
        break;
      case DISABLED:
        Platform.runLater( ()->{
          FadeTransition ft = new FadeTransition(Duration.millis(1), mediaControl.textContainer);
          ft.setFromValue(1.0);
          ft.setToValue(1.0);
          mediaControl.lbl.setText( text );
          ft.play();
        });
    }
  }

  public static void setTransicao(ETransition transicao) {
    Slide.transicao = transicao;
  }

  public static void setBackground(String source) {
    String sourceUrl = source.replaceAll(" ","%20");
    if (sourceMedia.equals( sourceUrl )) return;
    if (sourceMedia.equals( source )) return;
    if (source.contains( ".mp4" ))
    { sourceMedia = sourceUrl;
      try {
        mediaControl.mvPane.setStyle( "-fx-background: black;" );
        mediaControl.mediaView.setStyle( "-fx-background: black;" );
        if (mediaControl.img.getImage() == null) {
          Resource resource = new ClassPathResource("img.jpg");
          mediaControl.img.setImage( new Image( URLDecoder.decode(
                  resource.getURL().toString(), StandardCharsets.UTF_8.name() )
                  .replace( "file:/", "file:///" ).replaceAll( " ", "%20" )) );

        }
        Platform.runLater( ()->{
          FadeTransition ft = new FadeTransition(Duration.millis(duracaoTransicao), mediaControl.img);
          ft.setFromValue(1.0);
          ft.setToValue(0.0);
          ft.play();
          mediaPlayer = new MediaPlayer(new Media(sourceMedia));
          mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
          mediaPlayer.play();
          mediaControl.mediaView.setMediaPlayer(mediaPlayer );
          ft.onFinishedProperty().set( new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
              mediaControl.img.setImage(null);
            }
          } );
        });


      } catch(Exception ignored ){}

    } else if (source.contains( "-fx-background" )){
      sourceMedia = source;
      mediaControl.mvPane.setStyle( source );
      mediaPlayer.stop();
      mediaControl.img.setImage(null);
      mediaControl.mediaView.setMediaPlayer( null );
    } else {
      sourceMedia = sourceUrl;
      try{
        Platform.runLater( ()->{
          mediaControl.img.setImage(new Image(sourceMedia));
          FadeTransition ft = new FadeTransition(Duration.millis(duracaoTransicao), mediaControl.img);
          ft.setFromValue(0.0);
          ft.setToValue(1.0);
          ft.play();
          ft.onFinishedProperty().set( new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
              mediaPlayer.stop();
            }
          } );
        });

      }catch(Exception ignored ){}
    }
  }

  public void play() {
    Status status = mediaPlayer.getStatus();
    if (status == Status.UNKNOWN
            || status == Status.HALTED)
    {
      return;
    }

    if (status == Status.PAUSED
            || status == Status.STOPPED
            || status == Status.READY)
    {
      mediaPlayer.play();
    }
  }

  @Override public void stop() {
    mediaPlayer.stop();
  }

  public static class MediaControl extends BorderPane {
    private MediaPlayer mp;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private HBox mediaBar;
    private Pane mvPane;
    private Stage newStage;
    private boolean fullScreen = false;

    private Text lblHeader;
    private Text lbl;
    private Text lblFooter;
    VBox textContainer;
    ImageView img;


    @Override protected void layoutChildren() {
      if (mediaView != null && getBottom() != null) {
        mediaView.setFitWidth(getWidth());
        mediaView.setFitHeight(getHeight() - getBottom().prefHeight(-1));
      }
      super.layoutChildren();
      if (mediaView != null && getCenter() != null) {
        mediaView.setTranslateX((((Pane)getCenter()).getWidth() - mediaView.prefWidth(-1)) / 2);
        mediaView.setTranslateY((((Pane)getCenter()).getHeight() - mediaView.prefHeight(-1)) / 2);
      }
    }

    @Override protected double computeMinWidth(double height) {
      return mediaBar.prefWidth(-1);
    }

    @Override protected double computeMinHeight(double width) {
      return 200;
    }

    @Override protected double computePrefWidth(double height) {
      return Math.max(mp.getMedia().getWidth(), mediaBar.prefWidth(height));
    }

    @Override protected double computePrefHeight(double width) {
      return mp.getMedia().getHeight() + mediaBar.prefHeight(0);
    }

    @Override protected double computeMaxWidth(double height) { return Double.MAX_VALUE; }

    @Override protected double computeMaxHeight(double width) { return Double.MAX_VALUE; }

    public MediaControl(final MediaPlayer mp) {
      this.mp=mp;
      setStyle("-fx-background-color: black;");
      mediaView = new MediaView(mp);
      img = new ImageView();
      mvPane = new Pane();
      mvPane.getChildren().add(mediaView);
      mvPane.getChildren().add(img);
      mvPane.setStyle("-fx-background-color: black;");
      setCenter(mvPane);
      mediaBar = new HBox(0);
      mediaBar.setPadding(new Insets(0, 0, 0, 0));
      mediaBar.setAlignment(Pos.CENTER_LEFT);
      BorderPane.setAlignment(mediaBar, Pos.CENTER);
      if (sourceMedia != null && !sourceMedia.equals( "" )) img.setImage(new Image(sourceMedia));

      lblHeader = new Text();
      lblHeader.setText("-");
      lblHeader.setStyle("-fx-font: 50px Arial; -fx-font-weight: bold;-fx-fill: white;-fx-stroke: black;-fx-stroke-width: 2;");

      lbl = new Text();
      lbl.setText("");
      lbl.setStyle("-fx-font: 80px Arial; -fx-font-weight: bold; -fx-fill: white;-fx-stroke: black;-fx-stroke-width: 2;");

      lblFooter = new Text();
      lblFooter.setText("--");
      lblFooter.setStyle("-fx-font: 50px Arial; -fx-font-weight: bold;-fx-fill: white;-fx-stroke: black;-fx-stroke-width: 2;");
      //lbl.setStyle(" -fx-stroke-width: 201;");
      textContainer = new VBox();
      textContainer.getChildren().add(lblHeader);
      textContainer.getChildren().add(lbl);
      textContainer.getChildren().add(lblFooter);
      textContainer.setAlignment(Pos.CENTER);


      mvPane.getChildren().add(textContainer);

      final Button playButton  = ButtonBuilder.create()
              .minWidth(Control.USE_PREF_SIZE)
              .build();

      playButton.setOnAction( e -> {
        updateValues();
        Status status = mp.getStatus();
        if (status == Status.UNKNOWN
                || status == Status.HALTED)
        {
          return;
        }

        if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED)
        {

          if (atEndOfMedia) {
            mp.seek(mp.getStartTime());
            atEndOfMedia = false;

            updateValues();
          }
          mp.play();
        }
        else {
          mp.pause();
        }
      } );
      mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
        @Override
        public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
          updateValues();
        }
      });
      mp.setOnPlaying(new Runnable() {
        public void run() {


          if (stopRequested) {
            mp.pause();
            stopRequested = false;
          } else {
          }
        }
      });
      mp.setOnPaused(new Runnable() {
        public void run() {
        }
      });
      mp.setOnReady(new Runnable() {
        public void run() {
          duration = mp.getMedia().getDuration();
          updateValues();
        }
      });

      mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
      mp.setOnEndOfMedia(new Runnable() {
        public void run() {
          if (!repeat) {
            stopRequested = true;
            atEndOfMedia = true;
          }
        }
      });
      Label timeLabel = new Label("Time");
      timeLabel.setMinWidth(Control.USE_PREF_SIZE);

      timeSlider = SliderBuilder.create()
              .minWidth(30)
              .maxWidth(Double.MAX_VALUE)
              .build();
      HBox.setHgrow(timeSlider, Priority.ALWAYS);
      timeSlider.valueProperty().addListener(new InvalidationListener() {
        @Override
        public void invalidated(Observable ov) {
          if (timeSlider.isValueChanging()) {

            if(duration!=null) {
              mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
            }
            updateValues();

          }
        }
      });

      playTime = LabelBuilder.create()
              .minWidth(Control.USE_PREF_SIZE)
              .build();

      Button buttonFullScreen = ButtonBuilder.create()
              .text("Full Screen")
              .minWidth(Control.USE_PREF_SIZE)
              .build();

      buttonFullScreen.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          if (!fullScreen){
            newStage = new Stage();
            newStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
              @Override public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                onFullScreen();
              }
            });
            final BorderPane borderPane = new BorderPane(){
              @Override protected void layoutChildren(){
                try{
                  if (mediaView != null && getBottom() != null) {
                    mediaView.setFitWidth(getWidth());
                    mediaView.setFitHeight(getHeight() - getBottom().prefHeight(-1));
                  }
                  super.layoutChildren();

                  if (mediaView != null) {
                    mediaView.setTranslateX((((Pane)getCenter()).getWidth() - mediaView.prefWidth(-1)) / 2);
                    mediaView.setTranslateY((((Pane)getCenter()).getHeight() - mediaView.prefHeight(-1)) / 2);
                  }
                }catch(Exception ignored){
                }

              };
            };

            setCenter(null);
            setBottom(null);
            borderPane.setCenter(mvPane);

            Scene newScene = new Scene(borderPane);
            newStage.setScene(newScene);
            //Workaround for disposing stage when exit fullscreen
            newStage.setX(1950);
            newStage.setY(500);
            newStage.getScene().setFill( Color.BLACK );
            newStage.setFullScreen(true);
            fullScreen = true;
            newStage.show();

          }
          else{
            //toggle FullScreen
            fullScreen = false;
            newStage.setFullScreen(false);

          }
        }

      });
      //mediaBar.getChildren().add(buttonFullScreen);

      // Volume label
      Label volumeLabel = new Label("Vol");
      volumeLabel.setMinWidth(Control.USE_PREF_SIZE);
      //mediaBar.getChildren().add(volumeLabel);

      // Volume slider
      volumeSlider = SliderBuilder.create()
              .prefWidth(70)
              .minWidth(30)
              .maxWidth(Region.USE_PREF_SIZE)
              .build();
      volumeSlider.valueProperty().addListener(new InvalidationListener() {
        public void invalidated(Observable ov) {
        }
      });
      volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          if (volumeSlider.isValueChanging()) {
            mp.setVolume(volumeSlider.getValue() / 100.0);
          }
        }
      });
      //mediaBar.getChildren().add(volumeSlider);

      //setBottom(mediaBar);


      mp.setCycleCount(MediaPlayer.INDEFINITE);
      mediaView.setPreserveRatio(false);




    }

    protected void onFullScreen(){
      if (!newStage.isFullScreen()){

        fullScreen = false;
        setCenter(mvPane);
        setBottom(null);
        Platform.runLater(new Runnable() {
          @Override public void run() {
            newStage.close();
          }
        });

      }
    }

    protected void updateValues() {
      if (playTime != null && timeSlider != null && volumeSlider != null && duration != null) {
        Platform.runLater(new Runnable() {
          public void run() {
            Duration currentTime = mp.getCurrentTime();
            playTime.setText(formatTime(currentTime, duration));
            timeSlider.setDisable(duration.isUnknown());
            if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
              timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
            }
            if (!volumeSlider.isValueChanging()) {
              volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
            }
          }
        });
      }
    }


    private String formatTime(Duration elapsed, Duration duration) {
      int intElapsed = (int)Math.floor(elapsed.toSeconds());
      int elapsedHours = intElapsed / (60 * 60);
      if (elapsedHours > 0) {
        intElapsed -= elapsedHours * 60 * 60;
      }
      int elapsedMinutes = intElapsed / 60;
      int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

      if (duration.greaterThan(Duration.ZERO)) {
        int intDuration = (int)Math.floor(duration.toSeconds());
        int durationHours = intDuration / (60 * 60);
        if (durationHours > 0) {
          intDuration -= durationHours * 60 * 60;
        }
        int durationMinutes = intDuration / 60;
        int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

        if (durationHours > 0) {
          return String.format("%d:%02d:%02d/%d:%02d:%02d",
                  elapsedHours, elapsedMinutes, elapsedSeconds,
                  durationHours, durationMinutes, durationSeconds);
        } else {
          return String.format("%02d:%02d/%02d:%02d",
                  elapsedMinutes, elapsedSeconds,
                  durationMinutes, durationSeconds);
        }
      } else {
        if (elapsedHours > 0) {
          return String.format("%d:%02d:%02d",
                  elapsedHours, elapsedMinutes, elapsedSeconds);
        } else {
          return String.format("%02d:%02d",
                  elapsedMinutes, elapsedSeconds);
        }
      }
    }
  }

  @Override public void start(Stage primaryStage) throws Exception {
    // sourceMedia = "file:///C:/Program Files/Quelea/vid/abstract blue.mp4";
    Resource resource = new ClassPathResource("black.mp4");
    sourceMedia = URLDecoder.decode( resource.getURL().toString(), StandardCharsets.UTF_8.name() ).replaceAll( "file:/", "file:///" );
    System.out.println(sourceMedia);
    sourceMedia = sourceMedia.replaceAll(" ","%20");
    init(primaryStage);
    primaryStage.show();
    play();
  }

  public static void main(String[] args) {
    launch(args);
    System.out.println("Encerrando a aplicação...");
    exit(0);
  }
}
