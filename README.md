# 🍽️ ClickMenu API — Tech Challenge Fase 2

Sistema backend desenvolvido com **Spring Boot** para gestão de restaurantes, permitindo o cadastro de tipos de usuário, usuários, restaurantes e itens de cardápio, com autenticação via JWT.

---

## 📌 Sobre o Projeto

Um grupo de restaurantes decidiu se unir para desenvolver um sistema único de gestão, reduzindo custos com soluções individuais.

Esta é a **Fase 2** do desafio, que expande o sistema construído na Fase 1 ao incluir:

- Gestão dos **tipos de usuário** (agora uma entidade com CRUD completo, e não mais um valor fixo)
- Cadastro de **restaurantes**
- Cadastro de **itens de cardápio**

reforçando práticas de desenvolvimento, testes automatizados e estruturação de código limpo.

---

## 🎯 Objetivo

Desenvolver um backend robusto utilizando:

- Spring Boot
- **Clean Architecture** (Domain, Application, Infrastructure)
- **TDD (Test Driven Development)** — entidades e use cases foram escritos a partir dos testes
- Princípios SOLID e Orientação a Objetos

---

## 🧱 Arquitetura

Diferente da Fase 1 (que seguia o padrão MVC clássico), esta fase foi reescrita em **Clean Architecture**, com três camadas bem isoladas:

```
domain/
├── entities/       # Classes puras, sem anotação de framework. Validam invariantes no construtor.
├── enums/          # Enums de apoio (TipoCozinhaRestaurante, DiasDaSemana)
└── repositories/   # Interfaces — o domínio define o contrato de persistência

application/
├── usecases/<recurso>/   # Um use case por operação (Criar, Listar, BuscarPorId, Atualizar, Deletar)
└── exceptions/           # BusinessException (400) e ResourceNotFoundException (404)

infrastructure/
├── controllers/    # @RestController — injeção de use cases por construtor
├── dtos/<Recurso>/ # Records com Bean Validation (CreateDTO, UpdateDTO, ResponseDTO)
├── mappers/        # DTO ⇄ entidade, e ResultSet ⇄ entidade (RowMapper)
├── persistence/    # @Repository implementando a interface do domínio via JdbcTemplate
├── config/         # UseCaseConfig (beans dos use cases), InicializacaoDados (seed), Swagger
├── handlers/        # GlobalExceptionHandler — respostas padronizadas (ProblemDetail)
└── security/       # SecurityConfiguration, SecurityFilter, TokenService, CustomUserDetailsService
```

### 📌 Boas práticas aplicadas

- Separação de responsabilidades entre domínio, regras de negócio e infraestrutura
- Injeção de dependência por construtor em toda a aplicação
- Entidades de domínio sem qualquer dependência de framework
- Um use case por operação, com método público único `executar(...)`
- DTOs de entrada/saída dedicados por recurso, com Bean Validation
- Tratamento global de exceções com respostas no padrão **Problem Details (RFC 7807)**

---

## 🧪 Metodologia de desenvolvimento (TDD)

Os recursos desta fase (entidades, use cases e mappers) foram desenvolvidos a partir do **TDD**: os testes unitários foram escritos antes/junto da implementação, guiando o design das classes de domínio e de aplicação. Isso resultou em:

- Entidades que validam suas próprias invariantes (lançando `IllegalArgumentException` no construtor)
- Use cases pequenos, testáveis isoladamente via Mockito
- Cobertura de testes consistente em todas as camadas (unitária, mapeamento, controller e integração)

---

## 👤 Tipos de Usuário

O sistema distingue usuários por um **tipo cadastrável** (entidade `TipoUsuario`, com CRUD próprio), ao invés de um valor fixo. Os tipos seedados na inicialização são:

- `ADMIN`
- `CLIENTE`
- `DONO_RESTAURANTE`

Um `Usuario` referencia um `TipoUsuario` por chave estrangeira (`tipo_id`). Novos tipos podem ser criados livremente via API.

---

## 📋 Funcionalidades

- ✅ CRUD completo de Tipo de Usuário
- ✅ CRUD completo de Usuário (associado a um Tipo de Usuário)
- ✅ CRUD completo de Restaurante (associado a um Usuário do tipo `DONO_RESTAURANTE`)
- ✅ CRUD completo de Item de Cardápio (associado a um Restaurante)
- ✅ Troca de senha de usuário (endpoint separado)
- ✅ Autenticação via login (email e senha)
- ✅ Garantia de e-mail único e nome de tipo único
- ✅ Regra de negócio: usuário com restaurante(s) cadastrado(s) não pode ser excluído

---

## 🔐 Autenticação e Segurança

- Autenticação via **JWT** (`com.auth0:java-jwt`)
- Login realizado em `POST /api/v1/auth/login` (único endpoint de negócio público, além do Swagger)
- Todos os demais endpoints exigem o header `Authorization: Bearer <token>`
- Filtro customizado (`SecurityFilter`) validando o token a cada requisição
- Sessão stateless (`SessionCreationPolicy.STATELESS`)

---

## ⚙️ Tecnologias utilizadas

- Java 26
- Spring Boot 4.0.6
- Spring JDBC (`JdbcTemplate`) — **sem JPA/Hibernate**
- Spring Security + JWT (Auth0)
- Spring Validation (Bean Validation)
- Swagger / OpenAPI (springdoc-openapi 3.0.2)
- JUnit 5 + Mockito
- JaCoCo (cobertura de testes)
- Docker & Docker Compose
- H2 (banco em memória, usado em desenvolvimento/testes) e PostgreSQL (usado via Docker Compose)

---

## 🗄️ Banco de Dados

- Por padrão a aplicação sobe com **H2 em memória** (`application.properties`), útil para rodar/testar localmente sem dependências externas
- Ao subir via **Docker Compose**, a aplicação utiliza **PostgreSQL** (container próprio)
- Schema definido em `src/main/resources/schema.sql`, com a tabela `tipo_usuario` criada antes da `usuario` (FK `usuario.tipo_id → tipo_usuario.id`)
- `InicializacaoDados` (`CommandLineRunner`) garante os tipos `ADMIN`/`CLIENTE`/`DONO_RESTAURANTE` e um usuário admin (`admin@email.com` / `fiap123`) na subida da aplicação

---

## 📦 Docker

A aplicação é totalmente dockerizada com um **Dockerfile multi-stage**:

- **Estágio de build**: imagem JDK, compila o projeto com o Maven Wrapper e gera o `.jar`
- **Estágio final**: imagem JRE (menor), copia apenas o `.jar` gerado

O `docker-compose.yml` sobe dois serviços: a aplicação (`app`) e o banco de dados (`db`, PostgreSQL).

---

## 📚 Documentação da API

A API é documentada com **Swagger/OpenAPI**, disponível em:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

Contém, para cada endpoint:
- Descrição da operação (`@Operation`)
- Códigos de resposta possíveis com descrição (`@ApiResponses`) — sucesso, validação (400) e recurso não encontrado (404)

---

## ❗ Tratamento de Erros

A aplicação segue o padrão **Problem Details (RFC 7807)** (`ProblemDetail`), com um `@RestControllerAdvice` (`GlobalExceptionHandler`) centralizando o tratamento de:

| Exceção                          | Status |
|-----------------------------------|--------|
| `ResourceNotFoundException`       | 404    |
| `BusinessException`               | 400    |
| `MethodArgumentNotValidException` | 400    |
| `NoResourceFoundException`        | 404    |
| Demais exceções não tratadas      | 500    |

---

## 🌐 Endpoints

### Autenticação
| Método | Rota                    | Descrição                     | Acesso  |
|--------|-------------------------|--------------------------------|---------|
| POST   | `/api/v1/auth/login`    | Autentica e retorna um token JWT | Público |

### Tipo de Usuário
| Método | Rota                          | Descrição                          |
|--------|-------------------------------|-------------------------------------|
| POST   | `/api/v1/tipos-usuario`       | Cria um tipo de usuário             |
| GET    | `/api/v1/tipos-usuario`       | Lista todos os tipos de usuário     |
| GET    | `/api/v1/tipos-usuario/{id}`  | Busca um tipo de usuário por ID     |
| PUT    | `/api/v1/tipos-usuario/{id}`  | Atualiza um tipo de usuário         |
| DELETE | `/api/v1/tipos-usuario/{id}`  | Deleta um tipo de usuário           |

### Usuário
| Método | Rota                            | Descrição                              |
|--------|----------------------------------|------------------------------------------|
| POST   | `/api/v1/usuarios`               | Cria um usuário                         |
| GET    | `/api/v1/usuarios`               | Lista todos os usuários                 |
| GET    | `/api/v1/usuarios/{id}`          | Busca um usuário por ID                 |
| PUT    | `/api/v1/usuarios/{id}`          | Atualiza dados de um usuário            |
| PUT    | `/api/v1/usuarios/{id}/senha`    | Atualiza a senha de um usuário          |
| DELETE | `/api/v1/usuarios/{id}`          | Deleta um usuário (sem restaurante)     |

### Restaurante
| Método | Rota                          | Descrição                          |
|--------|-------------------------------|-------------------------------------|
| POST   | `/api/v1/restaurantes`        | Cria um restaurante                 |
| GET    | `/api/v1/restaurantes`        | Lista todos os restaurantes         |
| GET    | `/api/v1/restaurantes/{id}`   | Busca um restaurante por ID         |
| PUT    | `/api/v1/restaurantes/{id}`   | Atualiza um restaurante             |
| DELETE | `/api/v1/restaurantes/{id}`   | Deleta um restaurante               |

### Item de Cardápio
| Método | Rota                            | Descrição                          |
|--------|----------------------------------|-------------------------------------|
| POST   | `/api/v1/item-cardapio`         | Cria um item de cardápio            |
| GET    | `/api/v1/item-cardapio`         | Lista todos os itens de cardápio    |
| GET    | `/api/v1/item-cardapio/{id}`    | Busca um item de cardápio por ID    |
| PUT    | `/api/v1/item-cardapio/{id}`    | Atualiza um item de cardápio        |
| DELETE | `/api/v1/item-cardapio/{id}`    | Deleta um item de cardápio          |

---

## 📌 Estrutura das Entidades

### Tipo de Usuário
- Nome do tipo (único)

### Usuário
- Nome
- Email (único)
- Senha
- Data da última alteração
- Tipo de usuário (associação obrigatória)

### Restaurante
- Nome
- Endereço
- Tipo de cozinha
- Horário de abertura e fechamento
- Dias de funcionamento
- Dono do restaurante (usuário do tipo `DONO_RESTAURANTE`)

### Item de Cardápio
- Nome
- Descrição
- Preço
- Disponibilidade apenas para consumo no restaurante
- Caminho da foto do prato
- Restaurante ao qual pertence

---

## 🧪 Testes

O projeto conta com **testes unitários e de integração** em todas as camadas:

- **Unitários** (JUnit 5 + Mockito): entidades, use cases, mappers, controllers (`@WebMvcTest` + `@MockitoBean`) e classes de segurança
- **Integração** (sufixo `IT`, `@SpringBootTest` + Failsafe): use cases ponta a ponta contra o banco H2

Cobertura medida com **JaCoCo**: **84% de instrução**, acima dos 80% exigidos pelo desafio.

Execução:
```bash
./mvnw test      # somente testes unitários
./mvnw verify    # unitários + integração (gera relatório JaCoCo em target/site/jacoco)
```

---

## 🧪 Testes (Postman)

O projeto inclui uma collection do Postman (`postman/collections/ClickMenu-Fase2.postman_collection.json`) cobrindo o fluxo completo da API:

- Login com o usuário admin do seed (captura o token automaticamente)
- CRUD de Tipo de Usuário, Usuário, Restaurante e Item de Cardápio, encadeados (cada criação alimenta a próxima requisição)
- Exclusões na ordem correta, respeitando as regras de negócio (item → restaurante → usuário → tipo)

---

## 📈 Qualidade do Código

- Código organizado em Clean Architecture, com camadas isoladas e testáveis
- Aplicação dos princípios SOLID
- Regras de negócio centralizadas nos use cases, sem lógica no controller
- Nomenclatura consistente em português (`executar`, `buscarPorId`, `deveCriarQuando...`)

---

## 🚀 Como rodar o projeto ClickMenu

Siga o passo a passo abaixo para executar a aplicação localmente utilizando Docker.

### 📦 Pré-requisitos

Antes de começar, você precisa ter instalado:

- Docker
- Docker Compose
- Git

### 📥 Clonando o repositório

```bash
git clone https://github.com/DevKaio99/ClickMenu.git
cd ClickMenu
```

### ⚙️ Configuração das variáveis de ambiente

O projeto utiliza variáveis de ambiente para configurar o banco de dados e a segurança. Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo (ajustando os valores):

```
DB_NAME=clickmenu
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta_aqui
```

### 🐳 Subindo a aplicação com Docker

Execute o comando abaixo na raiz do projeto:

```bash
docker compose up --build
```

Esse comando irá:

- Construir a imagem da aplicação (multi-stage)
- Baixar a imagem do PostgreSQL
- Criar e iniciar os containers

### 🌐 Acessando a aplicação

Após subir os containers, a API estará disponível em:

```
http://localhost:8080
```

E a documentação Swagger em:

```
http://localhost:8080/swagger-ui.html
```

---

## 👨‍💻 Autor

Desenvolvido por **Kaio Andrade**
