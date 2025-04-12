# 🛒 Direct Purchase API

API REST em Java com Spring Boot para gerenciamento de pedidos diretos. Focada em controle de usuários, produtos, fornecedores, perfis e autenticação JWT. 

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.1**
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Redis
- Swagger/OpenAPI
- Flyway (migrations)
- Apache POI / JXLS / FastExcel (para arquivos Excel)
- Lombok

## ⚙️ Funcionalidades

- 🔐 Autenticação via JWT
- 📦 Cadastro e gestão de pedidos, produtos, usuários, fornecedores e compradores
- 👥 Controle de acesso por perfis (Admin, User, etc)
- 🧾 Exportação e leitura de dados em Excel
- 🧠 Token seguro com claims customizadas
- 📊 Documentação interativa com Swagger

## 🧩 Principais Módulos

| Módulo      | Descrição |
|-------------|-----------|
| **AuthService**        | Login com JWT e geração de tokens |
| **UsuarioService**     | Cadastro e recuperação de dados do usuário |
| **PedidoService**      | Criação de pedidos vinculados ao usuário logado |
| **ProdutoService**     | Busca por descrição e listagem geral de produtos |
| **FornecedorService**  | Listagem de fornecedores disponíveis para o usuário |
| **CompradorService**   | Visualização de compradores associados |
| **PerfilService**      | Perfis acessíveis com base no perfil logado |

## 🧪 Testes

- Configurado com `spring-boot-starter-test`
- Estrutura preparada para testes de unidade e integração

## 🔐 Segurança

- JWT com `JwtFilter` para validação e autenticação de requisições
- Configurações via `SecurityConfig` com controle de rotas públicas e privadas
- Stateless com `SessionCreationPolicy.STATELESS`

## 📄 Documentação da API

Disponível após subir a aplicação:


## 🐳 Rodando com Docker

```bash
# Build da imagem
docker build -t direct-purchase-api .

# Executando
docker run -p 8080:8080 direct-purchase-api
```

Certifique-se de que o PostgreSQL e o Redis estão rodando e acessíveis da aplicação.

📁 Estrutura do Projeto
```
src/main/java/br/com/directpurchase
├── auth              # Autenticação, payloads, serviços de login
├── config            # Filtros de segurança e configuração Spring Security
├── dao / repository  # Repositórios de dados
├── dto / entity      # DTOs e entidades de banco
├── service           # Regras de negócio
├── transform         # Conversão entre entidades e DTOs
├── request / response # Modelos para entrada/saída de dados da API
```

📦 Dependências

Verifique o arquivo pom.xml para uma lista completa. Algumas principais:

``
spring-boot-starter-web

spring-boot-starter-security

spring-boot-starter-data-jpa

jjwt (JWT)

springdoc-openapi

postgresql

flyway-core

lombok

poi, jxls, fastexcel

```