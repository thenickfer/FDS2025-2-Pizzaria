package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class StatusPresenter {
    private String status;

    public StatusPresenter(Pedido.Status statusEnum) {
        status = statusEnum.name();
    }

    public String getStatus() {
        return status;
    }
}
