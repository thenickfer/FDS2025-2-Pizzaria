package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class StatusPresenter {
    private String status;

    public StatusPresenter(Pedido.Status statusEnum) {
        switch (statusEnum) {
            case NOVO:
                status = "Novo";
                break;
            case AGUARDANDO:
                status = "Aguardando";
                break;
            case APROVADO:
                status = "Aprovado";
                break;
            case PREPARACAO:
                status = "Em preparação";
                break;
            case TRANSPORTE:
                status = "Em transporte";
                break;
            case PRONTO:
                status = "Pronto";
                break;
            case ENTREGUE:
                status = "Entregue";
                break;
            case PAGO:
                status = "Pago";
                break;
            default:
                status = "Desconhecido";
                break;
        }
    }

    public String getStatus() {
        return status;
    }
}
