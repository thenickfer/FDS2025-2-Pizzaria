package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface CalculoDesconto {
    double calcularDesconto(Pedido p);
}
