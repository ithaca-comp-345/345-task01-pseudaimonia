package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        //Positive Balance Case
        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount1.getBalance());

        //Empty Balance Case
        BankAccount bankAccount2 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount2.getBalance());

        //Positive Balance Correct Decimal Case
        BankAccount bankAccount4 = new BankAccount("a@b.com", 200.58);
        assertEquals(200.58, bankAccount4.getBalance());

    }

    @Test
    void withdrawTest() throws Exception{
        //positive balance - valid withdrawal and invalid withdrawals (digits)
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        bankAccount.withdraw(0.50);
        assertEquals(99.50, bankAccount.getBalance());
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(10.501));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-1));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        //balance of zero - all invalid withdrawals
        BankAccount bankAccount1 = new BankAccount("a@b.com", 0);
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.withdraw(-10));
        assertEquals(0, bankAccount1.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount1.withdraw(1));
        assertEquals(0, bankAccount1.getBalance());
    }

    @Test 
    void depositTest() throws Exception{
        //positive deposit (all valid digits)
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);
        assertEquals(300,bankAccount.getBalance());
        bankAccount.deposit(0.1);
        assertEquals(300.1,bankAccount.getBalance());
        bankAccount.deposit(0.01);
        assertEquals(300.11,bankAccount.getBalance());

        //deposit of zero
        BankAccount bankAccount1 = new BankAccount("a@b.com", 0);
        bankAccount1.deposit(100);
        assertEquals(100,bankAccount1.getBalance());
        bankAccount1.deposit(0.1);
        assertEquals(100.1,bankAccount1.getBalance());
        bankAccount1.deposit(0.05);
        assertEquals(100.15,bankAccount1.getBalance());

        //invalid deposits (negative and digits)
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-10));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-10.1));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-10.15));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(1.151));
    }
    @Test
    void transferTest() throws Exception{

        BankAccount lender = new BankAccount("a@b.com",1000);
        BankAccount recipient = new BankAccount("ab@c.com",0);

        //transferer and recipient are the same account
        assertThrows(IllegalArgumentException.class, () -> lender.transfer(lender,100));

        //recipient is null
        assertThrows(IllegalArgumentException.class, () -> lender.transfer(null,100));


        //invalid amounts
        assertThrows(InsufficientFundsException.class, () -> lender.transfer(recipient,1500));
        assertEquals(0,recipient.getBalance());
        assertEquals(1000,lender.getBalance());
        assertThrows(IllegalArgumentException.class, () -> lender.transfer(recipient,10.501));
        assertEquals(0,recipient.getBalance());
        assertEquals(1000,lender.getBalance());
        assertThrows(IllegalArgumentException.class, () -> lender.transfer(recipient,-10));
        assertEquals(0,recipient.getBalance());
        assertEquals(1000,lender.getBalance());
        assertThrows(IllegalArgumentException.class, () -> lender.transfer(recipient,-10.5));
        assertEquals(0,recipient.getBalance());
        assertEquals(1000,lender.getBalance());
        assertThrows(IllegalArgumentException.class, () -> lender.transfer(recipient,-10.50));
        assertEquals(0,recipient.getBalance());
        assertEquals(1000,lender.getBalance());

        //valid transfers
        lender.transfer(recipient,100);
        assertEquals(900,lender.getBalance());
        assertEquals(100,recipient.getBalance());
        lender.transfer(recipient,0.1);
        assertEquals(899.9,lender.getBalance());
        assertEquals(100.1,recipient.getBalance());
        lender.transfer(recipient,0.05);
        assertEquals(899.85,lender.getBalance());
        assertEquals(100.15,recipient.getBalance());

    }

    @Test
    void isAmountValidTest(){
        //negatives (with correct and incorrect digits after the decimal)
        assertFalse(BankAccount.isAmountValid(-1));
        assertFalse(BankAccount.isAmountValid(-1.1));
        assertFalse(BankAccount.isAmountValid(-1.11));
        assertFalse(BankAccount.isAmountValid(-1.111));

        //Smallest number of invalid digits after the decimal
        assertFalse(BankAccount.isAmountValid(0.666));

        //Zero case
        assertTrue(BankAccount.isAmountValid(0));

        //All valid digit amounts after the decimal
        assertTrue(BankAccount.isAmountValid(0.6));
        assertTrue(BankAccount.isAmountValid(0.66));

        //Positive case with significant number of digits
        assertTrue(BankAccount.isAmountValid(10000));
    }

    @Test
    void isEmailValidTest(){

        //Invalid Length Cases
        assertFalse(BankAccount.isEmailValid(""));
        assertFalse(BankAccount.isEmailValid("a@.com"));
        assertFalse(BankAccount.isEmailValid("@.com"));

        //Invalid Duplicates in both areas
        assertFalse(BankAccount.isEmailValid("..@@..@@"));
        assertFalse(BankAccount.isEmailValid(".@.@.@.@"));

        //Invalid Top-Level Domain Cases
        assertFalse(BankAccount.isEmailValid("a@b.b"));
        assertFalse(BankAccount.isEmailValid("a@b.comm"));
        assertFalse(BankAccount.isEmailValid("a@b.123"));

        //Invalid Domain Cases
        assertFalse(BankAccount.isEmailValid("a@te#st.com"));
        assertFalse(BankAccount.isEmailValid("a.b@test"));
        assertFalse(BankAccount.isEmailValid("a@test..com"));

        //Valid Domain Cases w/ Special Characters
        assertTrue(BankAccount.isEmailValid("abc@te_st.com"));
        assertTrue(BankAccount.isEmailValid("abc@te-st.com"));

        //Invalid Email Usernames
        assertFalse(BankAccount.isEmailValid("c-@test.com"));
        assertFalse(BankAccount.isEmailValid("c_@test.com"));
        assertFalse(BankAccount.isEmailValid(".abc@m.com"));
        assertFalse(BankAccount.isEmailValid("@abc@m.com"));
        assertFalse(BankAccount.isEmailValid("ab#cd@m.com"));

        //Valid Email Usernames
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertTrue(BankAccount.isEmailValid("abc123@test.com"));
        assertTrue(BankAccount.isEmailValid("ab-cd@test.com"));
        assertTrue(BankAccount.isEmailValid("ab_cd@test.com"));
        assertTrue(BankAccount.isEmailValid("ab.cd@test.com"));
    }

    //I addressed the comments below
    //Where the domain side of the @ is completely missing
    //Where the .com (or similar) is two letters or less

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly

        //invalid email
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        //negative balance
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
        //incorrect digits
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.501));
    }

}