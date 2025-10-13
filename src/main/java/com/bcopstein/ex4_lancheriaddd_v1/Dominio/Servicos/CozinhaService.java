package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class CozinhaService {
    private Queue<Pedido> filaEntrada;
    private Pedido emPreparacao;
    private Queue<Pedido> filaSaida;
    private PedidoRepository pedidoRepository;
    private EntregaService entregaService;

    private ScheduledExecutorService scheduler;

    @Autowired
    public CozinhaService(PedidoRepository pedidoRepository, EntregaService entregaService) {
        filaEntrada = new LinkedBlockingQueue<Pedido>();
        emPreparacao = null;
        filaSaida = new LinkedBlockingQueue<Pedido>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        this.pedidoRepository = pedidoRepository;
        this.entregaService = entregaService;
        mandaPedido();
    }

    private synchronized void colocaEmPreparacao(Pedido pedido) {
        pedido.setStatus(Pedido.Status.PREPARACAO);
        emPreparacao = pedido;
        pedidoRepository.atualizarStatus(pedido.getId(), Pedido.Status.PREPARACAO);
        System.out.println("Pedido em preparacao: " + pedido);
        // Agenda pedidoPronto para ser chamado em 2 segundos
        scheduler.schedule(() -> pedidoPronto(), 5, TimeUnit.SECONDS);
    }

    public synchronized void chegadaDePedido(Pedido p) {
        filaEntrada.add(p);
        pedidoRepository.atualizarStatus(p.getId(), Pedido.Status.AGUARDANDO);
        System.out.println("Pedido na fila de entrada: " + p);
        if (emPreparacao == null) {
            colocaEmPreparacao(filaEntrada.poll());
        }
    }

    public synchronized void pedidoPronto() { // chamar metodo para mexer no repositorio
        emPreparacao.setStatus(Pedido.Status.PRONTO);
        pedidoRepository.atualizarStatus(emPreparacao.getId(), Pedido.Status.PRONTO);
        filaSaida.add(emPreparacao);
        System.out.println("Pedido na fila de saida: " + emPreparacao);
        emPreparacao = null;
        // Se tem pedidos na fila, programa a preparação para daqui a 1 segundo
        if (!filaEntrada.isEmpty()) {
            Pedido prox = filaEntrada.poll();
            scheduler.schedule(() -> colocaEmPreparacao(prox), 1, TimeUnit.SECONDS);
        }
    }

    public void mandaPedido() {
        if (!filaSaida.isEmpty())
            entregaService.receberPedidoParaEntrega(filaSaida.poll());
        scheduler.schedule(() -> mandaPedido(), 2, TimeUnit.SECONDS);
    }
}
