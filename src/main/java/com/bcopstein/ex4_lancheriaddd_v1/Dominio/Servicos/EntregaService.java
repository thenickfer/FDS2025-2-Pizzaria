package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
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
    private BlockingQueue<Pedido> listaPedidosProntos;
    private ScheduledExecutorService scheduler;

    @Autowired
    public EntregaService(PedidoRepository pedidoRepository, BlockingQueue<Pedido> listaPedidosProntos) {
        this.pedidoRepository = pedidoRepository;
        this.listaPedidosProntos = listaPedidosProntos;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        this.scheduler.scheduleAtFixedRate(this::iniciandoTransporte, 5, 5,
        TimeUnit.SECONDS);
    }

    public synchronized void receberPedidoParaEntrega(Pedido p) {
        System.out.println("Recebemos o pedido " + p.getId() + "para entrega, aguardando entregador");
        pedidoRepository.atualizarStatus(p.getId(), Pedido.Status.TRANSPORTE);
        listaPedidosProntos.add(p);
    }

    private synchronized void iniciandoTransporte() {
        Pedido p = listaPedidosProntos.poll();

        if(p == null){
           return; // NAO FAZER EXCECAO,O SCHEDULER BUGA
        }

        System.out.println("Entregador pegou o pedido " + p.getId() + "e esta em transporte");
        p.setStatus(Status.TRANSPORTE);
        pedidoRepository.atualizarStatus(p.getId(), Status.TRANSPORTE);
        
        scheduler.schedule(() -> finalizarEntrega(p), 5, TimeUnit.SECONDS);
        
    }

    private synchronized void finalizarEntrega(Pedido p) {
        System.out.println("Entrega do pedido " + p.getId() + "finalizada");

        p.setStatus(Status.ENTREGUE);
        pedidoRepository.atualizarStatus(p.getId(), Status.ENTREGUE);

    }

}
