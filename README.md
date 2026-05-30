# 🛒 Direct Purchase API

REST API built with Java and Spring Boot for direct purchase order management.
Focused on user control, products, suppliers, profiles and JWT authentication.

## 🚀 Technologies

- **Java 17**
- **Spring Boot 3.4.1**
- **Spring Security + JWT**
- **Spring Data JPA**
- **PostgreSQL**
- **Redis**
- **Swagger/OpenAPI**
- **Flyway (migrations)**
- **Apache POI / JXLS / FastExcel (Excel files)**
- **Lombok**

## ⚙️ Features

- 🔐 JWT Authentication
- 📦 Order, product, user, supplier and buyer management
- 👥 Role-based access control (Admin, User, etc)
- 🧾 Excel data export and import
- 🧠 Secure token with custom claims
- 📊 Interactive API documentation with Swagger

## 🧩 Main Modules

| Module                | Description                                          |
|-----------------------|------------------------------------------------------|
| **AuthService**       | Login with JWT and token generation                  |
| **UsuarioService**    | User registration and data retrieval                 |
| **PedidoService**     | Order creation linked to logged-in user              |
| **ProdutoService**    | Product search by description and general listing    |
| **FornecedorService** | Supplier listing available to the user               |
| **CompradorService**  | Associated buyer visualization                       |
| **PerfilService**     | Profiles accessible based on logged-in user role     |

## 🧪 Tests

- Configured with `spring-boot-starter-test`
- Structure ready for unit and integration tests

## 🔐 Security

- JWT with `JwtFilter` for request validation and authentication
- `SecurityConfig` with public and private route control
- Stateless with `SessionCreationPolicy.STATELESS`

## 📄 API Documentation

Available after starting the application:

```
http://localhost:7777/directpurchase/swagger-ui.html
```

## 🐳 Running with Docker

```bash
# Build image
docker build -t direct-purchase-api .

# Run
docker run -p 8080:7777 direct-purchase-api
```

Make sure PostgreSQL and Redis are running and accessible.

## 📁 Project Structure

```
src/main/java/br/com/directpurchase
├── auth              # Authentication, payloads, login services
├── config            # Security filters and Spring Security config
├── dao / repository  # Data repositories
├── dto / entity      # DTOs and database entities
├── service           # Business rules
├── transform         # Entity to DTO conversion
├── request / response # API input/output models
```

## 📦 Dependencies

Check `pom.xml` for the full list. Main ones:

```
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