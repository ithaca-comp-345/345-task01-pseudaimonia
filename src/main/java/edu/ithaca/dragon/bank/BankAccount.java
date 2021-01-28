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
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
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
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        String[] splitEmail = email.split("@");
        String[] prefix = splitEmail[0].split("(?!^)");
        String[] domain = splitEmail[1].split("(?!^)");

        String[] validChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split("(?!^)");
        String[] validSymbolPrefix = "_.-".split("(?!^)");
        String[] validSymbolDomain = "-".split("(?!^)");
        String validWholePrefix = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_.-";
        for (int x = 0; x < prefix.length; x++){
            if (!(validWholePrefix.contains(prefix[x]))){
                return false;
            }
            for (int y = 0; y < validSymbolPrefix.length; y++){
                if (prefix[0].equals(validSymbolPrefix[y]) == true){
                    return false;
                }
                if (prefix[prefix.length - 1].equals(validSymbolPrefix[y]) == true){
                    return false;
                }
                if (prefix[x].equals(validSymbolPrefix[y]) == true){
                    if (prefix[x+1].equals(validSymbolPrefix[y]) == true){
                        return false;
                    }
                }
            }
        }


    }

}
