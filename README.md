# 🩺 UniStress: Plataforma de Bienestar Emocional Universitario

## Descripción del Proyecto

**UniStress** es una plataforma web enfocada en la **gestión y prevención del estrés académico y la ansiedad** en estudiantes universitarios. Nuestro objetivo es mejorar el bienestar emocional de los estudiantes, ofreciendo herramientas digitales y acceso a profesionales de la salud mental.

El proyecto busca abordar la creciente problemática de la salud mental en el ámbito universitario, donde se han detectado altos niveles de ansiedad (39%) y estrés (32%). La solución se alinea con el Objetivo de Desarrollo Sostenible (ODS) N.º 3: "Garantizar una vida sana y promover el bienestar para todas las edades".

## 💡 Características Principales

La aplicación ofrece un conjunto de funcionalidades centradas en la prevención y el apoyo constante, con acceso diferenciado por rol (ADMIN, ESTUDIANTE, PSICÓLOGO).

| Tipo de Funcionalidad | Descripción |
| :--- | :--- |
| **Monitoreo Emocional** | Registro de emociones diario y **Test de Autoevaluación Emocional Semanal** para un seguimiento continuo. |
| **Alerta Temprana** | Notificaciones preventivas cuando se detectan patrones de alto riesgo emocional. |
| **Recomendaciones Personalizadas** | Sugerencias adaptadas al nivel de estrés y al calendario académico del estudiante. |
| **Soporte Profesional** | Acceso a sesiones en vivo con **psicólogos especializados**. |
| **Seguridad y Confidencialidad** | Implementación de **Spring Security** para control de acceso por roles y protección de *endpoints*. |
| **API Completa** | Backend con modelo **CRUD completo** para 13 entidades, incluyendo usuarios, sesiones, registros emocionales y pagos. |

## 🛠️ Tecnologías Utilizadas (Stack)

Este proyecto se implementó utilizando una arquitectura moderna para garantizar escalabilidad, seguridad y eficiencia:

| Componente | Tecnología | Uso |
| :--- | :--- | :--- |
| **Backend** | **Java** (con **IntelliJ IDEA** como IDE) | Lógica de negocio y desarrollo de la API. |
| **Framework** | **Spring Boot** (implícito por el uso de Spring Security) | Desarrollo ágil de servicios y API REST. |
| **Seguridad** | **Spring Security** y **Tokens JWT** | Autenticación, autorización y protección de rutas según roles (ADMIN, ESTUDIANTE, PSICÓLOGO). |
| **Base de Datos** | **PostgreSQL** (con **pgAdmin**) | Motor de base de datos relacional para gestionar la información del sistema. |
| **Frontend/Landing** | **HTML, CSS, JavaScript** | Interfaz de usuario para la aplicación web y la *Landing Page*. |
| **Documentación API** | **Postman** y **Swagger** | Pruebas de *endpoints* (Postman) y generación de especificaciones estandarizadas (Swagger). |
| **Control de Versiones** | **Git** / **GitHub** | Colaboración en equipo y alojamiento centralizado del código. |

## 🚀 Despliegue y Enlaces

El proyecto se encuentra alojado en GitHub y se ha desplegado a través de GitHub Pages para la *Landing Page*.

| Tipo | Enlace |
| :--- | :--- |
| **Repositorio Principal** | [UniStress Repository](https://github.com/arturo-ns/UniStress2.git) |
| **URL del Despliegue** | [UniStress Live Demo (GitHub Pages)](https://arturo-ns.github.io/UniStress2/) |

## ⚙️ Configuración y Ejecución Local

Sigue estos pasos para levantar el proyecto en tu entorno local.

### 1. Requisitos Previos

Asegúrate de tener instalados:

* **Java Development Kit (JDK) 17+**
* **PostgreSQL**
* **IntelliJ IDEA** o un IDE compatible con Spring Boot.
* **Git**

### 2. Clonar el Repositorio

Clona el código fuente del proyecto:

```bash
git clone [https://github.com/arturo-ns/UniStress2.git](https://github.com/arturo-ns/UniStress2.git)
cd UniStress2
