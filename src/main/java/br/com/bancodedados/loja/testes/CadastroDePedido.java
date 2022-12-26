package br.com.bancodedados.loja.testes;

import br.com.bancodedados.loja.dao.CategoriaDao;
import br.com.bancodedados.loja.dao.ClienteDao;
import br.com.bancodedados.loja.dao.PedidoDao;
import br.com.bancodedados.loja.dao.ProdutoDao;
import br.com.bancodedados.loja.modelo.*;
import br.com.bancodedados.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(entityManager);
        CategoriaDao categoriaDao = new CategoriaDao(entityManager);
        ClienteDao clienteDao = new ClienteDao(entityManager);

        Produto produto = produtoDao.buscarPorId(1l);
        Cliente cliente = clienteDao.buscarPorId(1l);

        entityManager.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new itemPedido(5, pedido, produto));

        PedidoDao pedidoDao = new PedidoDao(entityManager);
        pedidoDao.cadastrar(pedido);

        entityManager.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorVendido();
        System.out.println("Valor total: R$ " + totalVendido);

        List<Object[]> relatorio =  pedidoDao.relatorioVendas();
        for (Object[] object : relatorio) {
            System.out.println(object[0]);
            System.out.println(object[1]);
            System.out.println(object[2]);
        }

    }

    private static void popularBancoDeDados() {
        Categoria categoria = new Categoria("Eletrônicos");
        Produto produto = new Produto("Macbook", "Macbook Apple", new BigDecimal("18700"), categoria );

        Cliente cliente = new Cliente("Eduardo", "124365");
        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(entityManager);
        CategoriaDao categoriaDao = new CategoriaDao(entityManager);
        ClienteDao clienteDao = new ClienteDao(entityManager);

        entityManager.getTransaction().begin();

        categoriaDao.cadastrar(categoria);
        produtoDao.cadastrar(produto);
        clienteDao.cadastrar(cliente);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
