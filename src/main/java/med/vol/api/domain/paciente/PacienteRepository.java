package med.vol.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long>, PacienteRepositoryQuery {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select m.ativo from Paciente m where m.id = :id
            """)
    Long findAtivoById(Long id);

}
