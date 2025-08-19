package br.com.esig.service;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import br.com.esig.repository.PessoaRepository;

@Stateless
public class SalarioAsyncService {

    @Inject
    private PessoaRepository pessoaRepository;

    @Asynchronous
    public void calcularSalariosAsync() {
        System.out.println("Calculando salarios de forma assincrona");
        pessoaRepository.calcularSalarios();
    }
}
