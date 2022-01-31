package br.app.freeslide.presentation.repository;

import br.app.freeslide.presentation.model.TextStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextStyleRepository extends JpaRepository<TextStyle, Integer> {
}
