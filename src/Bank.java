import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class Bank implements Operations {



    @Override
    public void showMenu() {

        System.out.println("********************     WELCOME TO BANK     ********************\n");
        System.out.println("1.Add new customer");
        System.out.println("2.Change interest rate");
        System.out.println("3.Update balance in new interest rate");
        System.out.println("4.Get number of currently active account");
        System.out.println("5.find any account details");
        System.out.println("6.Deposit money in any account");
        System.out.println("7.Withdraw money from account");
        System.out.println("8.Transfer money between two account");
        System.out.println("press 0 to exit");

        System.out.println("Your choice : ");



        Scanner scanner=new Scanner(System.in);
        String actype = null;
        String name;
        long acnumber;
        long inibalance = 0;
        Random random = new Random();
        int input = scanner.nextInt();


        if (input == 1) {
            System.out.println("Account Type ?\n1.Current\n2.Saving");
            int ac = scanner.nextInt();

            if (ac == 1) {
                actype = "Current";

            } else if (ac == 2) {
                actype = "Saving";
            }else {
                System.exit(0);
            }

           scanner.nextLine();
           System.out.println("Enter name");
           name = scanner.nextLine();
           System.out.println("Enter Balance");
           inibalance = scanner.nextLong();
           acnumber = random.nextInt(900) + 100;

           DataModelClass modelClass=new DataModelClass();
           modelClass.setName(name);
           modelClass.setAcNumber(acnumber);
           modelClass.setAcBalance(inibalance);
           modelClass.setAcType(actype);
           addCustomer(modelClass);
        }else if (input == 2){

            System.out.println("Enter Interest rate");
           double interest = scanner.nextDouble();
           changeInterestRate(interest);

        }else if(input == 3){

            updateBalanceWithInterestRate();

        }else if(input == 4){
            //number of currently active account
            numberOfActiveAccount();
        }else if (input == 5){

            System.out.println("Enter account name :");
            scanner.nextLine();
            String username = scanner.nextLine();

            accountInfo(username);

        }else if(input == 6){
            System.out.println("Enter account holder name");
            scanner.nextLine();
            String user = scanner.nextLine();
            System.out.println("Enter amount of money");
            long amount = scanner.nextLong();
            depositMoney(user,amount);
        }else if (input == 7){
            scanner.nextLine();
            System.out.println("Enter account holder name");
            String user = scanner.nextLine();
            System.out.println("Enter amount of money");
            long amount = scanner.nextLong();
            withdrawMoney(user,amount);

        }else if(input == 8){

            scanner.nextLine();
            System.out.println("Enter your account name");
            String myAccount = scanner.nextLine();
            System.out.println("Enter the amount");
            long money = scanner.nextLong();
            scanner.nextLine();
            System.out.println("Enter the account name you want to transfer money");
            String transferedAccount = scanner.nextLine();
            transferMoney(myAccount,money,transferedAccount);
        }else if(input == 0){
            System.exit(0);
        }else{
            System.out.println("Invalid choice");
        }

    }

    @Override
    public void addCustomer(DataModelClass modelClass) {

        try{
            String nameNumberString;
            int index;

            File file = new File("account.txt");
            if (!file.exists()){
                file.createNewFile();
            }

            RandomAccessFile raf = new RandomAccessFile(file,"rw");

            boolean found = false;

            while (raf.getFilePointer() < raf.length()){
                nameNumberString = raf.readLine();

                index = nameNumberString.indexOf('-');
                int index2 = nameNumberString.indexOf('_');
                int index3 = nameNumberString.indexOf('|');
                String acType = nameNumberString.substring(0,index);
                long acnumber = Long.parseLong(nameNumberString.substring(index+1,index2));
                String acname = nameNumberString.substring(index2+1,index3);
                long acbalance = Long.parseLong(nameNumberString.substring(index3+1));


                if (  acname.equals(modelClass.getName()) ){
                    found = true;

                    break;
                }

            }

            if (found != true){
                nameNumberString = modelClass.getAcType()+"-"+ String.valueOf(modelClass.getAcNumber()) +"_"+
                         modelClass.getName()+ "|"+String.valueOf(modelClass.getAcBalance());
                raf.writeBytes(nameNumberString);
                raf.writeBytes(System.lineSeparator());
                System.out.println("added");
                raf.close();

            } if(found == true){
                raf.close();
                System.out.println("Sorry cant create account in this name! the name already exist");
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void changeInterestRate(double interestRate) {

        try {
            String nameNumberString;
            int index;

            File file = new File("rate.txt");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");


            File tmpFile = new File("temp.txt");


            RandomAccessFile tmpraf
                    = new RandomAccessFile(tmpFile, "rw");


            raf.seek(0);

            while (raf.getFilePointer() < raf.length()) {


                nameNumberString = raf.readLine();

                index = nameNumberString.indexOf('-');
                double rate = Double.parseDouble(nameNumberString.substring(0, index));


                nameNumberString = String.valueOf(interestRate)+"-";



                tmpraf.writeBytes(nameNumberString);


                tmpraf.writeBytes(System.lineSeparator());
            }

            raf.seek(0);
            tmpraf.seek(0);


            while (tmpraf.getFilePointer() < tmpraf.length()) {
                raf.writeBytes(tmpraf.readLine());
                raf.writeBytes(System.lineSeparator());
            }


            raf.setLength(tmpraf.length());


            tmpraf.close();
            raf.close();


            tmpFile.delete();

            System.out.println(" interest rate updated ");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void numberOfActiveAccount() {
        try{
            String nameNumberString;
            int index;
            int counter = 0;

            File file = new File("account.txt");
            if (!file.exists()){
                file.createNewFile();
            }

            RandomAccessFile raf = new RandomAccessFile(file,"rw");

            boolean found = false;

            while (raf.getFilePointer() < raf.length()){
                nameNumberString = raf.readLine();

                index = nameNumberString.indexOf('-');
                int index2 = nameNumberString.indexOf('_');
                int index3 = nameNumberString.indexOf('|');
                String acType = nameNumberString.substring(0,index);
                long acnumber = Long.parseLong(nameNumberString.substring(index+1,index2));
                String acname = nameNumberString.substring(index2+1,index3);
                long acbalance = Long.parseLong(nameNumberString.substring(index3+1));

               // String typee = nameNumberString.substring(index2+1);

                counter++;

                }
            System.out.println("Total active accounts "+counter);

            }

         catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accountInfo(String name) {
        try{
            String nameNumberString;
            int index;

            File file = new File("account.txt");
            if (!file.exists()){
                file.createNewFile();
            }

            RandomAccessFile raf = new RandomAccessFile(file,"rw");

            while (raf.getFilePointer() < raf.length()){
                nameNumberString = raf.readLine();

                index = nameNumberString.indexOf('-');
                int index2 = nameNumberString.indexOf('_');
                int index3 = nameNumberString.indexOf('|');
                String acType = nameNumberString.substring(0,index);
                long acnumber = Long.parseLong(nameNumberString.substring(index+1,index2));
                String acname = nameNumberString.substring(index2+1,index3);
                long acbalance = Long.parseLong(nameNumberString.substring(index3+1));

              if (acname.equals(name) ){

                  System.out.println("account id :"+acnumber+"\nAccount type :"+acType+
                          "\nAccount Owner :"+acname+"\nAccount balance :"+acbalance);

              }

            }


        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void depositMoney(String name, long ammount) {
        try {
            String nameNumberString = null;
            int index;
            File file = new File("account.txt");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            while (raf.getFilePointer() < raf.length()) {
                nameNumberString = raf.readLine();
                index = nameNumberString.indexOf('-');
                int index2 = nameNumberString.indexOf('_');
                int index3 = nameNumberString.indexOf('|');
                String acType = nameNumberString.substring(0, index);
                long acnumber = Long.parseLong(nameNumberString.substring(index + 1, index2));
                String acname = nameNumberString.substring(index2 + 1, index3);
                long acbalance = Long.parseLong(nameNumberString.substring(index3 + 1));
                if (acname.equals(name)) {
                    found = true;
                    break;
                }
            }

            if (found == true) {

                File tmpFile = new File("temp.txt");
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

                raf.seek(0);


                while (raf.getFilePointer() < raf.length()) {
                    nameNumberString = raf.readLine();

                    index = nameNumberString.indexOf('-');
                    int index2 = nameNumberString.indexOf('_');
                    int index3 = nameNumberString.indexOf('|');
                    String acType = nameNumberString.substring(0, index);
                    long acnumber = Long.parseLong(nameNumberString.substring(index + 1, index2));
                    String acname = nameNumberString.substring(index2 + 1, index3);
                    long acbalance = Long.parseLong(nameNumberString.substring(index3 + 1));

               //

                    if (acname.equals(name)) {

                        long netBalance = acbalance + ammount;
                        nameNumberString
                                = acType+"-"+ String.valueOf(acnumber) +"_"+
                                acname+ "|"+String.valueOf(netBalance);
                        System.out.println(ammount+" Dollar added at " + acname);


                    }


                    tmpraf.writeBytes(nameNumberString);


                    tmpraf.writeBytes(System.lineSeparator());
                }


                raf.seek(0);
                tmpraf.seek(0);


                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }


                raf.setLength(tmpraf.length());


                tmpraf.close();
                raf.close();


                tmpFile.delete();


            }


            else {


                raf.close();


                System.out.println(" No user found at this name ");
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }

        catch (NumberFormatException nef) {
            System.out.println(nef);
        }

    }

    @Override
    public void withdrawMoney(String name, long amount) {
        try {


            String nameNumberString = null;
            int index;
            File file = new File("account.txt");
            RandomAccessFile raf
                    = new RandomAccessFile(file, "rw");
            boolean found = false;


            while (raf.getFilePointer() < raf.length()) {
                nameNumberString = raf.readLine();
                index = nameNumberString.indexOf('-');
                int index2 = nameNumberString.indexOf('_');
                int index3 = nameNumberString.indexOf('|');
                String acType = nameNumberString.substring(0, index);
                long acnumber = Long.parseLong(nameNumberString.substring(index + 1, index2));
                String acname = nameNumberString.substring(index2 + 1, index3);
                long acbalance = Long.parseLong(nameNumberString.substring(index3 + 1));
                if (acname.equals(name)) {
                    found = true;
                    break;
                }
            }

            if (found == true) {

                File tmpFile = new File("temp.txt");


                RandomAccessFile tmpraf
                        = new RandomAccessFile(tmpFile, "rw");

                raf.seek(0);


                while (raf.getFilePointer() < raf.length()) {


                    nameNumberString = raf.readLine();

                    index = nameNumberString.indexOf('-');
                    int index2 = nameNumberString.indexOf('_');
                    int index3 = nameNumberString.indexOf('|');
                    String acType = nameNumberString.substring(0, index);
                    long acnumber = Long.parseLong(nameNumberString.substring(index + 1, index2));
                    String acname = nameNumberString.substring(index2 + 1, index3);
                    long acbalance = Long.parseLong(nameNumberString.substring(index3 + 1));




                    if (acname.equals(name)) {

                        if (amount<acbalance) {

                            long netBalance = acbalance - amount;
                            nameNumberString
                                    = acType + "-" + String.valueOf(acnumber) + "_" +
                                    acname + "|" + String.valueOf(netBalance);
                            System.out.println(amount+" Dollar withdrawed at "+acname);
                        }else{
                            System.out.println("insufficient balance");
                        }
                    }
                    tmpraf.writeBytes(nameNumberString);

                    tmpraf.writeBytes(System.lineSeparator());
                }

                raf.seek(0);
                tmpraf.seek(0);

                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }

                raf.setLength(tmpraf.length());

                tmpraf.close();
                raf.close();

                tmpFile.delete();


            }

            else {

                raf.close();

                System.out.println(" No user found at this name ");
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }

        catch (NumberFormatException nef) {
            System.out.println(nef);
        }
    }

    @Override
    public void transferMoney(String myAccount, long money, String clientAccount) {
        withdrawMoney(myAccount,money);
        depositMoney(clientAccount,money);
        System.out.println("Transfer Successfull");
    }

    @Override
    public void updateBalanceWithInterestRate() {
        double rate = interestRate();

        try {
            String nameNumberString = null;
            int index;
            File file = new File("account.txt");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = true;

         /*   while (raf.getFilePointer() < raf.length()) {
                nameNumberString = raf.readLine();
                index = nameNumberString.indexOf('-');
                int index2 = nameNumberString.indexOf('_');
                int index3 = nameNumberString.indexOf('|');
                String acType = nameNumberString.substring(0, index);
                long acnumber = Long.parseLong(nameNumberString.substring(index + 1, index2));
                String acname = nameNumberString.substring(index2 + 1, index3);
                long acbalance = Long.parseLong(nameNumberString.substring(index3 + 1));
                if (acname.equals(name)) {
                    found = true;
                    break;
                }
            } */

            if (found == true) {

                File tmpFile = new File("temp.txt");
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

                raf.seek(0);


                while (raf.getFilePointer() < raf.length()) {
                    nameNumberString = raf.readLine();

                    index = nameNumberString.indexOf('-');
                    int index2 = nameNumberString.indexOf('_');
                    int index3 = nameNumberString.indexOf('|');
                    String acType = nameNumberString.substring(0, index);
                    long acnumber = Long.parseLong(nameNumberString.substring(index + 1, index2));
                    String acname = nameNumberString.substring(index2 + 1, index3);
                    long acbalance = Long.parseLong(nameNumberString.substring(index3 + 1));

                    //

                    if (acname.equals(acname)) {

                        double interest = ((double)acbalance * rate);
                        long Interest = (long)interest;
                        long net = Interest + acbalance;

                        long netBalance = acbalance + net;
                        nameNumberString
                                = acType+"-"+ String.valueOf(acnumber) +"_"+
                                acname+ "|"+String.valueOf(netBalance);
                    // .   System.out.println(ammount+" Dollar added at " + acname);


                    }


                    tmpraf.writeBytes(nameNumberString);


                    tmpraf.writeBytes(System.lineSeparator());
                }
                raf.seek(0);
                tmpraf.seek(0);
                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }
                raf.setLength(tmpraf.length());
                tmpraf.close();
                raf.close();
                tmpFile.delete();

            }
            else {
                raf.close();
              //  System.out.println(" No user found at this name ");
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }

        catch (NumberFormatException nef) {
            System.out.println(nef);
        }



    }

    public static double interestRate(){
        double rate = 0;
        try{
            String nameNumberString;
            int index;


            File file = new File("rate.txt");

            RandomAccessFile raf = new RandomAccessFile(file,"rw");

            while (raf.getFilePointer() < raf.length()){
                nameNumberString = raf.readLine();

                index = nameNumberString.indexOf('-');

                rate = Double.parseDouble(nameNumberString.substring(0,index));

            }



        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rate;

    }

}
