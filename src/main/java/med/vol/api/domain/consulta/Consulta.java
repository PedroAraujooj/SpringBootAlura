package med.vol.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas", schema = "DB_PEDRO")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @SequenceGenerator(name = "DB_PEDRO.SQ_CONSULTA", sequenceName = "DB_PEDRO.SQ_CONSULTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DB_PEDRO.SQ_CONSULTA")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column(name = "data")
    private LocalDateTime data;

}
