import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MetodosAPI {
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

    public Double cambioMoneda(JsonObject res, String moneda){
        return res.get("conversion_rates")
                .getAsJsonObject()
                .get(moneda).getAsDouble();
    }

}
