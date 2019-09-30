package br.com.fiap.reciclamais.service;

import java.util.List;

import br.com.fiap.reciclamais.resource.ScannerResource;

public class ScannerService {

    ScannerResource resource;

    public void registrar (String conteudo){
        resource.inserir(conteudo);
    }

    public List<String> listar(){
        return resource.listar();
    }


}
