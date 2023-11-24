# Stacker-sample / client

***Sample realization of web client***

---

## Description

The Stacker-sample / client is SPA web-application. 
It run in a browser and provides UI layer for components of [Stacker-sample](../README.md) app.
The client is very simple and based on [krieven / W3View](https://github.com/krieven/W3View) library.
Of course, you can use any other technology to create a client, but W3View is perfect for this task.

Client provides separate module for each component of Stacker-sample app.

Modules are loaded as needed. 
For example: if there is an interaction with the [basket](../flow-basket/README.md) component, 
the [basket.html](./src/main/resources/static/components/computers/basket.html) module is loaded and used, 
when the application switches to the [catalog](../flow-catalog/README.md) component, 
the [catalog.html](./src/main/resources/static/components/computers/catalog.html) module will be loaded, and so on. 
If modules use shared libraries, then these libraries are loaded into the application only once and only when necessary.

Stacker-sample doesn't explain how to use krieven / W3View, for more information visit
[krieven / W3View](https://github.com/krieven/W3View) repository on github 