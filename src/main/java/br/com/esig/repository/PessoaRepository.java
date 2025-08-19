package br.com.esig.repository;

import br.com.esig.model.PessoaSalarioConsolidado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@SuppressWarnings("unchecked")
public class PessoaRepository {

    @PersistenceContext(unitName = "esigPU")
    private EntityManager em;

    public List<PessoaSalarioConsolidado> listarVencimentosCreditos() {
        try {
            List<Object[]> resultados = em.createNativeQuery(
                    "SELECT p.id, p.nome, c.nome, COALESCE(SUM(CASE WHEN v.tipo = 'CREDITO' THEN v.valor ELSE 0 END), 0) AS vencimento_total "
                            +
                            "FROM pessoa p " +
                            "JOIN cargo c ON p.cargo_id = c.id " +
                            "LEFT JOIN cargo_vencimentos cv ON c.id = cv.cargo_id " +
                            "LEFT JOIN vencimentos v ON cv.vencimento_id = v.id " +
                            "GROUP BY p.id, p.nome, c.nome " +
                            "ORDER BY p.id")
                    .getResultList();

            List<PessoaSalarioConsolidado> pessoas = new ArrayList<>();
            for (Object[] row : resultados) {
                PessoaSalarioConsolidado pessoa = new PessoaSalarioConsolidado();
                pessoa.setPessoa_id((Integer) row[0]);
                pessoa.setNome_pessoa((String) row[1]);
                pessoa.setNome_cargo((String) row[2]);
                pessoa.setSalario(((Number) row[3]).doubleValue());
                pessoas.add(pessoa);
            }
            return pessoas;
        } catch (Exception e) {
            System.err.println("Erro ao listar vencimentos créditos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PessoaSalarioConsolidado> listarPessoas() {
        try {

            if (em == null) {
                System.err.println("EntityManager é null");
                return new ArrayList<>();
            }

            List<Object[]> resultados = em.createNativeQuery(
                    "SELECT pessoa_id, nome_pessoa, nome_cargo, salario FROM pessoa_salario_consolidado ORDER BY pessoa_id")
                    .getResultList();

            List<PessoaSalarioConsolidado> pessoas = new ArrayList<>();
            for (Object[] row : resultados) {
                PessoaSalarioConsolidado pessoa = new PessoaSalarioConsolidado();
                pessoa.setPessoa_id((Integer) row[0]);
                pessoa.setNome_pessoa((String) row[1]);
                pessoa.setNome_cargo((String) row[2]);
                pessoa.setSalario(((Number) row[3]).doubleValue());
                pessoas.add(pessoa);
            }

            return pessoas;
        } catch (Exception e) {
            System.err.println("Erro ao listar pessoas: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Transactional
    public void calcularSalarios() {
        try {

            if (em == null) {
                System.err.println("EntityManager é null!");
                return;
            }

            // query que deleta os registros existentes e insere apenas os registros
            // atualizados, evitando duplicidade ou inconsistência de dados
            em.createNativeQuery("DELETE FROM pessoa_salario_consolidado").executeUpdate();

            String sql = "INSERT INTO pessoa_salario_consolidado (pessoa_id, nome_pessoa, nome_cargo, salario) " +
                    "SELECT p.id AS pessoa_id, p.nome AS nome_pessoa, c.nome AS nome_cargo, " +
                    "COALESCE(SUM(CASE WHEN v.tipo = 'CREDITO' THEN v.valor ELSE -v.valor END), 0) AS salario " +
                    "FROM pessoa p " +
                    "JOIN cargo c ON p.cargo_id = c.id " +
                    "LEFT JOIN cargo_vencimentos cv ON c.id = cv.cargo_id " +
                    "LEFT JOIN vencimentos v ON cv.vencimento_id = v.id " +
                    "GROUP BY p.id, p.nome, c.nome " +
                    "ORDER BY p.id";

            int registrosInseridos = em.createNativeQuery(sql).executeUpdate();
            System.out.println("Salarios calculados: " + registrosInseridos + " registros inseridos");

        } catch (Exception e) {
            System.err.println("Erro ao calcular salarios: " + e.getMessage());
            e.printStackTrace();
        }
    }
}