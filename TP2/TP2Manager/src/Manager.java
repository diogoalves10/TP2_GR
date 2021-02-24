import java.io.*;
import java.util.*;

import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;


public class Manager {

    private Snmp snmp = null;
    private String address = null;

    public Manager(String add) {
        address = add;
    }

    public List<VariableBinding[]> start(OID[] OIDsProcesses) throws IOException { //pronto
        DefaultPDUFactory pduFactory = new DefaultPDUFactory(PDU.GETBULK);
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen(); // open port to receive response

        TreeUtils tree = new TreeUtils(snmp, pduFactory);
        tree.setMaxRepetitions(100); // 100 is the max

        List<TreeEvent> listWalk = tree.walk(getTarget(), OIDsProcesses);
        List<VariableBinding[]> vbs = new ArrayList<>(listWalk.size());

        int errorStatus = PDU.noError;
        for (TreeEvent treeEvent : listWalk) {
            errorStatus = treeEvent.getStatus();
            if (errorStatus == PDU.noError)  // check for errors
                vbs.add(treeEvent.getVariableBindings()); // copying the results to a data collection that can be manipulated later
            else
                System.out.println("error: " +errorStatus +"\n");
        }
        snmp.close();
        return vbs;

    }

    public String getGet(OID oid) throws IOException {
        String res=null;
      //  VariableBinding vb ;
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        PDU pdu = new PDU();
        pdu.add(new VariableBinding(oid));
        pdu.setType(PDU.GET);
        ResponseEvent response = snmp.send(pdu,getTarget());
        if(response.getResponse() == null){
            System.out.println("No response");
        }
        else{
            res = response.getResponse().getVariableBindings().get(0).toValueString();

        }
        return res;
    }


    private Target getTarget() {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("gr2020"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(3000);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }



}


