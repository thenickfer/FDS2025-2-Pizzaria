package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.Queue;

import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido.Status;

@Service
public class EntregaService {
    private PedidoRepository pedidoRepository;
    private Queue<Pedido> listaPedidosProntos;
    private ScheduledExecutorService scheduler;

    public EntregaService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.listaPedidosProntos = new LinkedBlockingQueue<Pedido>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        // simula um entregador ficando disponivel a cada 5 sec
        // this.scheduler.scheduleAtFixedRate(this::despacharPedido, 5, 5,
        // TimeUnit.SECONDS);
    }

    public synchronized void receberPedidoParaEntrega(Pedido p) {
        System.out.println("Recebemos o pedido " + p.getId() + "para entrega, aguardando entregador");
        listaPedidosProntos.add(p);
    }

    private synchronized void iniciandoTransporte() {
        Pedido p = listaPedidosProntos.poll();

        if (p != null) {
            System.out.println("Entregador pegou o pedido " + p.getId() + "e esta em transporte");
            p.setStatus(Status.TRANSPORTE);
            pedidoRepository.atualizarStatus(p.getId(), Status.TRANSPORTE);

            scheduler.schedule(() -> finalizarEntrega(p), 5, TimeUnit.SECONDS);
        }
    }

    private synchronized void finalizarEntrega(Pedido p) {
        System.out.println("Entrega do pedido " + p.getId() + "finalizada");

        p.setStatus(Status.ENTREGUE);
        pedidoRepository.atualizarStatus(p.getId(), Status.ENTREGUE);

    }

}
