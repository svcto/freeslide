package br.app.freeslide.presentation.controller;

import br.app.freeslide.presentation.model.TextStyle;
import br.app.freeslide.presentation.repository.TextStyleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text-style")
public class TextStyleController extends AbstractController<TextStyle, TextStyleRepository> {
}
