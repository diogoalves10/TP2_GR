import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class Writes  extends Thread{ //classe que vais buscar as coisas ao agente e imprime para json

    OID[] OIDsProcessesName = {
            new OID(new int[] {1,3,6,1,2,1,25,4,2,1,2}), // mib-2.host.hrSWRun.hrSWRunTable.hrSWRunEntry.hrSWRunName (process names)
    };

    OID[] OIDsProcessesCPUTime = {
            new OID(new int[] {1,3,6,1,2,1,25,5,1,1,1}), // mib-2.host.hrSWRunPerf.hrSWRunPerfTable.hrSWRunPerfEntry.hrSWRunPerfCPU (process CPU time)
    };

    OID[] OIDsProcessesAllocatedMem = {
            new OID(new int[] {1,3,6,1,2,1,25,5,1,1,2}),  // mib-2.host.hrSWRunPerf.hrSWRunPerfTable.hrSWRunPerfEntry.hrSWRunPerfMem (process allocated mem)
    };

    OID[] OIDsVars = {
            new OID(new int[] {1,3,6,1,2,1,25,1,6,0}), // mib-2.host.hrSystem.hrSystemProcesses (total number of processes)
            new OID(new int[] {1,3,6,1,2,1,25,2,2,0}), // mib-2.host.hrStorage.hrMemorySize (total memory size)
    };

    private TreeMap<Integer,String> vbToArray (List<VariableBinding[]> vbs) {

       TreeMap <Integer,String> array = new TreeMap<>();

        for (VariableBinding[] vba : vbs) {
            for (VariableBinding vb : vba) {
                Integer pid = vb.getOid().last();
                array.put(pid,vb.toValueString());
            }

        }
        return  array;
    }

    public void execWrites(String hostName) throws IOException, InterruptedException {
        Writes w = new Writes();
        String path = "/home/diogo/Desktop/GestãodeRedes/TP2_GR/TP2/Logs/";
        FileWrite fw = new FileWrite();
        FilesReader fr = new FilesReader();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time ;
       // String hostName = "127.0.0.1/161";
        String fileN = hostName.split("/")[0];
        int nFiles =0;

        List<VariableBinding[]> vbsProcessesName = new ArrayList<>();
        List<VariableBinding[]> vbsProcessesCPUTime = new ArrayList<>();
        List<VariableBinding[]> vbsProcessesAllocatedMem = new ArrayList<>();


        Manager client = new Manager(hostName);
        vbsProcessesName= client.start(OIDsProcessesName);
        vbsProcessesCPUTime = client.start(OIDsProcessesCPUTime);
        vbsProcessesAllocatedMem = client.start(OIDsProcessesAllocatedMem);

        while (nFiles<10) {
            Date date = new Date();
            time = formatter.format(date);
            TreeMap  pidName = new TreeMap<>();
            TreeMap  pidCPU = new TreeMap<>();
            TreeMap  pidAlloMem = new TreeMap<>();

            ArrayList<SnmpInfo> array = new ArrayList<>();
            pidName = w.vbToArray(vbsProcessesName);
            pidCPU = w.vbToArray(vbsProcessesCPUTime);
            pidAlloMem = w.vbToArray(vbsProcessesAllocatedMem);


            TreeMap finalPidCPU = pidCPU;
            TreeMap finalPidAlloMem = pidAlloMem;

            pidName.forEach((key, value) -> {
                SnmpInfo snmpInfo = new SnmpInfo();
                   snmpInfo.setProcessId(key.toString());
                   snmpInfo.setProcessName(value.toString());
                   snmpInfo.setProcessCPUTime(finalPidCPU.get(key).toString());
                   snmpInfo.setProcessAllocatedMem(finalPidAlloMem.get(key).toString());
                   array.add(snmpInfo);

            });

            fw.fileOpen(path + fileN + ".json", fileN,array,
                    client.getGet(OIDsVars[0]),
                    client.getGet(OIDsVars[1]),time);

            Thread.sleep(1000); //polling time de 1 segundo
            nFiles++;
        }
    }
}
