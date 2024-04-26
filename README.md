# Assembleia Vota

[![CI Prod](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-prod.yaml/badge.svg)](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-prod.yaml) [![CI Develop](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-develop.yaml/badge.svg)](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-develop.yaml)
## Resumo da Aplicação de Votação

Esta aplição é destinada a gerenciar e facilitar o processo de votação dentro do ambiente cooperativo. Utilizando uma API REST, a aplicação oferece os seguintes recursos:

- Cadastro de novas pautas
- Abertura de sessões de votação (com duração pré-definida ou padrão de 1 minuto)
- Recebimento e armazenamento de votos dos associados (os votos são apenas 'Sim'/'Não')
- Contabilização dos votos e divulgação dos resultados da votação

Cada associado é identificado por um ID único e só pode votar uma vez por pauta. A aplicação foi construída em Java, com Spring-boot. Todos os dados de pautas e votos são persistidos e não são perdidos após o reinício da aplicação.

A segurança das interfaces foi abstraída e todas as chamadas para as interfaces são consideradas autorizadas.

O foco principal está na comunicação com o backend, onde as mensagens no formato JSON são trocadas. Vale ressaltar que o projeto não inclui a aplicação cliente, somente os componentes do servidor são avaliados.

---

## Como Executar o Projeto

Os seguintes passos descrevem como configurar e executar o projeto usando Docker:

**Pré-requisitos:** 
- Ter o [Docker](https://www.docker.com/products/docker-desktop) instalado em sua máquina. As instruções abaixo foram testadas usando Docker version 26.1.0.
- Docker Compose: As instruções utilizam sintaxe do compose file version 3.8.

### Opção 1: Sem Baixar o Repositório

1. **Crie o arquivo compose.yaml:**

    Crie um novo arquivo chamado `compose.yaml` no diretório desejado em sua máquina local e copie o conteúdo abaixo para este arquivo:
```yaml
services:
  database:
    image: "mysql:latest"
    container_name: assembleia_mysql_db
    environment:
      - MYSQL_DATABASE=${DB_NAME}
      - MYSQL_ROOT_PASSWORD=${DB_PASSWORD}
    ports:
      - "3307:3306"
    volumes:
      - db-mysql-assembleia:/var/lib/mysql

  assembleia-app:
    image: diegoneves/assembleia-vota:latest
    container_name: assembleia_vota_api
    ports:
      - "8080:8080"
    depends_on:
      - database
    environment:
      - DB_HOST=assembleia_mysql_db
      - DB_PORT=3306
    entrypoint: sh -c "dockerize -wait tcp://assembleia_mysql_db:3306 -timeout 60s && java -jar target/assembleia-vota.jar"

volumes:
  db-mysql-assembleia:
    
```

2. **Configure as Variáveis de Ambiente:**

   Crie um arquivo chamado `.env` no mesmo diretório do compose.yaml. Substitua os valores `DB_NAME` e `DB_PASSWORD` pelas suas informações do banco de dados:

```dotenv
DB_NAME=sua_base_de_dados
DB_PASSWORD=sua_senha
```

3. **Execute o Docker Compose:**

   Agora você pode iniciar os serviços com o seguinte comando:

```shell
docker compose up -d
```
---

### Opção 2: Baixando o Repositório

1. **Clone o repositório:**
```shell
git clone git@github.com:diegosneves/assembleia-vota.git
```

2. **Vá até o diretório do repositório**

3. **Configure as variáveis de ambiente**

    Edite o arquivo `.env`, substitua os valores `DB_NAME` e `DB_PASSWORD` pelas suas informações do banco de dados.
```dotenv
DB_USERNAME=root
DB_PASSWORD=local_password
DB_NAME=local_db
DB_PORT=3307
DB_HOST=localhost
```
4. **Execute o Docker Compose**

```shell
docker compose up -d
```

O serviço agora está rodando e pode ser acessado na porta `8080`.

> Lembre-se de substituir `"sua_base_de_dados"` e `"sua_senha"` com as informações reais do seu banco de dados. Além disso, é importante lembrar que **versões** diferentes do `Docker` e do `Docker Compose` podem ter diferentes sintaxes e comportamentos, portanto, garanta que você está usando a mesma versão para evitar problemas.

---
