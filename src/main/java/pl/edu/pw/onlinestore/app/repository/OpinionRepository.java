package pl.edu.pw.onlinestore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.onlinestore.app.domain.Opinion;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
}
