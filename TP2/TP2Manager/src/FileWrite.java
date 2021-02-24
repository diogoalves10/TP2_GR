import java.io.*;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileWrite{
   // SnmpInfo sInfo = new SnmpInfo();

    public void fileOpen(String fName,String hostName , ArrayList<SnmpInfo> info,String totalNumberProcess, String totalMemSize,String time ) throws IOException{
        BufferedWriter bw = null;
        JSONParser parser = new JSONParser();
        try {

            File file = new File(fName);

            Object obj = parser.parse(new FileReader(file));

                //Objecto host
                JSONObject host = new JSONObject();

                JSONArray hostArray = (JSONArray)obj;

                JSONArray array = new JSONArray();

                for(SnmpInfo s : info){
                    JSONObject snmpAux = new JSONObject();
                    snmpAux.put("processId ",s.getProcessId());
                    snmpAux.put("processName ",s.getProcessName());
                    snmpAux.put("processCPUTime ",s.getProcessCPUTime());
                    snmpAux.put("processAllocatedMem ",s.getProcessAllocatedMem());

                    array.add(snmpAux);
                    }

                host.put("host",hostName);
                host.put("snmpData",array);
                host.put("time",time);
                host.put("totalNumProcessesFromSnmp",totalNumberProcess);
                host.put("TotalMemorySize",totalMemSize);

                hostArray.add(host);

                bw = new BufferedWriter(new FileWriter (fName));
                bw.write(hostArray.toJSONString());
                bw.close();

        } catch (IOException | ParseException e){
            System.out.println("Erro na criação do ficheiro");
            e.printStackTrace();
        }

    }

}
