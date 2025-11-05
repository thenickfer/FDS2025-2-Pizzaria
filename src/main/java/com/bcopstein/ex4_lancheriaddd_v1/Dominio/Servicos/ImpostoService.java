package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostosAux.CalculoImposto;

@Service
public class ImpostoService {
    private CalculoImposto ci;

    @Autowired
    public ImpostoService(CalculoImposto ci) {
        this.ci = ci;
    }

    public double calcularImposto(Pedido pedido) {
        return this.ci.calcularImposto(pedido);
    }
}