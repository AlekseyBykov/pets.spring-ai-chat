# Spring LLM Chat

A simple Spring Boot pet project that integrates with a local LLM via **OLLaMA**.
It sends a text prompt and receives a response from a model like **Mistral** running locally.

Tech Stack:
- Java 21
- Spring Boot 3
- Jib (no Dockerfile needed)
- Ollama + Mistral (or other local models)
- Swagger/OpenAPI

## Quick Start
### 1. Clone the project

```shell
git clone https://github.com/AlekseyBykov/pets.spring-ai-chat.git
cd pets.spring-ai-chat
```

### 2. Install OLLaMA
Follow the official instructions: https://ollama.com/download
Then verify it's available from the command line:
```shell
which ollama
```
If not found, add the directory to your `PATH` (e.g., `~/.ollama/bin` or `/usr/local/bin`).

### 3. Start the OLLaMA server (locally)

```shell
OLLAMA_HOST=0.0.0.0 ollama serve
```
This is important: by default, OLLaMA listens on 127.0.0.1, which is not reachable from Docker containers.

### 4. Pull a model
```shell
ollama pull mistral
```
This will only work if `ollama serve` is running. Make sure it's available:

```shell
curl http://localhost:11434/api/tags
```

Expected response:
```json
{"models":[{"name":"mistral", ... }]}
```

### 5. Configure application

Edit `src/main/resources/application.yml`:
```yaml
ollama:
  base-url: http://host.docker.internal:11434
  model: mistral
```
On Linux, `host.docker.internal` works with Docker Desktop or some extra setup. 
Otherwise, use your `docker0` bridge IP, like `http://172.17.0.1:11434`.

### 6. Build and run

The project has a ready script:

```shell
./build-docker.sh
```
This script:
- Stops the container if already running
- Builds the image using [Jib](https://github.com/GoogleContainerTools/jib)
- Runs the container via Docker Compose
- Follows logs

## Swagger UI

Open in browser:
```
http://localhost:8080/swagger-ui/index.html
```
Available endpoint: `POST /ai/chat`

Sample request:
```
{
    "prompt": "Who you are?"
}
```

## Troubleshooting

### Model not pulled / Ollama server not responding

You might see an error like this:
```shell
Error: ollama server not responding - could not connect to ollama server, run 'ollama serve' to start it`
```
This means the Ollama server is not running. Start it manually in your terminal:
```shell
OLLAMA_HOST=0.0.0.0 ollama serve
```
Note: this must be running before you pull a model or start the Spring Boot service.

### Connection refused
Check:
- Is OLLaMA running?
- Is it listening on `0.0.0.0:11434` and not `127.0.0.1`?
- Is the model pulled (`ollama pull mistral`)?
- Is base-url correct?

### Port 11434 already in use
If OLLaMA is already running as a background/system process, stop it cleanly:

```bash
pkill -f "ollama"
```
Then start it again with:

```bash
OLLAMA_HOST=0.0.0.0 ollama serve
```

### Swagger not accessible externally

**Check**:
- VPN may block localhost access
- Switching to a home or different network may help
- Ensure container is exposing the port:
```shell
docker ps
```
- Try testing from inside the container:
```shell
docker exec -it spring-ai-chat sh
curl http://localhost:8080/swagger-ui/index.html
```
### Cleanup
```shell
docker-compose down
docker rmi spring-ai-chat
```
