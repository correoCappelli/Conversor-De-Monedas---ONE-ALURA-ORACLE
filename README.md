

# ONE Alura + Oracle 

## api-conversordemonedas
## Instructores
### Ing Bruno Ellerbach
### Ing Genesys Rondon
# API ExchangeRate-API
### https://www.exchangerate-api.com/



# SUMARIO


- [ONE Alura + Oracle](#one-alura--oracle)
  - [api-conversordemonedas](#api-conversordemonedas)
  - [Instructores](#instructores)
    - [Ing Bruno Ellerbach](#ing-bruno-ellerbach)
    - [Ing Genesys Rondon](#ing-genesys-rondon)
- [API ExchangeRate-API](#api-exchangerate-api)
    - [https://www.exchangerate-api.com/](#httpswwwexchangerate-apicom)
- [SUMARIO](#sumario)
  - [Introduccion](#introduccion)
  - [EndPoints](#endpoints)
  - [URL con todos los codigos de monedas posibles](#url-con-todos-los-codigos-de-monedas-posibles)
  - [archivo de historial successFECHA.txt](#archivo-de-historial-successfechatxt)
  - [Variables de Entorno (.Env)](#variables-de-entorno-env)
  - [EXCEPCIONES](#excepciones)


## Introduccion

La aplicacion conversor de monedas utiliza la API ExchangeRate-API para obtener los tipos de cambio de las monedas.
Luego se devuelven los resultados al usuario , ya sea indicando la moneda de origen , la de destino y el importe o , también, solicitando la totalidad de las tasas de cambio , y mostrando tres de ellas (USD - EUR - BRL) . Los datos se obtienen utilizando el artifacto GSON y sus metodos JSonObject y JSONparser entre otros. Al finalizar las operaciones se archiva un historial en un archivo de texto cuyo nombre es la fecha del dia (libreria java.time) 

## EndPoints

|PETICION |URL |DESCRIPCION|
--- | --- | ---|
|GET|https://v6.exchangerate-api.com/v6/YOUR-API-KEY/latest/USD|es la ruta del API estandard. Devuelve todas las tasas de cambio en un JSONObject|
|GET|https://v6.exchangerate-api.com/v6/YOUR-API-KEY/pair/moneda_origen/moneda_destino|Es la API PAIR CONVERSION donde se introducen las monedas de destino y de origen. Sin el importe a convertir|
|GET|https://v6.exchangerate-api.com/v6/YOUR-API-KEY/pair/moneda_origen/moneda_destino/importe_a_convertir|Es la API PAIR CONVERSION + IMPORTE donde se introducen las monedas de destino y de origen. Además se puede indicar el importe a convertir|

Ejemplo de la respuesta de la API Estandard :

```json
{
	"result": "success",
	"documentation": "https://www.exchangerate-api.com/docs",
	"terms_of_use": "https://www.exchangerate-api.com/terms",
	"time_last_update_unix": 1585267200,
	"time_last_update_utc": "Fri, 27 Mar 2020 00:00:00 +0000",
	"time_next_update_unix": 1585353700,
	"time_next_update_utc": "Sat, 28 Mar 2020 00:00:00 +0000",
	"base_code": "USD",
	"conversion_rates": {
		"USD": 1,
		"AUD": 1.4817,
		"BGN": 1.7741,
		"CAD": 1.3168,
		"CHF": 0.9774,
		"CNY": 6.9454,
		"EGP": 15.7361,
		"EUR": 0.9013,
		"GBP": 0.7679,
		"...": 7.8536,
		"...": 1.3127,
		"...": 7.4722, etc. etc.
	}
}
```
Metodo para obtener los tipos de cambio para operar 

``` java
public Double cambioMoneda(JsonObject res, String moneda){
        return res.get("conversion_rates")
                .getAsJsonObject()
                .get(moneda).getAsDouble();
    }
```    

## URL con todos los codigos de monedas posibles

> https://www.exchangerate-api.com/docs/supported-codes-endpoint



## archivo de historial successFECHA.txt
```java
public void guardarJson(apiEXCHANGE resultado, Double importe,LocalDateTime hora) throws IOException {
        LocalDate fecha = LocalDate.now();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escritura = new FileWriter(resultado.result()+fecha+".txt",true);
        escritura.write("***"+ resultado.base_code()+"\n"+
                            resultado.target_code()+"\n"+
                            resultado.conversion_rate()+"\n"+
                            resultado.conversion_result()+"\n"+
                            importe+"\n"+
                            hora+"\n"+
                            "*************"+"\n");
        escritura.close();
    }
```

## Variables de Entorno (.Env)

```java
API-KEY = 
```

## EXCEPCIONES 

```java
try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

try {
            JsonObject jsonObject = gson.fromJson(respuesta, JsonObject.class);
        } catch (JsonSyntaxException e) {
//formato incompatible en la respuesta ?          
            throw new RuntimeException("*** error en el formato de la URI o del Json ***"+"   "+e);
        }

JsonObject jsonObject = gson.fromJson(respuesta, JsonObject.class);
//muestro el tipo de error que nos proporciona la API
            if (jsonObject.get("result").getAsString().equals("error")) {
                System.out.println(jsonObject.get("error-type").getAsString());
                throw new RuntimeException(jsonObject.get("error-type").getAsString());
            }                

try {
   

                ConexionAPI conexion = new ConexionAPI();

                apiEXCHANGE resultado = conexion.buscarMonedas(monedaOrigen, monedaDestino, montoAConvertir);
                JsonObject resultadoBasico = conexion.buscarMonedasBasico(monedaOrigen);

                metodo.guardarJson(resultado,montoAConvertir,hora);

// si el usuario ingresa un String en vez de Double para el importe
            } catch (NumberFormatException e) {
                System.out.println("*** revisar los datos *** " +e.getMessage());
// para las demas excepciones de escritura o de programa                
            } catch (RuntimeException | IOException e) {
                System.out.println("*** error en el programa ***  "+e.getMessage());
            }            
```            