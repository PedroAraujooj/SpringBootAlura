package med.vol.api.domain.medico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MedicoRepositoryQueryImpl implements MedicoRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Medico> findByLista(AutorizacaoMedico lista, Pageable pageable) {
        String query = "select m from Medico m where 0=0";
        query = adicionarParametros(lista, query);
        query += " order by " + pageable.getSort().toString().replaceAll(":", "");
        TypedQuery<Medico> consulta = manager.createQuery(query, Medico.class);
        consulta = (TypedQuery<Medico>) setarParametro(lista, consulta);
        adicionarRestricoesDePaginacao(consulta, pageable);
        return new PageImpl<>(consulta.getResultList(), pageable, total(lista));
        //return consulta.getResultList().stream().map(med -> new DadosListagemMedicos(med)).toList();
    }
    private Long total(AutorizacaoMedico lista){
        String query = "select count(m) from Medico m where 0=0";
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
    private String adicionarParametros(AutorizacaoMedico lista, String query) {
        if (lista.crm() != null){
            query += " AND crm = :crm";
        }
        if (lista.nome() != null){
            query += "AND nome = :nome";
        }
        if (lista.especialidade() != null){
            query += " AND especialidade = :especialidade";
        }
        return query;
    }
    private TypedQuery<?> setarParametro(AutorizacaoMedico lista, TypedQuery<?> consulta) {
        if (lista.crm() != null){
            consulta.setParameter("crm", lista.crm());
        }
        if (lista.nome() != null){
            consulta.setParameter("nome", lista.nome());
        }
        if (lista.especialidade() != null){
            consulta.setParameter("especialidade", lista.especialidade());
        }
        return consulta;
    }
}
