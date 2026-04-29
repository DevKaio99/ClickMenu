# 🍽️ ClickMenu API

Sistema backend desenvolvido com **Spring Boot** para gestão de restaurantes e interação com clientes, permitindo cadastro de usuários, autenticação, avaliações e operações essenciais.

---

## 📌 Sobre o Projeto

Um grupo de restaurantes decidiu se unir para desenvolver um sistema único de gestão, reduzindo custos com soluções individuais.

O objetivo é criar uma plataforma onde:

- Restaurantes possam gerenciar suas operações
- Clientes possam consultar informações, avaliar e realizar pedidos

O desenvolvimento foi dividido em fases para garantir evolução contínua, qualidade e possibilidade de melhorias com base no uso real.

---

## 🎯 Objetivo

Desenvolver um backend robusto utilizando:

- Spring Boot
- Boas práticas de arquitetura (MVC)
- Princípios SOLID
- Orientação a Objetos

---

### 👤 Usuários

O sistema possui dois tipos obrigatórios:

- Dono de restaurante
- Cliente

### 📋 Funcionalidades

- ✅ Cadastro de usuários  
- ✅ Atualização de dados do usuário  
- ✅ Exclusão de usuários  
- ✅ Troca de senha (endpoint separado)  
- ✅ Registro da data da última alteração  
- ✅ Busca de usuários por nome  
- ✅ Validação de login (autenticação)  
- ✅ Garantia de e-mail único  

---

## 🔐 Autenticação e Segurança

- Implementação de autenticação via **JWT**
- Validação de login com email e senha
- Proteção de endpoints com Spring Security
- Filtro customizado para validação de token

---

## 🧱 Arquitetura
controller/
service/
repository/
entity/
dto/
security/
exception/
mapper/
config/

O projeto segue o padrão **MVC com separação de camadas**:



### 📌 Boas práticas aplicadas:

- Separação de responsabilidades (SRP)
- Uso de DTOs para entrada e saída
- Camada de serviço para regras de negócio
- Mapeamento com classes dedicadas (Mapper)
- Tratamento global de exceções

---

## ⚙️ Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (Auth0)
- Swagger / OpenAPI
- Docker & Docker Compose
- Banco de dados relacional (PostgreSQL)

---

## 🗄️ Banco de Dados

- Executado via container Docker
- Configurado no `docker-compose.yml`

---

## 📦 Docker

A aplicação é totalmente dockerizada.


## 📚 Documentação da API

A API é documentada com **Swagger/OpenAPI**.

### Contém:
- Endpoints detalhados  
- Exemplos de requisições  
- Respostas de sucesso e erro  

---

## ❗ Tratamento de Erros

A aplicação segue o padrão:

- **Problem Details (RFC 7807)**  

Garantindo respostas padronizadas e claras.

---

## 🧪 Testes (Postman)

O projeto inclui uma collection do Postman com os principais cenários:

- Cadastro válido de usuário  
- Cadastro inválido (email duplicado, campos faltando)  
- Alteração de senha com sucesso e erro  
- Atualização de dados do usuário  
- Busca de usuários pelo nome  
- Validação de login  

---

## 📌 Estrutura do Usuário

Campos obrigatórios:

- Nome  
- Email (único)   
- Senha  
- Data da última alteração  
- Endereço  

---

## 📈 Qualidade do Código

- Código organizado e modular  
- Aplicação dos princípios SOLID  
- Fácil manutenção e escalabilidade  
- Separação clara entre camadas  

---

## 🚀 Como rodar o projeto ClickMenu

Siga o passo a passo abaixo para executar a aplicação localmente utilizando Docker.

📦 Pré-requisitos

Antes de começar, você precisa ter instalado:

- Docker
- Docker Compose
- Git

 


### 📥 Clonando o repositório

Clone o projeto usando o comando:

git clone https://github.com/DevKaio99/ClickMenu.git

Acesse a pasta do projeto:

cd ClickMenu

### ⚙️ Configuração das variáveis de ambiente

O projeto utiliza variáveis de ambiente para configurar o banco de dados e segurança.

1. Crie um arquivo .env na raiz do projeto
2. Copie o conteúdo do arquivo .env.example alterando as credenciais desejadas ou utilize o modelo abaixo:
- DB_NAME=clickmenu
- DB_USER=seu_usuario
- DB_PASSWORD=sua_senha
- JWT_SECRET=sua_chave_secreta_aqui


 
### 🐳 Subindo a aplicação com Docker

Execute o comando abaixo na raiz do projeto:

docker compose up --build

Esse comando irá:

- Construir a imagem da aplicação
- Baixar a imagem do PostgreSQL
- Criar e iniciar os containers

### 🌐 Acessando a aplicação

Após subir os containers, a API estará disponível em:

http://localhost:8080

---

## 👨‍💻 Autor

Desenvolvido por **Kaio Andrade**
