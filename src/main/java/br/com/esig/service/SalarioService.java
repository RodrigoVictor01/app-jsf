package br.com.esig.service;

import br.com.esig.model.PessoaSalarioConsolidado;
import br.com.esig.repository.PessoaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class SalarioService {

    @Inject
    private PessoaRepository repository;

    public List<PessoaSalarioConsolidado> listarPessoas() {
        return repository.listarPessoas();
    }

    public void calcularSalarios() {
        repository.calcularSalarios();
    }
}
