package csci318.demo;

import csci318.demo.model.Book;
import csci318.demo.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookSpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(BookSpringApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(BookRepository bookRepository) throws Exception {
		return args -> {
			Book entry = new Book();
			entry.setIsbn("0-684-84328-5");
			entry.setTitle("Introduction to Software Engineering");
			entry.addAvailableLibrary(2500L);
			entry.addAvailableLibrary(2522L);
			bookRepository.save(entry);
			System.out.println(bookRepository.findById("0-684-84328-5").orElseThrow());

			Book entry1 = new Book();
			entry1.setIsbn("93-86954-21-4");
			entry1.setTitle("Domain Drive Design");
			entry1.addAvailableLibrary(2522L);
			bookRepository.save(entry1);
			System.out.println(bookRepository.findById("93-86954-21-4").orElseThrow());
		};
	}

}
