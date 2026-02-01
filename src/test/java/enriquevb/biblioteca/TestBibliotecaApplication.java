package enriquevb.biblioteca;

import org.springframework.boot.SpringApplication;

public class TestBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.from(BibliotecaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
