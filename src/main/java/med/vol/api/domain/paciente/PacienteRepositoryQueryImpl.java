package med.vol.api.domain.paciente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import med.vol.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PacienteRepositoryQueryImpl implements PacienteRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Paciente> findByLista(AutorizacaoPaciente lista, Pageable pageable) {
        String query = "select p from Paciente p where ativo=1";
        query = adicionarParametros(lista, query);
        query += " order by " + pageable.getSort().toString().replaceAll(":", "");
        TypedQuery<Paciente> consulta = manager.createQuery(query, Paciente.class);
        consulta = (TypedQuery<Paciente>) setarParametro(lista, consulta);
        adicionarRestricoesDePaginacao(consulta, pageable);
        return new PageImpl<>(consulta.getResultList(), pageable, total(lista));
        //return consulta.getResultList().stream().map(med -> new DadosListagemMedicos(med)).toList();
    }
    private Long total(AutorizacaoPaciente lista){
        String query = "select count(p) from Paciente p where ativo=1";
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
    private String adicionarParametros(AutorizacaoPaciente lista, String query) {
        if (lista.cpf() != null){
            query += " AND cpf = :cpf";
        }
        if (lista.nome() != null){
            query += " AND nome = :nome";
        }
        return query;
    }
    private TypedQuery<?> setarParametro(AutorizacaoPaciente lista, TypedQuery<?> consulta) {
        if (lista.cpf() != null){
            consulta.setParameter("cpf", lista.cpf());
        }
        if (lista.nome() != null){
            consulta.setParameter("nome", lista.nome());
        }
        return consulta;
    }
}
