# Sistema de Logística e Armazenamento

Backend para gestão de operações de um armazém: controle de entrada, saída e retrabalho de cargas, com rastreamento por QR code e vínculo com clientes.

## Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

## Sobre o projeto

Em um armazém, cada carga que chega ou sai precisa ser registrada manualmente, o que gera atraso no operacional e risco de erro humano.
Este sistema centraliza esse controle, agilizando o processo em entrada de cargas, saídas e retrabalhos, permitindo buscar e localizar cargas de forma mais rápida.
Também conta com validações de QR code para diminuir erro humano e uma lista de serviços com status para melhor controle operacional.

## Funcionalidades

- Cadastro e consulta de clientes
- Registro de operações diárias (entrada, saída, retrabalho)
- Controle de cargas por cliente e nota fiscal
- Rastreamento de carga por QR code único
- Fluxo de retrabalho com identificação automática de saída no mesmo dia

Em desenvolvimento:

- Autenticação e controle de acesso por perfil
- Interface web (frontend em React/TypeScript)
- Leitura de QR code via navegador mobile

## Arquitetura

O projeto segue uma separação em camadas tradicional do ecossistema Spring:

```
model      -> entidades JPA e regras de negócio que pertencem ao próprio domínio
repository -> interfaces Spring Data JPA, acesso ao banco
service    -> orquestração de regras de negócio entre entidades
controller -> exposição de endpoints REST
validator  -> validações de estado e regras de negócio isoladas
console    -> interface via terminal, usada na fase inicial de testes
```

Algumas decisões tomadas ao longo do desenvolvimento:

**UUID como identificador em `Carga`, em vez de ID sequencial.**
Evita colisão de identificadores em cenários de múltiplas origens de dados (ex.: uma futura sincronização offline/mobile) e não expõe informação de negócio, como a quantidade total de registros, através do ID -- diferente do que aconteceria com um ID sequencial.

**Regras de negócio dentro da própria entidade `Carga`.**
Métodos como `registrarSaida()` e `registrarRetrabalho()` vivem na entidade, não apenas na camada de Service. Isso garante que a entidade proteja seus próprios estados inválidos -- por exemplo, impedir duas saídas registradas para a mesma carga -- independente de qual parte do sistema está chamando esse método.

**`Saida` e `Retrabalho` como `record`s `@Embeddable`, não entidades próprias.**
Representam dados imutáveis que só existem no contexto de uma `Carga` -- não têm identidade nem ciclo de vida independente, então não fazem sentido como tabela própria no banco.

## Como rodar localmente

Pré-requisitos: Java 21 e PostgreSQL instalados.

```
git clone https://github.com/Pimentel131/logistica-armazenamento.git
```
```
cd logistica-armazenamento
```

Configure o acesso ao banco em `src/main/resources/application.properties`.

```
./mvnw spring-boot:run
```

## Roadmap

- Cobertura de testes com JUnit
- Autenticação JWT e controle de acesso por perfil
- Deploy em produção
- Frontend em React/TypeScript
- Leitura de QR code via câmera do navegador mobile

## Autor

Gabriel Pimentel Teixeira

[LinkedIn](https://www.linkedin.com/in/pimentel131/)
[GitHub](https://github.com/Pimentel131)
