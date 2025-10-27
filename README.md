# App Golden Raspberry Awards

O objetivo do app é obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois
prêmios mais rápido

## Configuração

- Java 17
- Spring boot 3.5.7
- Banco de dados H2
- Maven

## Verificações antes de rodar o app

### Verificar se o java está instalado

```sh
java -version
```

Para windows baixe:

- https://www.azul.com/downloads/?version=java-17-lts&package=jdk#zulu
  ou
- https://adoptium.net/pt-BR/temurin/releases?version=17&os=any&arch=any

Para linux instala conforme a sua distribuição

### (Opcional) Instalar o maven

Baixar e instalar: https://maven.apache.org/install.html

```sh
# Ver se o maven está acessível
mvn -v
```

## Como executar

```sh
# No windows
# Na pasta do projeto
# Abra o powershell e execute o comando abaixo
.\mvnw.cmd spring-boot:run

# No linux
# Apenas uma vez de liberdade de execução
# Na pasta do projeto execute
chmod +x mvnw

# Depois execute o comando abaixo para rodar o teste
./mvnw spring-boot:run

# Ou se você instalou o maven (serve para windows e linux)
mvn spring-boot:run

# Para executar o jar
# Compile o app
mvn clean package

# Execute
java -jar target/golden-raspberry-awards-0.0.1-SNAPSHOT.jar
```

## Como executar o teste de integração

```sh
# No windows
# Na pasta do projeto
# Abra o powershell e execute o comando abaixo
.\mvnw.cmd verify

# No linux
# Apenas uma vez de liberdade de execução
# Na pasta do projeto execute
chmod +x mvnw

# Depois execute o comando abaixo para rodar o teste
./mvnw verify

#Ou se você instalou o maven (Serve para windows e linux)
mvn verify
```

## Como chamar a API

```sh
# No windows o powerShell já acessa o curl
# No linux depende da distro já vem com curl, senão basta instalar
# Basta rodar o comando abaixo
curl -X GET http://localhost:8080/api/awards/intervals

```

- Pode abrir no navegador também
- http://localhost:8080/api/awards/intervals

## Resultado esperado para o csv atual no repositório

Retorno esperado

```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}
```
