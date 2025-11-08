# Motosync API

API simples para gestão de motos e filiais, desenvolvida em Java Spring Boot e integrada a banco Oracle. O projeto foi pensado para implantação em Azure Web App com pipelines de CI/CD no Azure DevOps.

## Stack
- Java 17
- Spring Boot 3 (Web, Data JPA, Validation)
- Oracle Database (produção) / H2 (perfil local)
- Maven
- Front-end estático (HTML/JS) servido pelo próprio Spring Boot

## Pré-requisitos
- JDK 17+
- Maven 3.9+
- Banco Oracle acessível (ou Docker/OCI para testes)

## Configuração de Ambiente
### Variáveis para produção (Azure Web App)
Configure como Application Settings no Web App:

| Nome            | Exemplo              | Descrição                               |
|-----------------|----------------------|-----------------------------------------|
| `ORACLE_HOST`   | `oracle.fiap.com.br` | Host do banco Oracle                    |
| `ORACLE_PORT`   | `1521`               | Porta                                   |
| `ORACLE_SID`    | `ORCL`               | SID/Service Name                        |
| `ORACLE_USER`   | `rm558515`           | Usuário                                 |
| `ORACLE_PASS`   | `Fiap#2025`          | Senha                                   |
| `PORT`          | `8080`               | (Opcional) Porta do servidor Spring     |

A aplicação usa `application.properties` com placeholders dessas variáveis.

### Perfil local com H2
Para testar sem Oracle:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Isso habilita um banco H2 em memória (`/h2-console`) e recria o esquema a cada execução.

### Banco Oracle
O script de criação de tabelas está em `src/main/resources/db/schema-oracle.sql`. Execute-o uma vez no banco antes do primeiro deploy.

## Como Executar Localmente
```bash
mvn clean spring-boot:run
```

> Use `-Dspring-boot.run.profiles=dev` se quiser carregar o perfil com H2.

### Testes
```bash
mvn clean test
```

## Endpoints REST
| Método | Caminho          | Descrição                   |
|--------|------------------|-----------------------------|
| GET    | `/api/filiais`   | Lista filiais               |
| POST   | `/api/filiais`   | Cria filial                 |
| GET    | `/api/filiais/{id}` | Detalha filial          |
| PUT    | `/api/filiais/{id}` | Atualiza filial         |
| DELETE | `/api/filiais/{id}` | Remove filial           |
| GET    | `/api/usuarios`  | Lista usuários              |
| POST   | `/api/usuarios`  | Cria usuário                |
| GET    | `/api/usuarios/{id}` | Detalha usuário       |
| PUT    | `/api/usuarios/{id}` | Atualiza usuário      |
| DELETE | `/api/usuarios/{id}` | Remove usuário        |
| GET    | `/api/motos`     | Lista motos                 |
| POST   | `/api/motos`     | Cria moto                   |
| GET    | `/api/motos/{id}` | Detalha moto              |
| PUT    | `/api/motos/{id}` | Atualiza moto             |
| DELETE | `/api/motos/{id}` | Remove moto               |

O front-end estático está em `/index.html` e consome esses endpoints para demonstrar o CRUD completo.

## Estrutura do Projeto
```
src/
 ├─ main/
 │   ├─ java/com/motosync/api
 │   │   ├─ controller     -> REST controllers e tratamento de erros
 │   │   ├─ dto            -> DTOs de request/response
 │   │   ├─ model          -> Entidades JPA
 │   │   ├─ repository     -> Repositórios Spring Data
 │   │   └─ service        -> Regras de negócio
 │   └─ resources
 │       ├─ application.properties
 │       ├─ application-dev.properties
 │       ├─ static/index.html (front simples)
 │       └─ db/schema-oracle.sql
 └─ test/java/com/motosync/api -> testes básicos
```

## Deploy no Azure Web App (resumo)
1. Publicar o repositório no GitHub (`https://github.com/bispado/motosync-api`).
2. Criar pipeline de CI no Azure DevOps (Classic ou YAML) com etapas:
   - Checkout do GitHub
   - `mvn -B clean verify`
   - Publicar artefato `target/motosync-api-0.0.1-SNAPSHOT.jar`
3. Criar pipeline de CD apontando para o Web App (Java 17):
   - Baixar artefato do build
   - Deploy com tarefa *Azure Web App for Java*
4. Configurar Application Settings com as variáveis Oracle.
5. Garantir acesso do professor: Azure DevOps (Project `Sprint 4 – Azure DevOps`) e Web App (Função Reader).

## Vídeo de Demonstração (checklist)
- Mostrar IDE com o código e README.md
- Mostrar GitHub e Azure DevOps (pipelines CI/CD)
- Mostrar Azure Portal: Web App + Azure SQL/Oracle
- Executar alteração simples (ex.: README) -> push -> pipeline disparando
- Mostrar logs, artefatos e deploy concluído
- Acessar URL pública do Web App, abrir `/index.html` e executar CRUD completo
- Mostrar registros refletidos no banco

## Boas Práticas
- Nunca versionar senhas no repositório (usar variáveis de ambiente)
- Proteger a string de conexão no Azure DevOps (variáveis secretas)
- Adicionar monitoramento e logs estruturados no Azure App Insights (opcional)

---

Feito com 💙 para a Sprint 4 da FIAP.

