package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            startingBalance = reduceToTwoDecimalPlaces(startingBalance);
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double reduceToTwoDecimalPlaces(double amount){
        String strAmount = Double.toString(amount);
        String[] strAmountDigits = strAmount.split("\\.");
        if(strAmountDigits[1].length()>2){
            strAmount = strAmountDigits[0]+"."+strAmountDigits[1].substring(0,2);
            amount = Double.parseDouble(strAmount);
        }
        return amount;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws Exception{
        amount = reduceToTwoDecimalPlaces(amount);
        if(amount<=0){
            throw new Exception("Invalid withdraw amount");
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }  


    public static boolean isEmailValid(String email){
        if ((email.indexOf('@') == -1) || (email.indexOf('.') == -1)){
            return false;
        }
        if ((email.indexOf('@')) != email.lastIndexOf('@')){
            return false;
        }

        String[] splitEmail = email.split("@");
        String[] prefix = splitEmail[0].split("(?!^)");
        String[] domain = splitEmail[1].split("(?!^)");
        if (prefix.length <= 1){
            return false;
        }
        if (domain.length <= 1){
            return false;
        }

        //prefix
        String prefixValidChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String prefixValidNum = "0123456789";
        String prefixValidSym = "_.-";
        
        for (int i = 0; i < prefix.length; i++){
            if (!((prefixValidChar.contains(prefix[i])) || (prefixValidNum.contains(prefix[i])) || (prefixValidSym.contains(prefix[i])))){
                return false;
            }
        }
        if (!((prefixValidChar.contains(prefix[0])) || (prefixValidNum.contains(prefix[0])))){
            return false;
        }
        if (!((prefixValidChar.contains(prefix[prefix.length - 1])) || (prefixValidNum.contains(prefix[prefix.length - 1])))){
            return false;
        }
        for (int i = 0; i < prefix.length; i++){
            if ((prefixValidSym.contains(prefix[i])) && (prefixValidSym.contains(prefix[i + 1]))){
                return false;
            }
        }
        
        //domain
        String domainValidChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String domainValidNum = "0123456789";
        String domainValidSym = "_.-";
        
        int periodCount = 0;
        for (int i = 0; i < domain.length; i++){
            if (!((domainValidChar.contains(domain[i])) || (domainValidNum.contains(domain[i])) || (domainValidSym.contains(domain[i])))){
                return false;
            }
            if (domain[i].equals(".")){
                periodCount++;
            }
        }
        if (periodCount != 1){
            return false;
        }
        
        String[] splitDomain = splitEmail[1].split("\\.");
        String[] host = splitDomain[0].split("(?!^)");
        String[] TLD = splitDomain[1].split("(?!^)");
        if (host.length <= 1){
            return false;
        }
        if (TLD.length < 2){
            return false;
        }

        return true;
    }

}
