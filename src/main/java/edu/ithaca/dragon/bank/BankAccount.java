package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)){
            this.email = email;
            this.balance = startingBalance;
        }
        else if (!isEmailValid(email)){
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        else{throw new IllegalArgumentException("Invalid amount for starting balance");}
    }
    //this was a helper function I wrote for the withdraw function before I realized we were going to be creating isAmountValid 
    /*
    public double reduceToTwoDecimalPlaces(double amount){
        String strAmount = Double.toString(amount);
        String[] strAmountDigits = strAmount.split("\\.");
        if(strAmountDigits[1].length()>2){
            strAmount = strAmountDigits[0]+"."+strAmountDigits[1].substring(0,2);
            amount = Double.parseDouble(strAmount);
        }
        return amount;
    }*/

    /**
     * returns true for positive double amounts with two digits after the decimal place
     */
    public static boolean isAmountValid(double amount){
        if(amount<0){
            return false;
        }
        String strAmount = Double.toString(amount);
        String[] strAmountDigits = strAmount.split("\\.");
        if(strAmountDigits.length>1 && strAmountDigits[1].length()>2){
            return false;
        }
        return true;
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
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Invalid withdraw amount");
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }  

    public void deposit (double amount) throws Exception{
        //TODO
    }

    public void transfer (BankAccount recipient, double amount) throws Exception{
        //TODO
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
    }

}
