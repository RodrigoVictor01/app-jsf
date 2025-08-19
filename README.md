# Pessoa Salário Consolidado

## Requisitos
- Docker e Docker Compose instalados
- Java 11+ (para build do projeto)
- Maven
- WildFly 37.0.0


## Como rodar o projeto

### 1. Banco de dados PostgreSQL

Instale e configure o PostgreSQL localmente:
- Crie o banco `app_jsf_db`.
- Usuário: `postgres`, Senha: `sua-senha`


### 2. Configuração do WildFly

Instale o WildFly localmente e configure o datasource no arquivo `standalone.xml`:
```xml
<datasources>
    <datasource jndi-name="java:/PostgresDS" pool-name="PostgresDS" enabled="true" use-java-context="true">
        <connection-url>sua-connection-url</connection-url>
        <driver>postgresql</driver>
        <security user-name="postgres" password="sua-senha"/>
    </datasource>
    <drivers>
        <driver name="postgresql" module="org.postgresql">
            <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
        </driver>
    </drivers>
</datasources>
```
Certifique-se de instalar o driver do PostgreSQL no WildFly.

### 3. Build e Deploy da aplicação

No diretório raiz do projeto:
```sh
mvn clean package
```
O arquivo WAR gerado estará em `target/pessoa-salario-consolidado.war`.
Faça o deploy manualmente pelo console do WildFly ou mova para o diretório de deploy ou na IDE de sua preferência.


### 4. Acessando a aplicação

- Acesse `http://localhost:8080/pessoa-salario-consolidado/` no navegador.
- O WildFly Admin Console estará disponível em `http://localhost:9990/`.

### 5. Configuração do Hibernate/JPA

No arquivo `src/main/resources/META-INF/persistence.xml`:
- O datasource está configurado como `java:/PostgresDS`.
- O schema do banco deve ser criado manualmente (`hibernate.hbm2ddl.auto = none`).
- Scripts de schema estão em `docker/init-scripts/01_schema.sql`.

---

## Resumo dos principais arquivos
- `create_tables.sql`: cria o schema do banco
- `standalone.xml`: configuração completa do WildFly e datasource
- `src/main/resources/META-INF/persistence.xml`: configurações JPA/Hibernate
- `target/pessoa-salario-consolidado.war`: artefato gerado para deploy

---

## Dicas
- Para logs do WildFly: verifique o diretório de logs do WildFly
- Para logs do PostgreSQL: verifique o diretório de logs do PostgreSQL
- Para acessar o banco: `psql -h localhost -U postgres -d app_jsf_db`

---

Se precisar de configurações avançadas, ajuste os arquivos conforme sua necessidade!
