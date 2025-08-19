# Pessoa Salário Consolidado

## Requisitos
- Java 11+ (para build do projeto)
- Maven
- WildFly 37.0.0


## Como rodar o projeto

### 1. Banco de dados PostgreSQL

Instale e configure o PostgreSQL localmente:
- Usuário: `postgres`, Senha: `sua-senha`
  
- Crie o banco `app_jsf_db`. com o comando:
```sh
CREATE DATABASE app_jsf_db;
```

- Após o banco criado, no diretório raiz do projeto, rode:
```sh
psql -h localhost -U postgres -d app_jsf_db -f .\create_tables.sql
````
- Após a criação das tabelas no banco, rode:
``` sh
psql -h localhost -U postgres -d app_jsf_db -f .\copyfromcsv.sql
```


### 2. Configuração do WildFly

Instale o WildFly localmente (versão 37.0.0.Final) e configure o datasource no arquivo `standalone.xml`:
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
- Script de schema: `create_tables.sql`.
- Scprit que popula o banco com os dados da planilha: `copyfromcsv.sql`

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

# Links úteis
- [Download WildFly](https://www.wildfly.org/downloads/)
- [Download do postgresql-42.7.7 para configuração WildFly](https://jdbc.postgresql.org/download/)

