#!/bin/bash

APP_NAME=spring-ai-chat

echo "Stopping and removing existing container (if any)..."
docker-compose down

echo "Cleaning and building JAR with Spring Boot Maven Plugin..."
mvn clean compile jib:dockerBuild

echo "Starting application with Docker Compose..."
docker-compose up -d

echo "Waiting for container logs (press Ctrl+C to exit)..."
docker logs -f $APP_NAME
