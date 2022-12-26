package br.com.bancodedados.loja.dao;

import br.com.bancodedados.loja.modelo.Cliente;
import br.com.bancodedados.loja.modelo.Pedido;

import javax.persistence.EntityManager;

public class ClienteDao {

    private EntityManager manager;

    public ClienteDao(EntityManager manager) {
        this.manager = manager;
    }

    public void cadastrar(Cliente cliente) {
        this.manager.persist(cliente);
    }

    public Cliente buscarPorId(long id) {
        return manager.find(Cliente.class, id);
    }
}
