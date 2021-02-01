package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}