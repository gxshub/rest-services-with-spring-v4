package csci318.demo.repository;

import csci318.demo.model.event.BookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookEventRepository extends JpaRepository<BookEvent, Long> {
}
