package br.com.bancodedados.loja.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class itemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal precoUnitario;
    private int quantidade;
    @ManyToOne
    private Pedido pedido;
    @ManyToOne
    private Produto produto;

    public itemPedido() {
    }

    public itemPedido(int quantidade, Pedido pedido, Produto produto) {
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.precoUnitario = produto.getPreco();
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setPedido(Pedido pedido) {
    }
}
