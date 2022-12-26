package br.com.bancodedados.loja.dao;

import br.com.bancodedados.loja.modelo.Produto;
import org.ietf.jgss.ChannelBinding;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDao {

    private EntityManager manager;

    public ProdutoDao(EntityManager manager) {
        this.manager = manager;
    }

    public void cadastrar(Produto produto) {
        this.manager.persist(produto);
    }

    public void atualizar(Produto produto) {
        this.manager.merge(produto);
    }

    public void remover(Produto produto) {
        produto = manager.merge(produto);
        this.manager.remove(produto);
    }

    public Produto buscarPorId(Long id){
        return manager.find(Produto.class, id);
    }

    public List buscarTodos(){
        String jpql = "SELECT p FROM Produto p";
        return manager.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarNome(String nome){
        String jpql = "SELECT p from Produto p WHERE p.nome = :nome";
        return manager.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarCategoria(String nome){
        String jpql = "SELECT p from Produto p WHERE p.categoria.nome = :nome";
        return manager.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoComNome(String nome){
        String jpql = "SELECT p.preco from Produto p WHERE p.nome = :nome";
        return manager.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public List<Produto> buscaParametrosCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);

        Predicate filtros = builder.and();

        if (nome != null && !nome.trim().isEmpty()) {
            builder.and(filtros, builder.equal(from.get("nome"),nome));
        }
        else if (preco != null) {
            builder.and(filtros, builder.equal(from.get("preco"),preco));
        }
        else if (dataCadastro != null) {
            builder.and(filtros, builder.equal(from.get("dataCadastro"),dataCadastro));
        }
        query.where(filtros);

        return manager.createQuery(query).getResultList();
    }
}
