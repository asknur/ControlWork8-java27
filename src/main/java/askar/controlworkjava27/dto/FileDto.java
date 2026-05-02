package askar.controlworkjava27.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
    private Long id;
    private String name;
    private Long size;
    private Boolean isPublic;
    private String privateKey;
    private Integer downloadCount;
    private LocalDateTime uploadedAt;
    private Long userId;
}
