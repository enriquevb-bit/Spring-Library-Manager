package enriquevb.biblioteca.entities;

import enriquevb.biblioteca.models.LoanState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Loan {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanState loanState;

    private LocalDateTime loanDate;
    private LocalDateTime expiringDate;
    private LocalDateTime dueDate;


    // Relationships

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "loan")
    private Set<LoanLine> loan = new HashSet<>();
}
