package br.app.freeslide.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@MappedSuperclass
public class AbstractController<T, U extends JpaRepository<T, Integer>> {
  private static final Logger LOG = Logger.getLogger( AbstractController.class.getName() );

  @Autowired
  private U repository;
  @Autowired
  private ApplicationContext context;

  @GetMapping()
  public List<T> getAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public T getById(@PathVariable(value = "id") Integer id) {
    Optional<T> optional = repository.findById( id );
    return optional.orElse( null );
  }

  @PostMapping()
  public T create(@Valid @RequestBody T t) {
    return repository.save(t);
  }

  @PutMapping()
  public T save(@RequestBody T fila) {
    return repository.save(fila);
  }


}
