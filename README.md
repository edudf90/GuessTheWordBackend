# Guess the word

This is the backend for a word guessing game I made as an exercise using Spring boot and the Spring microservices environment. 
The project is composed by two microservices: One responsible for word generation and one for managing the game.
I wanted to exercise some microservice design patterns like service discovery and circuitbreakers in Spring.


# Service discovery 

This project uses Eureka for service discovery. 
I created a Spring boot project that uses the eureka-server dependency and configured the server to route the calls to the microservices instances (which are configured to use the eureka-client dependency).
As a result of using service discovery, I don't have to worry about configuring ip addresses and ports in every place that makes a call to a microservice instance served by the discovery server. 
Thus, service discovery makes it easier to add, remove instances of microservices and also to change the hardware in which the instance is running.


# Word microservice

A simple microservice that returns one randomly chosen word from a Postgres database.


# Game management microservice

This microservice offers two main API calls to manage a word guessing game.
One of the calls starts a new game, storing a new game state in a user session managed by Redis.
The other API call this microservice offers is to handle a guess. It receives a letter, processes the guess and returns the new game state.
The game state consists of the number of tries left, the streak of correctly guessed words, the letters already tried for the current word and the current representation of the word the player have to guess.
When the microservice needs to request a new word (when the user correctly guesses a word or starts a new game), it makes a request to the word microservice.


# Circuit breaker

For any reason, the word microservice may not return a fast reply when the game management microservice calls. 
If this happens, we don't want the game management service to wait for a slow response or even worse, having the request denied after waiting.
To avoid this case, I've added a circuit breaker to call the word microservice, and after a number of calls timeout the circuit breaker stops calling the service and instead calls a fallback method for a while, giving time to the word microservice to recover.
To implement this I've added the Resilience4J dependency in the game management spring boot project, configured the circuit breaker and wrote the fallback method to be called.


# Session management with Redis

The game state is stored with Redis, an in memory key-value storage. For every session, Redis stores the current game state and current word to guess.
Storing the session in a Redis database makes so the session information is not lost if we shut down the service instance. 
Also, if we have multiple instances of the microservice running, having a separated session storage means that all instances will be able to read and write in the same storage. 


# Docker containers

In this project I used two docker containers: The Postgres database container; And the Redis storage container.


# Points to improve

Word selection: For simplicity, the algorithm I'm using for random word selection is assuming that the ids in the database are sequential and without any gaps between them (there's a fallback word that is returned in case of gaps). Perhaps a better way of doing this would be to mantain the list of ids in cache and select one of them at random to select the word from the database, this way we would still avoid loading all of the ids from the database everytime we need to choose one word and deal better with the gaps. 
