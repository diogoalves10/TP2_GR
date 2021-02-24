public class SnmpInfo {

    String processId;
    String processName;
    String processCPUTime;
    String processAllocatedMem;


    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessCPUTime() {
        return processCPUTime;
    }

    public void setProcessCPUTime(String processCPUTime) {
        this.processCPUTime = processCPUTime;
    }

    public String getProcessAllocatedMem() {
        return processAllocatedMem;
    }

    public void setProcessAllocatedMem(String processAllocatedMem) {
        this.processAllocatedMem = processAllocatedMem;
    }

    public SnmpInfo() {
        this.processId = "";
        this.processName = "";
        this.processCPUTime = "";
        this.processAllocatedMem = "";
    }
}
