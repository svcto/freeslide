package br.app.freeslide.presentation.controller;

import br.app.freeslide.presentation.model.MediaBackground;
import br.app.freeslide.presentation.repository.MediaBackgroundRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media-background")
public class MediaBackgroundController extends AbstractController<MediaBackground, MediaBackgroundRepository> {

}
