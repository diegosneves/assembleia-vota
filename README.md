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

