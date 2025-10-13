package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.StatusPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmetePedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmetePedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.StatusPedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.SubmetePedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CancelaPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.PagaPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaStatusPedidoUC;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private SubmetePedidoUC submetePedidoUC;
    private RecuperaStatusPedidoUC recuperaStatusPedidoUC;
    private CancelaPedidoUC cancelaPedidoUC;
    private PagaPedidoUC pagaPedidoUC;

    public PedidoController(SubmetePedidoUC submetePedidoUC, RecuperaStatusPedidoUC recuperaStatusPedidoUC,
            CancelaPedidoUC cancelaPedidoUC, PagaPedidoUC pagaPedidoUC) {
        this.submetePedidoUC = submetePedidoUC;
        this.recuperaStatusPedidoUC = recuperaStatusPedidoUC;
        this.cancelaPedidoUC = cancelaPedidoUC;
        this.pagaPedidoUC = pagaPedidoUC;
    }// Usar response entities, tratamento de excecao ta uma bosta
     // provavelmente o melhor seria response entities + uma classe pra tratamento de
     // excecoes

    @GetMapping("/status/{id}")
    @CrossOrigin("*")
    public ResponseEntity<StatusPresenter> getStatus(@PathVariable(value = "id") long id) {
        StatusPedidoResponse statusResponse = recuperaStatusPedidoUC.run(id);
        return ResponseEntity.ok(new StatusPresenter(statusResponse.status()));
    }

    @PostMapping("/submit")
    @CrossOrigin("*")
    public ResponseEntity<PedidoPresenter> submetePedido(@RequestBody PedidoRequest pedido) {

        SubmetePedidoRequest applicationRequest = new SubmetePedidoRequest(
                pedido.getCliente(),
                pedido.getDataHoraPagamento(),
                pedido.getItens().stream()
                        .map(item -> new SubmetePedidoRequest.ItemRequest(item.getProdutoId(), item.getQuantidade()))
                        .toList());

        SubmetePedidoResponse pedidoResponse = submetePedidoUC.run(applicationRequest);
        PedidoPresenter pedidoPresenter = new PedidoPresenter(
                pedidoResponse.getId(),
                pedidoResponse.getCliente(),
                pedidoResponse.getDataHoraPagamento(),
                pedidoResponse.getItens(),
                pedidoResponse.getStatus(),
                pedidoResponse.getValor(),
                pedidoResponse.getImpostos(),
                pedidoResponse.getDesconto(),
                pedidoResponse.getValorCobrado());
        return ResponseEntity.ok(pedidoPresenter);
    }

    @DeleteMapping("/cancelar/{id}")
    @CrossOrigin("*")
    public ResponseEntity<Void> cancelarPedido(@PathVariable(value = "id") long id) {
        boolean ok = cancelaPedidoUC.run(id);
        if (ok)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/pagamento/{id}")
    @CrossOrigin("*")
    public ResponseEntity<PedidoPresenter> pagarPedido(@PathVariable(value = "id") long id) {
        Pedido ped = pagaPedidoUC.run(id);// agora que me dei conta que esqueci de fazer um objeto no nivel de aplicacao
                                          // pro pedido, mas sao 2:30 da manha, facam vcs isso
        if (ped != null) {
            return ResponseEntity.ok(new PedidoPresenter(id, ped.getCliente(), ped.getDataHoraPagamento(),
                    ped.getItens(), ped.getStatus(), ped.getValor(), ped.getImpostos(), ped.getDesconto(),
                    ped.getValorCobrado()));
        }
        return ResponseEntity.badRequest().build();

    }

}