package med.vol.api.domain.consulta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ConsultaRepositoryQueryImpl implements ConsultaRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Consulta> findByLista(AutorizacaoConsulta lista, Pageable pageable) {
        String query = "select c from Consulta c where 0=0";
        query = adicionarParametros(lista, query);
//        query += " order by " + pageable.getSort().toString().replaceAll(":", "");
        TypedQuery<Consulta> consulta = manager.createQuery(query, Consulta.class);
        consulta = (TypedQuery<Consulta>) setarParametro(lista, consulta);
        adicionarRestricoesDePaginacao(consulta, pageable);
        return new PageImpl<>(consulta.getResultList(), pageable, total(lista));
    }
    private Long total(AutorizacaoConsulta lista){
        String query = "select count(c) from Consulta c where 0=0";
        query = adicionarParametros(lista, query);
        TypedQuery<Long> consulta = manager.createQuery(query, Long.class);
        consulta = (TypedQuery<Long>) setarParametro(lista, consulta);
        return (consulta.getSingleResult());
    }
    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }
    private String adicionarParametros(AutorizacaoConsulta lista, String query) {
        if (lista.idPaciente() != null){
            query += " AND c.paciente.id = :paciente";
        }
        if (lista.idMedico() != null){
            query += " AND c.medico.id = :medico";
        }
        if (lista.especialidade() != null){
            query += " AND especialidade = :especialidade";
        }
        return query;
    }
    private TypedQuery<?> setarParametro(AutorizacaoConsulta lista, TypedQuery<?> consulta) {
        if (lista.idPaciente() != null){
            consulta.setParameter("paciente", lista.idPaciente());
        }
        if (lista.idMedico() != null){
            consulta.setParameter("medico", lista.idMedico());
        }
        if (lista.especialidade() != null){
            consulta.setParameter("especialidade", lista.especialidade());
        }
        return consulta;
    }

}

