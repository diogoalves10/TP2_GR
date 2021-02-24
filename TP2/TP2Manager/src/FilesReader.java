import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class FilesReader { //lê um array de objectos e retorna um arraylist de objectos

    public ArrayList<JSONObject> jsonToArray(String fname) {
        JSONParser parser = new JSONParser();
        ArrayList<JSONObject> res = new ArrayList<>();
        try {

            Object obj = parser.parse(new FileReader(fname));

            JSONArray hostArray = (JSONArray) obj;

            for (Object jo : hostArray) {
                JSONObject objecto = (JSONObject) jo;
                res.add(objecto);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}

