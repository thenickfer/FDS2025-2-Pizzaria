package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto;

@Service
public class DescontoService {
    private CalculoDesconto cd;

    @Autowired
    public DescontoService(CalculoDesconto cd) {
        this.cd = cd;
    }

    public double calcularDesconto(Pedido p) {
        return this.cd.calcularDesconto(p);   
    }

}