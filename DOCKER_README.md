# Docker Setup for Parcial Final N-Capas

This document explains how to run the Spring Boot application using Docker.

## Prerequisites

- Docker Desktop installed and running
- Docker Compose installed (usually comes with Docker Desktop)

## Quick Start

### 1. Build and Run with Docker Compose

```bash
# Build and start all services
docker-compose up --build

# Run in detached mode (background)
docker-compose up -d --build
```

### 2. Access the Application

- **Spring Boot Application**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **Health Check**: http://localhost:8081/actuator/health
- **PostgreSQL Database**: localhost:5432

### 3. Stop the Services

```bash
# Stop and remove containers
docker-compose down

# Stop and remove containers + volumes (WARNING: This will delete all data)
docker-compose down -v
```

## Manual Docker Commands

### Build the Application Image

```bash
docker build -t parcial-final-app .
```

### Run PostgreSQL Only

```bash
docker run -d \
  --name postgres \
  -e POSTGRES_DB=supportdb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=root \
  -p 5432:5432 \
  postgres:15-alpine
```

### Run the Application Container

```bash
docker run -d \
  --name app \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/supportdb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=root \
  parcial-final-app
```

## Environment Variables

The application uses the following environment variables:

- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_JPA_HIBERNATE_DDL_AUTO`: Hibernate DDL mode (update)
- `SPRING_JPA_SHOW_SQL`: Show SQL queries in logs (true)

## Database

- **Database**: PostgreSQL 15
- **Database Name**: supportdb
- **Username**: postgres
- **Password**: root
- **Port**: 5432

## Troubleshooting

### Check Container Logs

```bash
# View logs for all services
docker-compose logs

# View logs for specific service
docker-compose logs app
docker-compose logs postgres

# Follow logs in real-time
docker-compose logs -f app
```

### Check Container Status

```bash
# List running containers
docker ps

# List all containers (including stopped)
docker ps -a
```

### Access Container Shell

```bash
# Access application container
docker exec -it parcial-final-app sh

# Access database container
docker exec -it supportdb psql -U postgres -d supportdb
```

### Reset Everything

```bash
# Stop and remove everything
docker-compose down -v
docker system prune -f
docker volume prune -f

# Rebuild and start
docker-compose up --build
```

## Development Workflow

1. Make changes to your code
2. Rebuild the Docker image: `docker-compose build app`
3. Restart the application: `docker-compose up -d app`

## Production Considerations

For production deployment, consider:

1. Using environment-specific configuration files
2. Setting up proper logging
3. Configuring database connection pooling
4. Setting up monitoring and alerting
5. Using secrets management for sensitive data
6. Implementing proper backup strategies for the database 