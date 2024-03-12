import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonLoader {

    public static List<Kunden> loadKundenFromJson(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            KundenWrapper wrapper = gson.fromJson(reader, KundenWrapper.class);
            return wrapper.getBook();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
