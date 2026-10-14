# MisGastos - Control de Gastos Personales

## Descripcion

MisGastos es una aplicacion Android creada para ayudar a las personas a controlar sus gastos personales y conocer cuanto dinero tienen disponible.

## Funcionalidades

- Inicio de sesion con usuario y contrasena.
- Registro de gastos con descripcion, monto y categoria.
- Ingreso del presupuesto disponible.
- Calculo automatico del total gastado.
- Calculo del dinero restante.
- Lista de gastos registrados.
- Eliminacion de gastos.
- Resumen de gastos por categoria.
- Mensajes de advertencia cuando se supera el presupuesto.
- Uso de la camara para fotografiar comprobantes.
- Imagen relacionada con las finanzas.
- Boton para cerrar sesion.

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose
- Android Studio

## Estructura del proyecto

- MainActivity.kt: contiene el login, registro de gastos, calculos, resumen y uso de la camara.
- ui/theme: contiene los archivos relacionados con el tema visual del proyecto.
- res/drawable: contiene la imagen utilizada en la aplicacion.
- AndroidManifest.xml: contiene la configuracion principal de la aplicacion.

## Como ejecutar la aplicacion

1. Abrir el proyecto MisGastos en Android Studio.
2. Esperar que Gradle termine de sincronizar.
3. Seleccionar un emulador Android o conectar un celular.
4. Presionar Run para ejecutar la aplicacion.
5. Iniciar sesion con los datos de prueba.
6. Ingresar un presupuesto y registrar gastos.
7. Consultar el resumen y probar las demas funciones.

## Datos de acceso

Usuario: admin

Contrasena: 1234

## Observacion

Esta aplicacion fue desarrollada como practica academica. Los datos se mantienen temporalmente durante el uso de la pantalla y no se guardan en una base de datos. La camara permite obtener una vista previa de un comprobante.

## Objetivo

Facilitar el control de gastos personales mediante una aplicacion sencilla y facil de utilizar.