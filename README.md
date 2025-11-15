# Spring Security Demo

Proyecto de demostración de seguridad de aplicaciones con Spring Security, JWT, y autenticación/autorización completa.

## 📋 Descripción

Este proyecto es una demostración completa de seguridad de aplicaciones con Spring Security 6.x, implementando autenticación basada en JWT (JSON Web Tokens), autorización basada en roles, y mejores prácticas de seguridad. Actualizado a Spring Boot 3.3.0 con Java 21.

## 🚀 Características

- ✅ Autenticación JWT (JSON Web Tokens)
- ✅ Autorización basada en roles (RBAC)
- ✅ Spring Security 6.x con SecurityFilterChain
- ✅ Password encoding con BCrypt
- ✅ CORS configurable y seguro
- ✅ Validación de input con Bean Validation
- ✅ Security headers configurados
- ✅ Spring Boot 3.3.0 con Java 21
- ✅ Tests unitarios con JUnit 5 y cobertura > 70%
- ✅ Operaciones CRUD para Rooms, Speakers, Talks
- ✅ Integración con MySQL y Flyway para migraciones

## 📋 Requisitos

- **Java**: JDK 21 o superior
- **Gradle**: 8.9+ (incluido wrapper)
- **MySQL**: 8.0+ (opcional, H2 disponible para desarrollo)
- **Docker**: (opcional, para MySQL)

## ⚙️ Configuración

### Variables de Entorno

```properties
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=yourpassword

# JWT
JWT_SECRET=your-secret-key-change-in-production-min-256-bits-required
JWT_EXPIRATION=3600000

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:4200,http://localhost:3000
CORS_ALLOWED_METHODS=GET,POST,PUT,DELETE,OPTIONS
CORS_ALLOWED_HEADERS=*
CORS_ALLOW_CREDENTIALS=true
```

### application.properties

```properties
spring.application.name=demo-service

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Flyway
spring.flyway.locations=classpath:db-migration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# JWT Configuration
jwt.secret=${JWT_SECRET:your-secret-key-change-in-production-min-256-bits}
jwt.expiration=3600000

# CORS Configuration
cors.allowed-origins=${CORS_ALLOWED_ORIGINS:http://localhost:4200,http://localhost:3000}
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed-headers=*
cors.allow-credentials=true
```

### Base de Datos MySQL

#### Con Docker

```bash
# Ejecutar contenedor MySQL
docker run -d \
  --name mysql-demo \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=demo \
  -p 3306:3306 \
  mysql:8.0

# Conectar a MySQL
docker exec -it mysql-demo mysql -uroot -p123456
```

#### Crear Esquema

```sql
CREATE DATABASE IF NOT EXISTS demo;
USE demo;
```

Las migraciones de Flyway se ejecutarán automáticamente al iniciar la aplicación.

## 🏃 Ejecución

### Con Gradle Wrapper

```bash
# Compilar
./gradlew clean build

# Ejecutar
./gradlew bootRun

# O ejecutar JAR
java -jar build/libs/springsecuritydemo-0.0.1-SNAPSHOT.jar
```

### Con Profile Específico

```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

## 🔐 Seguridad

### Autenticación JWT

El proyecto implementa autenticación basada en JWT:

1. **Login**: `POST /login` con `username` y `password`
2. **Token**: Se genera un JWT token con validez configurable
3. **Autenticación**: El token se envía en header `Authorization: Bearer <token>`

**Ejemplo de Login:**

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Uso del Token:**

```bash
curl -X GET http://localhost:8080/test \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### Mejoras de Seguridad Implementadas

1. **Password Encoding**: BCryptPasswordEncoder (fuerza 12)
2. **JWT Secret**: Configurable desde properties (mínimo 256 bits)
3. **CORS**: Configurable, no wildcard (`origins: "*"`)
4. **Security Headers**: X-Content-Type-Options, X-Frame-Options, X-XSS-Protection
5. **Input Validation**: Bean Validation en todos los endpoints
6. **Error Handling**: Manejo seguro de errores sin exponer información sensible

### Configuración de Seguridad

- **SecurityFilterChain**: Reemplaza deprecated `WebSecurityConfigurerAdapter`
- **CORS**: Configuración centralizada en `WebSecurity`
- **Password Encoder**: BCrypt con fuerza 12 (recomendado para producción)
- **JWT**: Secret key desde properties con validación de longitud

## 📡 API Endpoints

### Autenticación

#### POST /login

Autentica usuario y genera JWT token.

**Request:**
```json
{
  "username": "testuser",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Response (401 UNAUTHORIZED):**
```json
{
  "token": null,
  "message": "Invalid username or password"
}
```

### Endpoints Protegidos

Todos los endpoints excepto `/login` y `/database/populate` requieren autenticación JWT.

#### GET /test

Endpoint de prueba protegido.

**Headers:**
```
Authorization: Bearer <token>
```

**Response:**
```
test
```

### Rooms

#### GET /rooms

Obtiene todas las salas (requiere autenticación).

**Response:**
```json
[
  {
    "id": 1,
    "number": 12
  }
]
```

#### POST /rooms

Crea una nueva sala (requiere autenticación).

**Request:**
```json
{
  "number": 15
}
```

### Speakers

#### GET /speakers

Obtiene todos los speakers (requiere autenticación).

**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe"
  }
]
```

### Talks

#### GET /talks

Obtiene información de talks (requiere autenticación).

**Response:**
```
Hola LUXO
```

### Database

#### POST /database/populate

Pobla la base de datos con datos de ejemplo (público, solo para desarrollo).

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
./gradlew test

# Tests con cobertura
./gradlew test jacocoTestReport

# Verificar cobertura mínima (70%)
./gradlew test jacocoTestCoverageVerification

# Ver reporte HTML
open build/reports/jacoco/test/html/index.html
```

### Cobertura de Código

- **Umbral mínimo**: 70%
- **Herramienta**: JaCoCo 0.8.11
- **Exclusiones**: Application class, Config classes, DTOs, Mappers

### Tests Implementados

1. **UserControllerTest**: Tests completos del controller de autenticación
2. **JwtServiceTest**: Tests del servicio JWT
3. **WebSecurityTest**: Integration tests de configuración de seguridad
4. **DemoApplicationTests**: Context loading test

## 📦 Dependencias Principales

- **Spring Boot**: 3.3.0
- **Spring Security**: 6.x (gestionado por Spring Boot)
- **JWT**: io.jsonwebtoken:jjwt 0.12.5
- **Database**: MySQL Connector 8.3.0, H2 2.2.224 (tests)
- **Flyway**: 10.11.0
- **Lombok**: 1.18.30
- **MapStruct**: 1.5.5.Final
- **Testing**: JUnit 5, Mockito 5.11.0, JaCoCo 0.8.11

## 🔒 Seguridad - Mejores Prácticas

### JWT Secret

**IMPORTANTE**: En producción, usa un secret key fuerte:

```bash
# Generar secret key seguro (256 bits = 32 bytes = 64 caracteres hex)
openssl rand -hex 32
```

Configurar en variables de entorno:
```bash
export JWT_SECRET=tu-secret-key-muy-largo-y-seguro-de-al-menos-32-caracteres
```

### Password Storage

Las contraseñas deben almacenarse hasheadas con BCrypt en la base de datos:

```java
// Ejemplo de hash de contraseña
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
String hashedPassword = encoder.encode("rawPassword");
// Guardar hashedPassword en base de datos
```

### CORS en Producción

Actualizar `application.properties` o variables de entorno:

```properties
cors.allowed-origins=https://production-domain.com,https://www.production-domain.com
```

**NUNCA** usar `origins: "*"` en producción.

### Security Headers

Los siguientes headers están configurados:
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`

## 🔄 Migración de Spring Boot 2.x a 3.x

Este proyecto ha sido migrado de Spring Boot 2.5.2 a 3.3.0. Principales cambios:

### Cambios Principales

1. **javax.servlet** → **jakarta.servlet**
   - Todos los imports actualizados
   - `javax.servlet.*` → `jakarta.servlet.*`

2. **WebSecurityConfigurerAdapter** → **SecurityFilterChain**
   - Clase deprecada reemplazada por `@Bean SecurityFilterChain`
   - Configuración funcional con lambdas

3. **NoOpPasswordEncoder** → **BCryptPasswordEncoder**
   - Eliminado encoder inseguro
   - Implementado BCrypt con fuerza 12

4. **JWT API** → **JJWT 0.12.x**
   - `setSigningKey()` → `verifyWith(SecretKey)`
   - `SignatureAlgorithm.HS512` → `Jwts.SIG.HS512`
   - Uso de `SecretKey` en lugar de String

5. **CORS** → **Configuración centralizada**
   - Eliminado `@CrossOrigin(origins = "*")` de controllers
   - CORS configurado en `WebSecurity`

## 🛠️ Desarrollo

### Agregar Nuevo Endpoint Protegido

```java
@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceController {
    
    @GetMapping
    public ResponseEntity<List<Resource>> getResources() {
        // Requiere autenticación JWT automáticamente
        return ResponseEntity.ok(resources);
    }
}
```

### Agregar Nuevo Rol

1. Actualizar `MyUserDetails.getAuthorities()` para incluir el nuevo rol
2. O implementar carga de roles desde base de datos
3. Usar `@PreAuthorize("hasRole('ROLE_NAME')")` en endpoints

### Configurar Nuevo Usuario

```java
// En MyUserDetailService, cargar desde base de datos
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
    return User.builder()
        .username(user.getUsername())
        .password(user.getPassword()) // Ya hasheado con BCrypt
        .authorities(user.getRoles())
        .build();
}
```

## 🔧 Troubleshooting

### Error: JWT secret too short

```
IllegalArgumentException: JWT secret must be at least 256 bits (32 characters)
```

**Solución**: Configurar `JWT_SECRET` con al menos 32 caracteres:

```bash
export JWT_SECRET=your-very-long-secret-key-at-least-32-characters
```

### Error: javax.servlet cannot be resolved

**Solución**: Actualizar imports a `jakarta.servlet.*`

### Error: WebSecurityConfigurerAdapter not found

**Solución**: Usar `SecurityFilterChain` en lugar de extender `WebSecurityConfigurerAdapter`

### Error: CORS blocked

**Solución**: Verificar configuración en `application.properties`:
```properties
cors.allowed-origins=http://localhost:4200
```

### Error: Password encoding failed

**Solución**: Asegurar que passwords estén hasheados con BCrypt antes de guardar en BD

## 📚 Documentación Adicional

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Boot Security](https://spring.io/guides/topicals/spring-security-architecture)
- [JWT.io](https://jwt.io/) - Para entender y debuggear JWT tokens
- [OWASP Security Guidelines](https://owasp.org/www-project-top-ten/)

## 📝 Estructura del Proyecto

```
springsecuritydemo/
├── src/
│   ├── main/
│   │   ├── java/com/sample/springsecurity/demo/
│   │   │   ├── DemoApplication.java
│   │   │   ├── security/
│   │   │   │   ├── WebSecurity.java          # Security configuration
│   │   │   │   ├── JwtService.java           # JWT token service
│   │   │   │   ├── JwtAuthorizationFilter.java # JWT filter
│   │   │   │   └── ...
│   │   │   ├── web/controller/
│   │   │   │   ├── UserController.java       # Authentication endpoints
│   │   │   │   ├── RoomController.java
│   │   │   │   └── ...
│   │   │   ├── service/
│   │   │   │   ├── MyUserDetailService.java
│   │   │   │   └── ...
│   │   │   └── ...
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db-migration/
│   │           └── V1__init_tables.sql
│   └── test/
│       └── java/com/sample/springsecurity/demo/
│           ├── DemoApplicationTests.java
│           ├── security/
│           │   ├── JwtServiceTest.java
│           │   └── WebSecurityTest.java
│           └── web/controller/
│               └── UserControllerTest.java
├── build.gradle
├── settings.gradle
├── gradle/wrapper/
└── README.md
```

## 🔄 Flujo de Autenticación

1. **Cliente** envía credenciales a `/login`
2. **AuthenticationManager** valida credenciales
3. **MyUserDetailService** carga detalles del usuario
4. **JwtService** genera token JWT
5. **Cliente** recibe token en respuesta
6. **Cliente** envía token en header `Authorization: Bearer <token>`
7. **JwtAuthorizationFilter** intercepta request y valida token
8. **SecurityContext** se establece con usuario autenticado
9. **Endpoint** procesa request con usuario autenticado

## 📞 Soporte

Para reportar issues o hacer preguntas:
1. Abre un issue en el repositorio
2. Revisa la documentación de Spring Security
3. Consulta la documentación principal: [../README.md](../README.md)

## 🔒 Mejoras de Seguridad Implementadas

Ver documentación detallada en [SECURITY_IMPROVEMENTS.md](../SECURITY_IMPROVEMENTS.md)

### Resumen de Mejoras

1. ✅ Eliminado `NoOpPasswordEncoder` (inseguro)
2. ✅ Implementado `BCryptPasswordEncoder`
3. ✅ CORS configurable (no wildcard)
4. ✅ JWT secret desde properties (no hardcoded)
5. ✅ Validación de input en todos los endpoints
6. ✅ Security headers configurados
7. ✅ Manejo de errores seguro
8. ✅ Logging seguro (sin información sensible)
9. ✅ Migración completa a Spring Security 6.x
10. ✅ Tests unitarios con JUnit 5 (> 70% cobertura)

## 📅 Versionado

- **Versión**: 0.0.1-SNAPSHOT
- **Spring Boot**: 3.3.0
- **Java**: 21
- **Última actualización**: Enero 2025

---

**Nota de Seguridad**: Este es un proyecto de demostración. Para producción, asegúrate de:
- Usar un secret key fuerte y seguro
- Almacenar passwords hasheados en base de datos
- Configurar CORS con orígenes específicos
- Implementar rate limiting
- Usar HTTPS en producción
- Implementar refresh tokens
- Agregar logging de auditoría
