# Currency Exchange Challenge

Existen tareas que por sencillas que parezcan, pueden ser simplificadas utilizando las herramientas adecuadas. Convertir cierta cantidad de una moneda a otra se reduce en un par de operaciones matematicas, no obstante, existen formas de hacer mas accesible y sencillo realizar dicha tarea.

Este conversor de monedas ofrece soporte a más de 250 codigos de monedas (Siguiendo el estándar [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217)), contiene un historial de conversiones y se mantiene actualizado gracias a la API DE [ExchangeRate](https://www.exchangerate-api.com).

Este proyecto fomenta la integración de servicios externos para la resoución de problemas, además de resaltar la importancia de la lógica y la integración de APIs en el desarrollo de software.

## API

#### Consultar moneda

```
  https://v6.exchangerate-api.com/v6/api_key/latest/currency
```

| Parametro | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Requerido**. Nuestra API key |
| `currency` | `string` | **Requerido**. Moneda solicitada |

Esta consulta retorna:

* Resultado de la consulta ("Success").
* Fecha de la utima vez que se actualizaron los valores.
* Fecha de la proxima vez que se actualizarán los valores.
* Nombre de la moneda segun el estándar [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217).
* Tasas de conversión de esa moneda.

## Uso

La manera de usar este programa es sencillo de entender y ejecutar.

El programa ofrece ciertas opciones a realizar, el usuario decide que acción llevar a cabo y el programa ejecuta dicha acción y regresa al inicio; estos pasos se repiten en un bucle hasta que el programa sea cerrado por el usuario.

