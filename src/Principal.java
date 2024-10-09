import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        MetodosAPI metodo = new MetodosAPI();

        while (true) {
            try {
                System.out.println("**api-conversordemonedas** Dolar USD - Euro EUR - Real BRL ** : ");
                System.out.println("**al finalizar la operacion se mostraran los tres cambios con JsonParser**");
                System.out.println("**tambien se creará un archivo de texto con el historial**"+"\n");
                System.out.println("**********************************************************"+"\n");

                System.out.println("Ingresar la moneda de origen : ");
                String monedaOrigen = teclado.nextLine();
                System.out.println("Ingresar la moneda de destino : ");
                String monedaDestino = teclado.nextLine();
                System.out.println("Ingresar el monto a convertir : ");
                var montoAConvertir = Double.valueOf(teclado.nextLine());

                ConexionAPI conexion = new ConexionAPI();

                apiEXCHANGE resultado = conexion.buscarMonedas(monedaOrigen, monedaDestino, montoAConvertir);
                JsonObject resultadoBasico = conexion.buscarMonedasBasico(monedaOrigen);


                //muestro otras monedas usuales con el jsonobject y el metodo API estandard que trae todas las conversiones
                System.out.println("************************************"+"\n");

                System.out.println("El importe de " + montoAConvertir + resultado);
                System.out.println("En Euros el importe es de $EUR  " + metodo.cambioMoneda(resultadoBasico,"EUR") * montoAConvertir);
                System.out.println("En Dolares el importe es de $USD  " + metodo.cambioMoneda(resultadoBasico,"USD") * montoAConvertir);
                System.out.println("En Reales el importe es de $BRL  " + metodo.cambioMoneda(resultadoBasico,"BRL") * montoAConvertir+"\n"+"\n");

                LocalDateTime hora = LocalDateTime.now();
                //MetodosAPI archivar = new MetodosAPI();
                metodo.guardarJson(resultado,montoAConvertir,hora);

            } catch (NumberFormatException e) {
                System.out.println("*** revisar los datos *** " +e.getMessage());
            } catch (RuntimeException | IOException e) {
                System.out.println("*** error en el programa ***  "+e.getMessage());
            }

            System.out.println("Desea seguir operando ? si / no");
            String salida = teclado.nextLine();
            if(salida.equals("no")){
                System.out.println("Se creó un archivo de texto con el historial. Gracias");
                break;
            }
        }
    }
}