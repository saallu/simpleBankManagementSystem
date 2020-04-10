import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Operations{
    void showMenu();
    void addCustomer(DataModelClass modelClass);
    void changeInterestRate(double interestRate);
    void numberOfActiveAccount();
    void accountInfo(String name);
    void depositMoney(String name,long ammount);
    void withdrawMoney(String name,long amount);
    void transferMoney(String myAccount,long money,String clientAccount);
    void updateBalanceWithInterestRate();
}
