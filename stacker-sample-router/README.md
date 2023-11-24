# Stacker-sample-router

***Sample Router***

---

## Description

The [router](src/main/java/Main.java) uses reference implementation of RouterServer,
HttpTransport and in-memory SimpleSessionStorage, provided with [krieven / Stacker](https://github.com/krieven/Stacker).

The router configuration is described in the [config.json](src/main/resources/config.json) file.
The file is a serialized object of the NSRouterConfig class that implements RouterConfig interface.
Please refer to the corresponding classes in the [krieven / Stacker](https://github.com/krieven/Stacker) github repository.

The configuration contains one namespace with three workflow components.

