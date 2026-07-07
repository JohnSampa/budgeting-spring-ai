# Budgeting Spring AI

API REST para controle simples de transacoes financeiras com recursos de IA generativa: registro e consulta de gastos por categoria, transcricao de audio, respostas em texto e sintese de voz usando Spring AI e OpenAI.

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring AI](https://img.shields.io/badge/Spring_AI-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

---

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring AI
- OpenAI Chat
- OpenAI Whisper para transcricao de audio
- OpenAI Text-to-Speech
- MySQL
- Docker Compose
- Lombok
- Maven

---

## Como rodar localmente

### Pre-requisitos

- Java 21
- Docker e Docker Compose
- Chave de API da OpenAI

### Configuracao

Clone o repositorio:

```bash
git clone https://github.com/JohnSampa/budgeting-spring-ai.git
cd budgeting-spring-ai
```

Defina a variavel de ambiente `OPEN_AI_API_KEY`:

```bash
export OPEN_AI_API_KEY=sua_chave_openai
```

No Windows PowerShell:

```powershell
$env:OPEN_AI_API_KEY="sua_chave_openai"
```

Se estiver rodando pela IDE, configure a mesma variavel no run configuration da aplicacao.

Tambem e possivel manter um `.env` local como referencia:

```env
OPEN_AI_API_KEY=sua_chave_openai
```

Suba o banco MySQL:

```bash
docker compose up -d
```

Execute a aplicacao:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
./mvnw.cmd spring-boot:run
```

A API estara disponivel em:

```text
http://localhost:8080
```

---

## Banco de dados

O projeto utiliza MySQL via Docker Compose.

Configuracao padrao do `compose.yml`:

```text
Host: localhost
Porta: 3307
Database: transaction
Usuario: app
Senha: app
```

O Hibernate esta configurado com `ddl-auto=update`, entao as tabelas sao criadas/atualizadas automaticamente ao iniciar a aplicacao.

---

## Configuracao de IA

A aplicacao usa Spring AI com OpenAI para:

- Chat via `ChatClient`
- Chat direto via `OpenAiChatModel`
- Transcricao de audio com Whisper
- Sintese de voz em MP3
- Tool calling para registrar e consultar transacoes financeiras

Modelos configurados em `application.properties`:

```properties
spring.ai.openai.chat.options.model=gpt-4o-mini
spring.ai.openai.audio.transcription.options.model=whisper-1
spring.ai.model.audio.speech.options.model=gpt-4o-mini-tts
```

---

## Endpoints

### Transacoes

| Metodo | Rota                     | Descricao                                      |
|--------|--------------------------|------------------------------------------------|
| POST   | /transactions            | Registra uma nova transacao                    |
| GET    | /transactions/{category} | Lista transacoes por categoria                 |
| POST   | /transactions/ai         | Recebe audio, interpreta o pedido e retorna MP3 |

Categorias disponiveis:

```text
GROCERIES
PHARMA
AUTO
```

### Criar transacao

```http
POST /transactions
Content-Type: application/json

{
  "description": "Compra no mercado",
  "category": "GROCERIES",
  "amount": 15000
}
```

Resposta:

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "category": "GROCERIES",
  "description": "Compra no mercado",
  "amount": 15000.0
}
```

### Listar transacoes por categoria

```http
GET /transactions/GROCERIES
```

Resposta:

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "category": "GROCERIES",
    "description": "Compra no mercado",
    "amount": 15000.0
  }
]
```

### Fluxo com IA por audio

```http
POST /transactions/ai
Content-Type: multipart/form-data
```

Campo do formulario:

```text
file: arquivo de audio
```

Esse endpoint:

1. Recebe um arquivo de audio.
2. Transcreve o conteudo usando OpenAI Whisper.
3. Envia a mensagem transcrita para o `ChatClient`.
4. Usa tools do Spring AI para registrar ou consultar transacoes.
5. Retorna a resposta sintetizada em audio MP3.

---

## Endpoints de IA

### Chat com ChatClient

```http
GET /api/chat?prompt=Liste meus gastos de mercado
```

### Chat direto com OpenAiChatModel

```http
GET /api/chat-model?prompt=Explique como organizar meus gastos
```

### Transcricao de audio

```http
POST /api/transcription
Content-Type: multipart/form-data
```

Campo do formulario:

```text
file: arquivo de audio
```

### Sintese de voz

```http
POST /api/synthesize
Content-Type: application/json
```

```json
{
  "text": "Transacao registrada com sucesso."
}
```

A resposta e um arquivo `audio.mp3`.

---

## Decisoes tecnicas

**Arquitetura em camadas**
O projeto separa dominio, aplicacao e infraestrutura. As regras de uso ficam nos use cases, enquanto controllers e repositorios ficam isolados na camada de infraestrutura.

**UUID como identificador de transacao**
As transacoes usam UUID como identificador, evitando IDs sequenciais expostos pela API.

**Spring AI Tool Calling**
Os use cases de persistencia e listagem de transacoes sao expostos como tools para o modelo de IA, permitindo que comandos em linguagem natural acionem operacoes da aplicacao.

**Entrada por audio**
O endpoint `/transactions/ai` permite registrar ou consultar gastos a partir de audio, combinando transcricao, interpretacao por IA, execucao de tools e resposta em voz.

**Persistencia com JPA**
As transacoes sao persistidas em MySQL usando Spring Data JPA. O repositorio de dominio e adaptado para JPA na camada de infraestrutura.

**Docker Compose para desenvolvimento**
O banco MySQL roda via `compose.yml`, simplificando o ambiente local de desenvolvimento.

---

## Contato

Jonathan Sampaio - [aurijona192@gmail.com](mailto:aurijona192@gmail.com)
