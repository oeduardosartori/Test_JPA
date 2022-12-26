package br.com.bancodedados.loja.dao;

import br.com.bancodedados.loja.modelo.Pedido;
import br.com.bancodedados.loja.vo.RelatorioVendasVo;

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

    public List<RelatorioVendasVo> relatorioVendas() {
        String jpql = "SELECT new br.com.bancodedados.loja.vo.RelatorioVendasVo( " +
                "produto.nome, " +
                "SUM(item.quantidade), " +
                "MAX(pedido.data)) " +
                "FROM Pedido pedido" +
                "JOIN pedido.itens item" +
                "JOIN item.produto produto" +
                "GROUP BY produto.nome" +
                "ORDER BY item.quantidade DESC";
        return manager.createQuery(jpql, RelatorioVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id) {
        return manager.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
