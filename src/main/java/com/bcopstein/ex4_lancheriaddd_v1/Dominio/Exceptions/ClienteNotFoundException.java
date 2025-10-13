package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

public class ClienteNotFoundException extends RuntimeException{

    public ClienteNotFoundException (String cpf){
        super ("Cliente nao encontrado. CPF: "+cpf);
    }
    
}
