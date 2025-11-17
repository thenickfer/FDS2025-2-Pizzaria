package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.MudaDescontoUC;

@RestController
@RequestMapping("/master")
public class MasterController {
    private MudaDescontoUC mudaDescontoUC;
    private CardapioController cardapioController;

    @Autowired
    public MasterController(MudaDescontoUC mudaDescontoUC, CardapioController cardapioController) {
        this.mudaDescontoUC = mudaDescontoUC;
        this.cardapioController = cardapioController;
    }

    @PatchMapping("/desconto")
    public ResponseEntity<Void> atualizaDesconto(@RequestParam(value = "nome_desconto") String nomeDesconto) {
        nomeDesconto = nomeDesconto.strip();
        if (mudaDescontoUC.mudarCalculoDesconto(nomeDesconto))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/cardapio")
    public ResponseEntity<Void> atualizaCardapio(@RequestParam(value = "id_cardapio") long idCardapio) {
        if (cardapioController.setCardapio(idCardapio))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

}
