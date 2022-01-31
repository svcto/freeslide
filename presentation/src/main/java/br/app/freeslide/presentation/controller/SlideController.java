package br.app.freeslide.presentation.controller;

import br.app.freeslide.presentation.dto.SlideDTO;
import br.app.freeslide.presentation.model.ETransition;
import br.app.freeslide.presentation.model.MediaBackground;
import br.app.freeslide.presentation.model.TextStyle;
import br.app.freeslide.presentation.repository.MediaBackgroundRepository;
import br.app.freeslide.presentation.repository.TextStyleRepository;
import br.app.freeslide.presentation.view.Slide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping( "/slide" )
public class SlideController {

  @Autowired
  MediaBackgroundRepository mediaBackgroundRepository;
  @Autowired
  TextStyleRepository textStyleRepository;

  @GetMapping( "/fade" )
  public void fade() {
    Slide.fade();
  }

  @GetMapping( "/hide" )
  public void hide() {
    Slide.hideText();
  }

  @PostMapping("/show")
  public void show( @RequestBody SlideDTO slideDTO) {
    try {
      String background = null;
      TextStyle style = null;
      if (slideDTO.getIdMedia() != null) {
        Optional<MediaBackground> mediaBackground = mediaBackgroundRepository.findById( slideDTO.getIdMedia() );
        if (mediaBackground.isPresent()) {
          background = mediaBackground.get().getUrl();
        }
      }
      if (slideDTO.getIdStyle() != null) {
        Optional<TextStyle> optional = textStyleRepository.findById( slideDTO.getIdStyle() );
        if (optional.isPresent()) style = optional.get();
      }
      Slide.setSlide(slideDTO.getTitle(), slideDTO.getText(), slideDTO.getFooter(), style, background);

    } catch ( Exception ignored ) {}
  }

  @PostMapping("/transition")
  public void setTransition( @RequestBody ETransition transition ) {
    try {
      Slide.setTransicao( transition );
    } catch ( Exception ignored ) {}
  }
}
