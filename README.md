# Sistema de Gerenciamento de Posto de Combustível

Sistema desktop desenvolvido em Java para gerenciar operações de um posto de combustível, incluindo controle de tipos de combustível, bombas e abastecimentos.

## Sobre o Projeto

Aplicação desktop com interface gráfica (Swing) básica que permite o gerenciamento de um posto de combustível, desde o cadastro de tipos de combustível até o registro de abastecimentos realizados.

##  Estrutura do Banco

O sistema utiliza MySQL como banco de dados.

### Configuração do Banco de Dados

1. Certifique-se de ter o MySQL instalado e rodando
2. Execute o script SQL localizado em `/database/posto.sql`
3. O script criará automaticamente o banco `posto_db` e suas tabelas

```bash
mysql -u root -p < database/posto.sql
```

## Tecnologias Utilizadas

- **Java** - Linguagem de programação
- **Maven** - Gerenciamento de dependências
- **MySQL** - Banco de dados relacional
- **Java Swing** - Interface gráfica desktop


### Pré-requisitos

- JDK 8 ou superior
- Maven 3.x
- MySQL 8.0 ou superior

### Configuração da Conexão com Banco de Dados

Edite o arquivo `src/main/java/com/jlucacariolato/dao/DatabaseConnection.java` com suas credenciais:

```java
private static final String URL = "jdbc:mysql://localhost:3306/posto_db";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

## Instalação e Execução

1. Clone o repositório:
```bash
git clone https://github.com/jeancariolato/desafio-vaga-junior.git
```

2. Configure o banco de dados executando o script SQL:
```bash
mysql -u root -p < database/posto.sql
```
ou execute o Script dentro do SGDB de sua preferência.

3. Compile o projeto:
```bash
mvn clean compile
```

4. Execute a aplicação:

## Funcionalidades

### Gerenciamento de Tipos de Combustível
- **Criar**: Cadastrar novos tipos de combustível com nome e preço por litro
- **Visualizar**: Listar todos os tipos cadastrados com ID, nome e preço
- **Atualizar**: Modificar informações de tipos existentes
- **Deletar**: Remover tipos de combustível do sistema

### Gerenciamento de Bombas
- **Criar**: Cadastrar novas bombas associadas a um tipo de combustível
- **Visualizar**: Listar todas as bombas com seus respectivos combustíveis
- **Atualizar**: Modificar informações das bombas
- **Deletar**: Remover bombas do sistema

### Gerenciamento de Abastecimentos
- **Criar**: Registrar novos abastecimentos com:
  - Bomba utilizada
  - Data e hora
  - Quantidade abastecida (litros)
  - Preço do combustível
  - Valor total pago
- **Visualizar**: Listar todos os abastecimentos com informações formatadas
- **Atualizar**: Modificar registros de abastecimentos
- **Deletar**: Remover registros de abastecimentos

### Interface Gráfica
- Menu organizado por categorias (Tipos de Combustível, Bombas, Abastecimentos)
- Área de exibição com dados formatados em tabela
- Diálogos modais para operações de CRUD

## Arquitetura

O projeto segue uma arquitetura em camadas:

- **Model**: Entidades que representam as tabelas do banco
- **DAOs**: Responsável pela comunicação com o banco de dados
- **Service**: Camada de lógica de negócio para execução de DAOs
- **View**: Interface gráfica com o usuário (Swing)

## Autor

**Jean Luca Cariolato**

## Licença

Este projeto é de uso educacional.
