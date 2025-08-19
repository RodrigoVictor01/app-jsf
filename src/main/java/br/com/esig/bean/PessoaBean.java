package br.com.esig.bean;

import br.com.esig.model.PessoaSalarioConsolidado;
import br.com.esig.repository.PessoaRepository;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import br.com.esig.service.SalarioAsyncService;

@Named("pessoaBean")
@ViewScoped
public class PessoaBean implements Serializable {

    @Inject
    private PessoaRepository repository;

    private boolean salarioCalculado = false;
    private List<PessoaSalarioConsolidado> pessoas;
    @Inject
    private SalarioAsyncService salarioAsyncService;

    private boolean calculandoSalarios = false;

    public void calcularSalariosAssincrono() {
        calculandoSalarios = true;
        salarioAsyncService.calcularSalariosAsync();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        atualizarDadosSalario();
        calculandoSalarios = false;
    }

    public void atualizarDadosSalario() {
        this.pessoas = repository.listarPessoas();
        this.salarioCalculado = true;
    }

    public boolean isCalculandoSalarios() {
        return calculandoSalarios;
    }

    @PostConstruct
    public void init() {
        try {
            this.pessoas = repository.listarPessoas();
        } catch (Exception e) {
            System.err.println("Erro ao carregar pessoas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<PessoaSalarioConsolidado> pessoasVencimentoCredito;

    public List<PessoaSalarioConsolidado> getPessoasVencimentoCredito() {
        if (pessoasVencimentoCredito == null) {
            pessoasVencimentoCredito = repository.listarVencimentosCreditos();
        }
        return pessoasVencimentoCredito;
    }

    public List<PessoaSalarioConsolidado> getPessoas() {
        return pessoas;
    }

    public boolean isSalarioCalculado() {
        return salarioCalculado;
    }

}
