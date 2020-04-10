public class DataModelClass {

    String acType;
    String name;
    long acNumber;
    long acBalance;

    public DataModelClass(String acType, String name, long acNumber, long acBalance) {
        this.acType = acType;
        this.name = name;
        this.acNumber = acNumber;
        this.acBalance = acBalance;
    }

    public DataModelClass() {
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAcNumber() {
        return acNumber;
    }

    public void setAcNumber(long acNumber) {
        this.acNumber = acNumber;
    }

    public long getAcBalance() {
        return acBalance;
    }

    public void setAcBalance(long acBalance) {
        this.acBalance = acBalance;
    }
}
