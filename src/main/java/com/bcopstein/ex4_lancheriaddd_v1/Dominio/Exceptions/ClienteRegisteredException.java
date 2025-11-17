package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

public class ClienteRegisteredException extends RuntimeException{

    public ClienteRegisteredException (String email, String cpf){
        super ("Cliente já registrado. Email: "+email+" CPF: "+cpf);
    }
    
}
