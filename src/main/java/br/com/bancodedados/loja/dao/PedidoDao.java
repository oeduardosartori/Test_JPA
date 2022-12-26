package br.com.bancodedados.loja.dao;

import br.com.bancodedados.loja.modelo.Pedido;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

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

}
