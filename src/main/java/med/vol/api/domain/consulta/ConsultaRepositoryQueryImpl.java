package med.vol.api.domain.consulta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import med.vol.api.domain.paciente.Paciente;
import med.vol.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ConsultaRepositoryQueryImpl implements ConsultaRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Page<Consulta> findByLista(AutorizacaoConsulta lista, Pageable pageable) {

        String query = "select * from Consultas c where 0=0";
        if (lista.idPaciente() != null){
             query += " AND Paciente = " + pacienteRepository.getReferenceById(lista.idPaciente());
        }
        if (lista.idMedico() != null){
            query += " AND medico_id = " + lista.idMedico();
        }
        if (lista.especialidade() != null){
            query += " AND especialidade = '" + lista.especialidade() + "'";
        }
//        query += " order by " + pageable.getSort().toString().replaceAll(":", "");
        TypedQuery<Consulta> consulta = manager.createQuery(query, Consulta.class);
        adicionarRestricoesDePaginacao(consulta, pageable);
        return new PageImpl<>(consulta.getResultList(), pageable, total(lista));
    }
    private Long total(AutorizacaoConsulta lista){
        String query = "select count(c) from Consulta c where 0=0";
        if (lista.idPaciente() != null){
            query += " AND paciente_id = " + lista.idPaciente();
        }
        if (lista.idMedico() != null){
            query += " AND medico_id = "+ lista.idMedico();
        }
        if (lista.especialidade() != null){
            query += " AND especialidade = '" + lista.especialidade() + "'";
        }
        return manager.createQuery(query, Long.class).getSingleResult();
    }
    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);

    }
}

