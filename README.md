# Parcial Final Programación N-Capas – (Seguridad con Spring Security + JWT)

Este repositorio contiene un proyecto para evaluar y practicar los conceptos de seguridad en aplicaciones Spring Boot usando JWT, roles y Docker.

### Estudiantes
- **Nombre del estudiante 1**: Jaime Samuel Portan - 00046119
- **Nombre del estudiante 2**: Daniel Alfredo Quesada Cortez - 00147619
- Sección: 1
---

## Sistema de Soporte Técnico

### Descripción
Simula un sistema donde los usuarios pueden crear solicitudes de soporte (tickets) y los técnicos pueden gestionarlas. Actualmente **no tiene seguridad implementada**.

Su tarea es **agregar autenticación y autorización** utilizando **Spring Security + JWT**, y contenerizar la aplicación con Docker.

### Requisitos generales

- Proyecto funcional al ser clonado y ejecutado con Docker.
- Uso de PostgreSQL (ya incluido en docker-compose).
- Seguridad implementada con JWT.
- Roles `USER` y `TECH`.
- Acceso restringido según el rol del usuario.
- Evidencia de funcionamiento (colección de Postman/Insomnia/Bruno o capturas de pantalla).

**Nota: El proyecto ya tiene una estructura básica de Spring Boot con endpoints funcionales para manejar tickets. No es necesario modificar la lógica de negocio, solo agregar seguridad. Ademas se inclye un postman collection para probar los endpoints. **

_Si van a crear mas endpoints como el login o registrarse recuerden actualizar postman/insomnia/bruno collection_

### Partes de desarrollo

#### Parte 1: Implementar login con JWT
- [ ] Crear endpoint `/auth/login`.
- [ ] Validar usuario y contraseña (puede estar en memoria o en BD).
- [ ] Retornar JWT firmado.

#### Parte 2: Configurar filtros y validación del token
- [ ] Crear filtro para validar el token en cada solicitud.
- [ ] Extraer usuario desde el JWT.
- [ ] Añadir a contexto de seguridad de Spring.

#### Parte 3: Proteger endpoints con Spring Security
- [ ] Permitir solo el acceso al login sin token.
- [ ] Proteger todos los demás endpoints.
- [ ] Manejar errores de autorización adecuadamente.

#### Parte 4: Aplicar roles a los endpoints

| Rol   | Acceso permitido                                 |
|--------|--------------------------------------------------|
| USER  | Crear tickets, ver solo sus tickets              |
| TECH  | Ver todos los tickets, actualizar estado         |

- [ ] Usar `@PreAuthorize` o reglas en el `SecurityFilterChain`.
- [ ] Validar que un USER solo vea sus tickets.
- [ ] Validar que solo un TECH pueda modificar tickets.

#### Parte 5: Agregar Docker
- [ ] `Dockerfile` funcional para la aplicación.
- [ ] `docker-compose.yml` que levante la app y la base de datos.
- [ ] Documentar cómo levantar el entorno (`docker compose up`).

#### Parte 6: Evidencia de pruebas
- [ ] Probar todos los flujos con Postman/Insomnia/Bruno.
- [ ] Mostrar que los roles se comportan correctamente.
- [ ] Incluir usuarios de prueba (`user`, `tech`) y contraseñas.

---

## USO DE API

> **Nota:** El backend corre en `http://localhost:8081` por defecto.
> Ya existe un usuario TECH para comenzar a usar la API:
> - **Correo:** admin@example.com
> - **Contraseña:** admin123
> 
> Para instalar y correr la imagen Docker, lee el archivo `DOCKER_README.md`.

### 1. Login (obtener access_token y refresh_token)

```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "admin@example.com",
    "password": "admin123"
  }'
```

---

### 2. Registrar un usuario (requiere token de TECH)

```bash
curl -X POST http://localhost:8081/api/users/register \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_ACCESS_TOKEN" \
  -d '{
    "nombre": "Samuel Portan",
    "correo": "samuel.user@example.com",
    "password": "hola!123",
    "nombreRol": "USER"
  }'
```

---

### 3. Crear un ticket (solo USER)

```bash
curl -X POST http://localhost:8081/api/tickets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_ACCESS_TOKEN_USER" \
  -d '{
    "titulo": "No funciona mi PC",
    "descripcion": "La computadora no enciende",
    "correoUsuario": "samuel.user@example.com",
    "correoSoporte": "admin@example.com"
  }'
```

---

### 4. Ver todos los tickets (solo TECH)

```bash
curl -X GET http://localhost:8081/api/tickets \
  -H "Authorization: Bearer TU_ACCESS_TOKEN"
```

---

### 5. Ver tus propios tickets (solo USER)

```bash
curl -X GET http://localhost:8081/api/tickets/my-tickets \
  -H "Authorization: Bearer TU_ACCESS_TOKEN_USER"
```

---

### 6. Actualizar un ticket (solo TECH)

```bash
curl -X PUT http://localhost:8081/api/tickets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_ACCESS_TOKEN" \
  -d '{
    "id": 1,
    "descripcion": "Ya revisé el equipo, falta cambiar la fuente.",
    "estado": "IN_PROGRESS",
    "correoSoporte": "admin@example.com"
  }'
```

> Para cerrar un ticket, usa `"estado": "CLOSED"`.

---

### 7. Eliminar un ticket (solo TECH)

```bash
curl -X DELETE http://localhost:8081/api/tickets/1 \
  -H "Authorization: Bearer TU_ACCESS_TOKEN"
```

---

### 8. Refrescar el token

```bash
curl -X POST http://localhost:8081/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "TU_REFRESH_TOKEN"
  }'
```

---

### 9. Ver todos los usuarios (requiere token)

```bash
curl -X GET http://localhost:8081/api/users/all \
  -H "Authorization: Bearer TU_ACCESS_TOKEN"
```

---

### 10. Ver usuario por correo (requiere token)

```bash
curl -X GET http://localhost:8081/api/users/admin@example.com \
  -H "Authorization: Bearer TU_ACCESS_TOKEN"
```

---

> **Importante:**
> - Todos los endpoints (excepto login y refresh) requieren un token válido en el header `Authorization`.
> - El campo `estado` de los tickets debe ser exactamente `IN_PROGRESS` o `CLOSED` para cambiar el estado.
> - El usuario admin ya existe para que puedas comenzar a probar la API.
> - Para más detalles sobre la instalación y uso con Docker, revisa el archivo `DOCKER_README.md`.

