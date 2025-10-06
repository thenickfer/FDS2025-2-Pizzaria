package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

@Service
public class ImpostoService{

    private static final double TAXA = 0.10;

    public double calcularImposto(double valor){

        if (valor < 0){
            return 0;
        } 
        else{
            return valor * TAXA;
        }
    }
}