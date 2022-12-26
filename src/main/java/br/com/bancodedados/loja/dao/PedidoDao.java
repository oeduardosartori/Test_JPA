package br.com.bancodedados.loja.dao;

import br.com.bancodedados.loja.modelo.Pedido;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private EntityManager manager;

    public PedidoDao(EntityManager manager) {
        this.manager = manager;
    }

    public void cadastrar(Pedido pedido) {
        this.manager.persist(pedido);
    }

    public BigDecimal valorVendido() {
        String jpql = "select sum(p.valorTotal) from Pedido p";
        return manager.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<Object[]> relatorioVendas() {
        String jpql = "SELECT produto.nome, " +
                "SUM(item.quantidade), " +
                "MAX(pedido.data) " +
                "FROM Pedido pedido" +
                "JOIN pedido.itens item" +
                "JOIN item.produto produto" +
                "GROUP BY produto.nome" +
                "ORDER BY item.quantidade DESC";
        return manager.createQuery(jpql, Object[].class)
                .getResultList();
    }

}
