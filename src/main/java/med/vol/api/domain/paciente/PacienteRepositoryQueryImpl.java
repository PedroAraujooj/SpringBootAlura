package med.vol.api.domain.paciente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PacienteRepositoryQueryImpl implements PacienteRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Paciente> findByLista(AutorizacaoPaciente lista, Pageable pageable) {
        String query = "select p from Paciente p where ativo=1";
        if (lista.cpf() != null){
            query += " AND cpf = '" + lista.cpf()+ "'";
        }
        if (lista.nome() != null){
            query += " AND nome = '" + lista.nome() + "'";
        }
        query += " order by " + pageable.getSort().toString().replaceAll(":", "");
        TypedQuery<Paciente> consulta = manager.createQuery(query, Paciente.class);
        adicionarRestricoesDePaginacao(consulta, pageable);
        return new PageImpl<>(consulta.getResultList(), pageable, total(lista));
        //return consulta.getResultList().stream().map(med -> new DadosListagemMedicos(med)).toList();
    }
    private Long total(AutorizacaoPaciente lista){
        String query = "select count(p) from Paciente p where ativo=1";
        if (lista.cpf() != null){
            query += " AND cpf = '" + lista.nome() + "'";
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
