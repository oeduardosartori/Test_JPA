package br.com.bancodedados.loja.testes;

import br.com.bancodedados.loja.dao.CategoriaDao;
import br.com.bancodedados.loja.dao.ClienteDao;
import br.com.bancodedados.loja.dao.PedidoDao;
import br.com.bancodedados.loja.dao.ProdutoDao;
import br.com.bancodedados.loja.modelo.*;
import br.com.bancodedados.loja.util.JPAUtil;
import br.com.bancodedados.loja.vo.RelatorioVendasVo;

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
        Produto produto1 = produtoDao.buscarPorId(2l);
        Produto produto2 = produtoDao.buscarPorId(3l);

        Cliente cliente = clienteDao.buscarPorId(1l);

        entityManager.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new itemPedido(5, pedido, produto));
        pedido.adicionarItem(new itemPedido(20, pedido, produto1));

        Pedido pedido1 = new Pedido(cliente);
        pedido1.adicionarItem(new itemPedido(15, pedido1, produto2));

        PedidoDao pedidoDao = new PedidoDao(entityManager);
        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido1);

        entityManager.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorVendido();
        System.out.println("Valor total: R$ " + totalVendido);

        List<RelatorioVendasVo> relatorio =  pedidoDao.relatorioVendas();
        relatorio.forEach(System.out::println);

    }

    private static void popularBancoDeDados() {
        Categoria categoria = new Categoria("Eletrônicos");
        Categoria categoria1 = new Categoria("Informática");
        Categoria categoria2 = new Categoria("Games");

        Produto produto = new Produto("Macbook", "Macbook Apple", new BigDecimal("18700"), categoria );
        Produto produto1 = new Produto("Teclado Mecânico", "Teclado Redragon", new BigDecimal("277"), categoria1);
        Produto produto2 = new Produto("Playstation 5", "Vídeo Game Sony", new BigDecimal("4900"), categoria2);

        Cliente cliente = new Cliente("Eduardo", "124365");
        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(entityManager);
        CategoriaDao categoriaDao = new CategoriaDao(entityManager);
        ClienteDao clienteDao = new ClienteDao(entityManager);

        entityManager.getTransaction().begin();

        categoriaDao.cadastrar(categoria);
        categoriaDao.cadastrar(categoria1);
        categoriaDao.cadastrar(categoria2);

        produtoDao.cadastrar(produto);
        produtoDao.cadastrar(produto1);
        produtoDao.cadastrar(produto2);

        clienteDao.cadastrar(cliente);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
