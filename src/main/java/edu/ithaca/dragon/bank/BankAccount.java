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
        email = email.toLowerCase();
        //minimum required length
        if (email.length() < 6){
            return false;
        }
        //if email does not contain @ or period
        if ((email.indexOf('@') == -1) || (email.indexOf('.') == -1)){
            return false;
        }
        //if email contains multiple @
        if ((email.indexOf('@')) != email.lastIndexOf('@')){
            return false;
        }
        //if first character is an underscore, period, dash, or @
        if ((email.indexOf('_') == 0) || (email.indexOf('.') == 0) || (email.indexOf('-') == 0) || (email.indexOf('@') == 0)){
            return false;
        }
        //if period is right after @
        if (email.indexOf('@') == (email.indexOf('.') - 1)){
            return false;
        }
        else {
            //PREFIX
            String[] splitEmail = email.split("@");
            String prefix = splitEmail[0];

            //check for unfollowed punctuation
            if ((prefix.charAt(prefix.length() - 1) == 45) || (prefix.charAt(prefix.length() - 1) == 46) || (prefix.charAt(prefix.length() - 1) == 95)){
                return false;
            }
            for (int i = 0; i < prefix.length(); i++){
                //check for invalid characters in prefix
                if ((prefix.charAt(i) != 45) && (prefix.charAt(i) != 46) && (prefix.charAt(i) != 95) && 
                (!((prefix.charAt(i) >= 97) && (prefix.charAt(i) <= 122))) && (!((prefix.charAt(i) >= 47) && (prefix.charAt(i) <= 57)))){
                    return false;
                }
                //check for sequential punctuation
                if ((prefix.charAt(i) == 45) || (prefix.charAt(i) == 46) || (prefix.charAt(i) == 95)){
                    if ((prefix.charAt(i-1) == 45) || (prefix.charAt(i-1) == 46) || (prefix.charAt(i-1) == 95)){
                        return false;
                    }
                }
            }

            //DOMAIN
            //checks for existence of a period in the domain
            if (!(splitEmail[1].contains("."))){
                return false;
            }
            //checks for one period in domain
            if (splitEmail[1].indexOf(".") != splitEmail[1].lastIndexOf(".")){
                return false;
            }
            String[] splitDomain = splitEmail[1].split("\\.");
            String host = splitDomain[0];
            System.out.println(host);

            //check for invalid characters in host
            for (int i = 0; i < host.length(); i++){
                if ((host.charAt(i) != 45) && (host.charAt(i) != 95) && (!((host.charAt(i) >= 97) && (host.charAt(i) <= 122)))){
                    return false;
                }
            }

            String TLD = splitDomain[1];
            //check for proper top-level domain
            if (!(TLD.equals("com"))){
                return false;
            }

            return true;
        }



        /**
        if ((email.indexOf('@') == -1) || (email.indexOf('.') == -1)){
            return false;
        }
        if ((email.indexOf('@')) != email.lastIndexOf('@')){
            return false;
        }
        if ((email.indexOf('.')) != email.lastIndexOf('.')){
            return false;
        }

        String[] splitEmail = email.split("@");
        String[] prefix = splitEmail[0].split("(?!^)");
        String[] domain = splitEmail[1].split("(?!^)");
        if (prefix.length < 1){
            return false;
        }
        if (domain.length < 1){
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
        System.out.println(host.length);
        String[] TLD = splitDomain[1].split("(?!^)");
        System.out.println(TLD.length);
        if (host.length < 1){
            return false;
        }
        if ((host.length == 1) && (host[0].equals(" "))){

        }
        if (TLD.length < 2){
            return false;
        }

        return true;
        */
    }

}
