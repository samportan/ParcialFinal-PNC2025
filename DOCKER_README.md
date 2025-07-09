# Configuración de Docker

## Requisitos 

* Docker Desktop instalado y en ejecución
* Docker Compose instalado (generalmente viene con Docker Desktop)

### 1. Construir y Ejecutar con Docker Compose

```bash
# Construir e iniciar todos los servicios
docker-compose up --build
```

### 2. Acceder a la Aplicación

* **Aplicación Spring Boot**: [http://localhost:8081](http://localhost:8081)
* **Base de datos PostgreSQL**: localhost:5432

### 3. Detener los Servicios

```bash
# Detener y eliminar contenedores
docker-compose down

# Detener y eliminar contenedores + volúmenes (ADVERTENCIA: Esto eliminará todos los datos)
docker-compose down -v
```

### Construir la Imagen de la Aplicación

```bash
docker build -t parcial-final-app .
```

## Variables de Entorno

La aplicación utiliza las siguientes variables de entorno:

* `SPRING_DATASOURCE_URL`: URL de conexión a la base de datos
* `SPRING_DATASOURCE_USERNAME`: Usuario de la base de datos
* `SPRING_DATASOURCE_PASSWORD`: Contraseña de la base de datos
* `SPRING_JPA_HIBERNATE_DDL_AUTO`: Modo DDL de Hibernate (por ejemplo, `update`)
* `SPRING_JPA_SHOW_SQL`: Mostrar consultas SQL en los logs (`true`)

## Base de Datos

* **Base de datos**: PostgreSQL 15
* **Nombre de la base de datos**: supportdb
* **Usuario**: postgres
* **Contraseña**: root
* **Puerto**: 5432


## Flujo de Trabajo en Desarrollo

1. Realiza cambios en tu código
2. Reconstruye la imagen de Docker: `docker-compose build app`
3. Reinicia la aplicación: `docker-compose up -d app`
