package br.app.freeslide.presentation.repository;

import br.app.freeslide.presentation.model.SongLyrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongLyricsRepository extends JpaRepository<SongLyrics, Integer> {
}
