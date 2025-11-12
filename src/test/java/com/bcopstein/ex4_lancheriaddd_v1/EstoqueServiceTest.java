package com.bcopstein.ex4_lancheriaddd_v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Receita;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.EstoqueService;

public class EstoqueServiceTest {

    @Mock
    private ItemEstoqueRepository itemEstoqueRepository;

    private EstoqueService estoqueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<ItemEstoque> lista = Arrays.asList(new ItemEstoque(new Ingrediente(0), 1));
        when(itemEstoqueRepository.getAll()).thenReturn(lista);
        estoqueService = new EstoqueService(itemEstoqueRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2, false",
    })
    void aprovaPedido(int quant, boolean expectedResult) {
        List<ItemPedido> lista = Arrays.asList(
                new ItemPedido(0,
                        new Produto(1, "Cafe", new Receita(0, "Cafe a lait", Arrays.asList(new Ingrediente(0))), 5000),
                        quant));
        Pedido p = new Pedido(0, null, null, lista, null, 0, 0, 0, 0);
        assertEquals(expectedResult, estoqueService.avaliarPedido(p).getStatus().equals(Pedido.Status.APROVADO));

    }
}
