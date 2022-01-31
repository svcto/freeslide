package br.app.freeslide.presentation.controller;

import br.app.freeslide.presentation.model.SongLyrics;
import br.app.freeslide.presentation.repository.SongLyricsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/song-lyrics")
public class SongLyricsController  extends AbstractController<SongLyrics, SongLyricsRepository> {
}
