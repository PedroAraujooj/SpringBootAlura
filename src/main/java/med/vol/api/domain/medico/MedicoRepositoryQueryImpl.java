package med.vol.api.domain.medico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import med.vol.api.controller.AutorizacaoListar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MedicoRepositoryQueryImpl implements MedicoRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Medico> findByLista(AutorizacaoListar lista, Pageable pageable) {
        String query = "select m from Medico m where 0=0";
        if (lista.crm() != null){
            query += " AND crm = " + lista.crm();
        }
        if (lista.nome() != null){
            query += " AND nome = '" + lista.nome() + "'";
        }
        if (lista.especialidade() != null){
            query += " AND especialidade = '" + lista.especialidade() + "'";
        }
        query += " order by " + pageable.getSort().toString().replaceAll(":", "");
        TypedQuery<Medico> consulta = manager.createQuery(query, Medico.class);
        adicionarRestricoesDePaginacao(consulta, pageable);
        return new PageImpl<>(consulta.getResultList(), pageable, total(lista));
        //return consulta.getResultList().stream().map(med -> new DadosListagemMedicos(med)).toList();
    }
    private Long total(AutorizacaoListar lista){
        String query = "select count(m) from Medico m where 0=0";
        if (lista.crm() != null){
            query += " AND crm = " + lista.crm();
        }
        if (lista.nome() != null){
            query += " AND nome = '" + lista.nome() + "'";
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
