# Stacker-sample
***Stacker sample application***

----

## Description

Stacker sample application is the first [Stacker](https://github.com/krieven/Stacker) based application.
It can be used as reference and demonstrates how to use Stacker 
to build scalability applications.

## The application consists of 
- [Client](./client/README.md) 
- [Router](./stacker-sample-router/README.md)
- Workflow components
  - [basket](./flow-basket/README.md)
  - [pack](./flow-pack/README.md)
  - [catalog](./flow-catalog/README.md)
- [project common library](./sample-common/README.md) that contains 
  - shared model
  - backing services interfaces and stub
  - common contract wrapper

## How to run

>Note: Stacker and Stacker-sample both written on Java 11

To Run the application you should 


- clone [Stacker](https://github.com/krieven/Stacker) repository
- run `mvn clean install` in the root of them
- clone [Stacker-sample](https://github.com/krieven/Stacker-sample) repository
- run `mvn clean install` in the root of them
- configure your proxy as defined in [stacker.nginx.conf](./client/src/main/resources/stacker.nginx.conf)
- run Main classes in the modules
  - [client](./client)
  - [stacker-sample-router](./stacker-sample-router)
  - [flow-basket](./flow-basket)
  - [flow-pack](./flow-pack)
  - [flow-catalog](./flow-catalog)
- open [http://localhost:8080/](http://localhost:8080/index.html) page

