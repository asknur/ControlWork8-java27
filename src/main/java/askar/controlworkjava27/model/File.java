package askar.controlworkjava27.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "files", schema = "public")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stored_name")
    private String storedName;

    private Long size;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "download_count")
    private Integer downloadCount = 0;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
