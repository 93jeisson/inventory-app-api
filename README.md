# 📦 Inventory App API

API REST para la gestión de inventario desarrollada con Spring Boot.
Incluye autenticación con JWT y manejo de usuarios, roles y productos.

---

## 🚀 Tecnologías utilizadas

* Java
* Spring Boot
* Spring Data JPA
* Spring Security + JWT
* Maven
* Base de datos relacional (MySQL)

---

## 🧱 Arquitectura del proyecto

El proyecto sigue una arquitectura en capas:

```
controller/   → Controladores REST
service/      → Lógica de negocio
repository/   → Acceso a datos (JPA)
model/        → Entidades (Producto, Usuario, Rol)
security/     → Configuración de seguridad y JWT
exception/    → Manejo de errores
```

### 📂 Detalle de módulos

* **controller**

  * `ProductoController`
  * `UsuarioController`

* **model**

  * `Producto`
  * `Usuario`
  * `Rol`

* **repository**

  * `ProductoRepository`
  * `UsuarioRepository`
  * `RolRepository`

* **security**

  * `JwtFilter`
  * `JwtUtil`
  * `SecurityConfigJWT`
  * `PasswordConfig`
  * `MethodSecurityConfig`

---

## 🔐 Seguridad

La API utiliza autenticación basada en **JWT (JSON Web Tokens)**:

* Login de usuario
* Generación de token
* Protección de endpoints
* Encriptación de contraseñas

---

## ⚙️ Configuración del proyecto

### 1. Clonar repositorio

```bash
git clone https://github.com/93jeisson/inventory-app-api.git
cd inventory-app-api
```

---

### 2. Configurar base de datos

Editar:

```
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

Servidor disponible en:

```
http://localhost:8080
```

---

## 🔗 Endpoints principales

### 📦 Productos

* GET `/api/products` → listar productos
* POST `/api/products` → crear producto
* PUT `/api/products/{id}` → actualizar producto
* DELETE `/api/products/{id}` → eliminar producto

### 👤 Usuarios

* Registro y gestión de usuarios
* Asignación de roles

---

## 🔑 Autenticación

Los endpoints protegidos requieren un token JWT:

```
Authorization: Bearer tu_token
```

---

## 🌐 Integración con frontend

Este backend está diseñado para ser consumido por un frontend en React.

https://github.com/93jeisson/inventory-app-frontend.git

Ejemplo de endpoint:

```
http://localhost:8080/api/products
```

---

## ⚠️ CORS

Permitir conexión desde el frontend:

```java
@CrossOrigin(origins = "http://localhost:3000")
```

---

## 📌 Estado del proyecto

🚧 En desarrollo

---

## 👨‍💻 Autor

Jeisson Rodriguez

---
