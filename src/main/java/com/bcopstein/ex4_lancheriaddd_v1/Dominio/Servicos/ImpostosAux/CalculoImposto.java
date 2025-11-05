package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostosAux;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface CalculoImposto {
    double calcularImposto(Pedido pedido);
}
