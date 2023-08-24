package csci318.demo;

import csci318.demo.model.Library;
import csci318.demo.repository.LibraryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibrarySpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(LibrarySpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadDatabase(LibraryRepository libraryRepository) throws Exception {
		return args -> {
			Library entry = new Library();
			entry.setId(2500L);
			entry.setName("Wollongong City Library");
			libraryRepository.save(entry);
			System.out.println(libraryRepository.findById(2500L).orElseThrow());
			Library entry1 = new Library();
			entry1.setId(2522L);
			entry1.setName("UOW Library");
			libraryRepository.save(entry1);
			System.out.println(libraryRepository.findById(2522L).orElseThrow());
		};
	}
}


