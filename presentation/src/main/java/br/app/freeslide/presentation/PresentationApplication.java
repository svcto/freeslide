package br.app.freeslide.presentation;

import br.app.freeslide.presentation.view.Slide;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ServiceLoader;

@SpringBootApplication
public class PresentationApplication {

	public static void main(String[] args) {
		Thread t = new Thread(){
			public void run(){
				Slide.main( args );
			}
		};
		t.start();
		SpringApplication.run(PresentationApplication.class, args);
	}

}
