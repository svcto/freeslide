package br.app.freeslide.presentation.repository;

import br.app.freeslide.presentation.model.MediaBackground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaBackgroundRepository extends JpaRepository<MediaBackground, Integer> {
}
