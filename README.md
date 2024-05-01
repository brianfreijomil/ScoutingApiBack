Microservices Ports:

## Eureka : 8761

## Config Service : 8889

## API Gateway : 8080

## Player Service : 8090

## Calendar Service : 8091

## Recruitment-Stats Service : 8092

## User Service : 8093

## Team Service : 9090

## Kafka Service : 9092

## Keycloak Service : 8181

RECORDATORIOS :
* el metodo create de user deberia preguntar antes si existe el usuario por username
* respuesta de error 500 en create user
* preguntar si el usuario existe antes de realizar la accion? en update y delete de usuario
* borrado de scouter en microservicios player y calendar (critico)
* acoplar ms-team y ms-user (tiene poco sentido tanta estructura para la entidad Team, 
  * y conviene que este en el mismo lugar que usuarios para mapear los teamId y subscriptionStatus)