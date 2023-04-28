package med.vol.api.domain.medico;

import med.vol.api.controller.AutorizacaoListar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.stream.DoubleStream;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable pagina);

    @Query("""
           select m from Medico m 
           where 
           m.ativo = 1
           and 
           m.especialidade = :especialidade
           and
           m.id not in(
                select c.medico.id from Consulta c
                where c.data = :data)
                order by rand()
           limit 1""")
    Medico escolherMedicoAleatorio(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.ativo from Medico m where m.id = :id
            """)
    int findAtivoById(Long id);


    Page<Medico> findByCrm(String crm, Pageable pagina);

}
