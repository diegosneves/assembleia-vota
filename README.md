# Assembleia Vota

[![Linkedin badge](https://img.shields.io/badge/-Linkedin-blue?flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/diego-neves-224208177/)](https://www.linkedin.com/in/diego-neves-224208177/) [![CI Prod](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-prod.yaml/badge.svg)](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-prod.yaml) [![CI Develop](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-develop.yaml/badge.svg)](https://github.com/diegosneves/assembleia-vota/actions/workflows/ci-develop.yaml)
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

## Arquitetura do Projeto

Escolhemos utilizar a arquitetura Model-View-Controller (MVC) para estruturar nosso projeto. Aqui estão algumas razões para esta escolha:

1. **Separação de Concerns**: A arquitetura MVC nos permite dividir a aplicação em três partes distintas - Model, View e Controller. Isso facilita o gerenciamento e a manutenção do código.

2. **Desenvolvimento Paralelo**: Dividindo a aplicação em partes distintas, diferentes desenvolvedores podem trabalhar em diferentes partes da aplicação simultaneamente sem interferência.

3. **Reutilização de Código & Modulação**: MVC permite um alto grau de reutilização de código e modularidade. Os componentes são facilmente intercambiáveis e reutilizáveis.

Neste projeto:

- **Model**: Representa as classes de domínio, as regras de negócios, lógica e funções. Aqui temos as classes que representam as Pautas e Votos.

- **View**: Como estamos trabalhando com uma API REST, aqui seria onde formatamos a resposta do servidor para JSON.

- **Controller**: Controla a relação entre a Model e a View. Ele recebe os inputs do usuário, processa a requisição e retorna a resposta para o cliente.

Ao adotar a arquitetura MVC, esperamos construir uma aplicação organizada, responsiva e de fácil manutenção.

---

## Swagger

- [Swagger - Local](http://localhost:8080/swagger-ui/index.html)

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

