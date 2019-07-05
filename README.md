# Musiclimate

Get Music tracks suggestion based on the climate at your given location

Miscroservice environment built to provide scalable APIs to suit your needs

## Dependencies

* Maven v3.5.4
* Docker Engine v18.09.2
* Docker Compose v1.23.2

## Development Environment Breakdown

The environment setup follows a traditional microservice architecrure, using Spring Boot and the support it provides to Netflix microservice and cloud libraries which are

 * Eureka
 * Hystrix
 * Zuul
 * Ribbon

The main service called musiclimate-server is where the application lives, it defines endpoints to retrieve the data defined in the test description (detailed later), configuration to communicate with the external APIs and business logic

To support the main server we have the services using the technologies mentioned above

* eureka-server - Eureka is used as a service discovery system, where the microservices will register themselves and fetch registries from. This makes it possible for multiple instances of the microservice to be available for discovery in a single point of contact
* zuul-server - Zuul is used as a gateway for exposing the environment to external access. Zuul will proxy incoming calls and route them to the respective microservice following predefined rules. Using Ribbon under the hood, Zuul will fetch registries from Eureka and load balance requests to the available instances of the microservices
* redis-server - Redis is used as an in memory cache, it will store relevant results following strategies that will avoid load on the external APIs and improve response time of incoming requests

All support services are scalable in a way that is not explored in this example, but is evidence to the powerful tools they are in a microservice architecture

## Getting Started

After cloning the repository the first step is to build the application. It is built using Maven parent-child structure so you can build all services with one command

```
mvn clean install
```

Alternatively, you can skip running the tests, which is faster

```
mvn clean install -DskipTests
```

The environment is setup by defining Dockerfiles to create images of each deployable service. On top of that, Docker Compose is used to build, manage and deploy all required containers to have a functional environmnent  

You can achieve this by running

```
docker-compose up --build -d
```

The --build flag will tell Docker compose to build the images, even if it is already created. The -d flag is to assure the process will be run in the background (detached)

Like mentioned above, the environment is setup in a scalable fashion, if you want additional instances of the microservice running, to improve fault tolerance and response time, you can achieve this by running

```
docker-compose up --scale musiclimate=3 --build -d
```

In the above example, the environment will be created as before, but this time with 3 instances of the musiclimate-server microservice, all of them accessible through the same gateway, which will load balance requests and handle system failures

After running docker-compose, be patient, the command will create all docker containers and run the applications inside them, it may take a while for the environment to be fully functional. After initiating, the microservices have to register to the Eureka server to make themselves available, after that, there can be a while until Zuul gateway retrieve the updated list of available instances. But after some time verything should be stable.

When done, you can wrap it all up by running

```
docker-compose down
```

## Usage and Examples

Once the environment is up and running you can retrieve data using the following endpoints (the gateway is open under localhost:8765)

Retrieving playlists base on city name

```
http://localhost:8765/musiclimate/playlist/city/{cityname}
```

Retrieving playlists base on location coordinates

```
http://localhost:8765/musiclimate/playlist/coordinates?lat={latitude}&lon={longitude}
```

Examples:

```
http://localhost:8765/musiclimate/playlist/city/Campinas
```
```
http://localhost:8765/musiclimate/playlist/city/Tokyo
```
```
http://localhost:8765/musiclimate/playlist/city/Moscow
```
```
http://localhost:8765/musiclimate/playlist/city/Bali
```
```
http://localhost:8765/musiclimate/playlist/coordinates?lat=-22.90&lon=-47.06
```
```
http://localhost:8765/musiclimate/playlist/coordinates?lat=-8.40&lon=115.18
```