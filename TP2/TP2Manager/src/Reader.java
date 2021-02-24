import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Reader {

    public ArrayList<JSONObject> execReader(String hostName){ //lê o json e vai retornar as informações relevantes
        FilesReader fr = new FilesReader();
        String fileN = hostName.split("/")[0];
        String path = "/home/diogo/Desktop/GestãodeRedes/TP2_GR/TP2/Logs/"+fileN+".json";
        ArrayList<JSONObject> array = new ArrayList<>();
        ArrayList<SnmpInfo> fin = new ArrayList<>();
        Set<String> set = new HashSet<>();

        array = fr.jsonToArray(path);

        return array;
        // fin = q.getPRamDia("15-02-2021",array);
       /* fin = q.getPRamAlltime(array);

         for (SnmpInfo s : fin){
             System.out.println(
                     "PID : " + s.getProcessId() + "\nName : " + s.getProcessName()
                             + "\nAllocated : "+ s.getProcessAllocatedMem()
             );
         }

        */
    }
}
