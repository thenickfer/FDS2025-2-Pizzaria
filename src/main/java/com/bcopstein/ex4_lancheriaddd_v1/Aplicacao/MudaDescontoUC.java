package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto20Dias;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto30Dias;

@Component
public class MudaDescontoUC {
    private DescontoService descontoService;
    private CalculoDesconto20Dias clienteFrequente;
    private CalculoDesconto30Dias clienteGastador;

    @Autowired
    public MudaDescontoUC(DescontoService descontoService, CalculoDesconto20Dias clienteFrequente,
            CalculoDesconto30Dias clienteGastador) {
        this.descontoService = descontoService;
        this.clienteFrequente = clienteFrequente;
        this.clienteGastador = clienteGastador;
    }

    public boolean mudarCalculoDesconto(String nomeCalc) {
        if (nomeCalc.equalsIgnoreCase("gastador"))
            descontoService.setCalculoDesconto(clienteGastador);
        else if (nomeCalc.equalsIgnoreCase("frequente"))
            descontoService.setCalculoDesconto(clienteFrequente);
        else
            return false;
        return true;
    }
}
