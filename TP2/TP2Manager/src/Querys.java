import com.sun.source.tree.Tree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.snmp4j.Snmp;

import java.util.*;

public class Querys {

    private void getTime (JSONObject j , String dia , ArrayList<JSONObject> res){
        String time = (String) j.get("time");
        if(time.split(" ")[0].equals(dia)){
            res.add(j);
        }
    }

    private void arrayToSnmpInfo (JSONArray jsonarray , ArrayList<SnmpInfo> arrayList){
        JSONObject jsonObject = new JSONObject();
        for(int i=0; i<jsonarray.size();i++){
            SnmpInfo snmpInfo = new SnmpInfo();
            jsonObject = (JSONObject) jsonarray.get(i);
            snmpInfo.setProcessId((String) jsonObject.get("processId "));
            snmpInfo.setProcessName((String) jsonObject.get("processName "));
            snmpInfo.setProcessCPUTime((String) jsonObject.get("processCPUTime "));
            snmpInfo.setProcessAllocatedMem((String) jsonObject.get("processAllocatedMem "));
            arrayList.add(snmpInfo);
        }
    }

    public ArrayList<SnmpInfo> getPRamDia(String dia , ArrayList<JSONObject> array){ //10 processos que mais ram utilizam num determinado dia
    ArrayList<JSONObject> res = new ArrayList<>();
    ArrayList<SnmpInfo> aux = new ArrayList<>();
    ArrayList<SnmpInfo> fin = new ArrayList<>();

    TreeMap<Integer,SnmpInfo> tmap = new TreeMap<>(Collections.reverseOrder());

    Integer ram;

     for(JSONObject j : array){ //eliminamos todos os que não são do dia que queremos
        getTime(j,dia,res);
     }
        for(JSONObject j : res){ //para todos os objectos vamos buscar o array snmpdata e guardamos a informação num arraylist de snmpInfo
            JSONArray jsonArray = (JSONArray)j.get("snmpData");

            arrayToSnmpInfo(jsonArray,aux);

        }
        JSONObject obj = res.get(0);
        ram = Integer.parseInt((String) obj.get("TotalMemorySize"));
        System.out.println("ram: " +ram);
        for(SnmpInfo s : aux){ //ordenamos toda a informação pelos que usam mais memória ram
          //  int temp = Integer.parseInt(s.getProcessAllocatedMem());

            tmap.put(Integer.parseInt(s.getProcessAllocatedMem()),s);
        }

      for(int i=0; i<10 ; i++){
          Integer k = tmap.firstKey();
          fin.add(tmap.get(k));
          tmap.remove(k);
      }

      for(SnmpInfo s : fin) {

          Integer temp = Integer.parseInt(s.getProcessAllocatedMem());
          Integer i = (temp*100)/ram;
          System.out.println("Antes de entrar" + s.getProcessAllocatedMem());
          s.setProcessAllocatedMem(i.toString());
          System.out.println("Depois de entrar" + s.getProcessAllocatedMem());

      }


        return fin;
    }

    public ArrayList<SnmpInfo> getPRamAlltime(ArrayList<JSONObject> array){ //10 processos que mais RAM gastam de sempre
        ArrayList<JSONObject> res = new ArrayList<>();
        ArrayList<SnmpInfo> aux = new ArrayList<>();
        ArrayList<SnmpInfo> fin = new ArrayList<>();

        TreeMap<Integer,SnmpInfo> tmap = new TreeMap<>(Collections.reverseOrder());
        Integer ram;

        for(JSONObject j : array){
            res.add(j);
        }
        //problema tem de estar aqui
        for(JSONObject j : res){ //para todos os objectos vamos buscar o array snmpdata e guardamos a informação num arraylist de snmpInfo
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray)j.get("snmpData");

            arrayToSnmpInfo(jsonArray,aux);

        }
        JSONObject obj = res.get(0);
        ram = Integer.parseInt((String) obj.get("TotalMemorySize"));

        for(SnmpInfo s : aux){ //ordenamos toda a informação pelos que usam mais memória ram
            //  int temp = Integer.parseInt(s.getProcessAllocatedMem());

            tmap.put(Integer.parseInt(s.getProcessAllocatedMem()),s);
        }

        for(int i=0; i<10 ; i++){
            Integer k = tmap.firstKey();
            fin.add(tmap.get(k));
            tmap.remove(k);
        }

        for(SnmpInfo s : fin) {

            Integer temp = Integer.parseInt(s.getProcessAllocatedMem());
            Integer i = (temp*100)/ram;
            s.setProcessAllocatedMem(i.toString());

        }


        return fin;
    }

    public void getCPUInfoDia(){ //percentagem de utilização de cpu

    }

    public Set<String> getPIDSzero(ArrayList<JSONObject> array){ //todos os processos que estão a 0 no cpu e na ram
        ArrayList<JSONObject> res = new ArrayList<>();
        ArrayList<SnmpInfo> aux = new ArrayList<>();
        ArrayList<SnmpInfo> fin = new ArrayList<>();
        Set<String> set = new HashSet<>();

        TreeMap<String,SnmpInfo> tmap = new TreeMap<>(Collections.reverseOrder());


        for(JSONObject j : array){
            res.add(j);
        }

        for(JSONObject j : res){ //para todos os objectos vamos buscar o array snmpdata e guardamos a informação num arraylist de snmpInfo
            JSONArray jsonArray = (JSONArray)j.get("snmpData");
            arrayToSnmpInfo(jsonArray,aux);

        }

        aux.removeIf(s->!(Integer.parseInt(s.getProcessAllocatedMem()) ==0 && Integer.parseInt(s.getProcessCPUTime())==0));
        for(SnmpInfo s : aux){
            set.add(s.getProcessName());
        }
        return set;
    }
}

