import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Principal {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Scanner input = new Scanner(System.in);
    public static ArrayList<String> exchangeMemory = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        //Lista de acciones
        List<String> acciones = Arrays.asList("1 - Convertir monedas","2 - Consultar monedas más populares del mundo",
                "3 - Consultar monedas de Latinoamerica", "4 - Historial de conversiones", "0 - Salir");

        System.out.println("Bienvenido al conversor de monedas");

        //Ciclo que termina cuando el usuario lo indique
        while (true){
            System.out.println("***********************************************");
            for (String items: acciones){
                System.out.println(items);
            }
            System.out.println("¿Que accion desea realizar?");
            int opcion = input.nextInt();
            input.nextLine();

            //Fin del ciclo
            if (opcion == 0) {
                System.out.println("Gracias por usar el sistema");
                break;
            }

            //Arbol de opciones
            switch (opcion){
                case 1:
                    conversorMonedas();
                    break;
                case 2:
                    monedasDelMundo();
                    break;
                case 3:
                    monedasDeLatinoamerica();
                    break;
                case 4:
                    System.out.println("Historial de conversiones");
                    if (exchangeMemory.isEmpty()) {
                        System.out.println("Aun no hay registros");
                        break;
                    }
                    for (String registro: exchangeMemory){
                        System.out.println(registro);
                    }
                    break;
            }
        }
    }

    //Metodo encargado de establecer la conexion con la API
    public static String apiRequest(String currency) throws IOException, InterruptedException {
        String URL = "https://v6.exchangerate-api.com/v6/e09da3b08863afadcc1efb0b/latest/" + currency;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest filmsRequest = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();

        HttpResponse<String> response = client
                .send(filmsRequest, HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.body());

        return response.body();
    }

    //Opciones
    public static void conversorMonedas() throws IOException, InterruptedException {
        try {
            System.out.println("¿Que moneda desea operar? (Utilice el codigo de la moneda)");
            String firstCurrencyName = input.nextLine();
            Currency currencyConsult = gson.fromJson(apiRequest(firstCurrencyName), Currency.class);

            //Verifica que la moneda que buscamos existe
            if (currencyConsult.conversion_rates() == null) {
                throw new ExceptionMonedas();
            }

            System.out.println("¿Que cantidad desea convertir?");
            double cantidad = Double.parseDouble(input.nextLine());

            //Verifica que la cantidad sea positiva
            if (cantidad <= 0) {
                throw new ExceptionNegativos();
            }

            System.out.println("¿Hacia que moneda desea convertir? (Utilice el codigo de la moneda)");
            String secondCurrencyName = input.nextLine().toUpperCase();

            //Consulta el valor de la 2da moneda en relacion a la 1ra moneda
            double valor = currencyConsult.conversion_rates().get(secondCurrencyName);
            //Realiza la conversion de divisas
            double conversion = cantidad * valor;
            //Obtiene la hora en que se realizó la operacion
            LocalTime timeObj = LocalTime.now();

            System.out.printf("Precio de %s: %f%n", secondCurrencyName, valor);
            System.out.printf("%.2f %s equivalen a %.2f %s%n",
                    cantidad, firstCurrencyName.toUpperCase(), conversion, secondCurrencyName);

            exchangeMemory.add("(%s) [%.2f %s] == [%.2f %s]".formatted(
                    timeObj.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    cantidad,
                    firstCurrencyName.toUpperCase(),
                    conversion,
                    secondCurrencyName));
        }
        catch (ExceptionNegativos | InputMismatchException | NumberFormatException e){
            System.out.println("Cantidad no valida. Intente nuevamente");
        }
        catch (ExceptionMonedas | NullPointerException e){
            System.out.println("Moneda no encontrada, favor de verificar el codigo");
        }
        catch (JsonSyntaxException | IllegalStateException e){
            System.out.println("Codigo no valido.");
        }
    }
    public static void monedasDelMundo(){
        List<String> monedas = Arrays.asList("Dolar Americano - [USD]", "Euro - [EUR]", "Yen Japones - [JPY]",
                "Libra Esterlina - [GBP]", "Dolar Australiano - [AUD]", "Dolar Canadiense - [CAD]",
                "Franco Suizo - [CHF]", "Renminbi Chino - [CNH]", "Dolar de Hong Kong - [HKD]",
                "Dolar de Nueva Zelanda - [NZD]");
        System.out.println("Estas son las monedas mas populares del mundo en la actualidad: ");
        for (String item: monedas){
            System.out.println(item);
        }
    }
    public static void monedasDeLatinoamerica(){
        List<String> monedas = Arrays.asList("Real Brtazileño - [BRL]", "Peso Argentino - [ARS]",
                "Peso Uruguayo - [UYU]", "Peso Colombiano - [COP]", "Peso Chileno - [CLP]", "Peso Mexicano - [MXN]");
        System.out.println("Estas son las monedas mas populares de Latinoamerica en la actualidad: ");
        for (String item: monedas){
            System.out.println(item);
        }
    }
}

class ExceptionNegativos extends Exception{}
class ExceptionMonedas extends Exception{}