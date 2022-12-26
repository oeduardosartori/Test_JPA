package br.com.bancodedados.loja.testes;

import br.com.bancodedados.loja.dao.CategoriaDao;
import br.com.bancodedados.loja.dao.ProdutoDao;
import br.com.bancodedados.loja.modelo.Categoria;
import br.com.bancodedados.loja.modelo.Produto;
import br.com.bancodedados.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {

        cadastrarProduto();
        EntityManager manager = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(manager);

        Produto produto = produtoDao.buscarPorId(1l);
        System.out.println(produto.getPreco());

        List<Produto> todos = produtoDao.buscarCategoria("Eletrônicos");
        todos.forEach(produto1 -> System.out.println(produto1.getNome()));

        BigDecimal precoProduto = produtoDao.buscarPrecoComNome("Macbook");
        System.out.println("Preço do produto: R$ " + precoProduto);
    }

    private static void cadastrarProduto() {
        Categoria categoria = new Categoria("Eletrônicos");
        Produto produto = new Produto("Macbook", "Macbook Apple", new BigDecimal("18700"), categoria );

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(categoria);
        produtoDao.cadastrar(produto);

        em.getTransaction().commit();
        em.close();
    }
}
