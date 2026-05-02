package askar.controlworkjava27.repository;

import askar.controlworkjava27.model.File;
import askar.controlworkjava27.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Page<File> findByIsPublicTrue(Pageable pageable);

    Page<File> findByUser(User user, Pageable pageable);

    Optional<File> findByPrivateKey(String key);
}
