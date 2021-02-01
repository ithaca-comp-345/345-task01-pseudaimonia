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

        //Negative Balance Case
        BankAccount bankAccount3 = new BankAccount("a@b.com", -10);
        assertEquals(-10, bankAccount3.getBalance());

        //Positive Balance Case
        BankAccount bankAccount4 = new BankAccount("a@b.com", 200.58);
        assertEquals(200.58, bankAccount4.getBalance());
    }

    @Test
    void withdrawTest() throws Exception{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        bankAccount.withdraw(10.501);
        assertEquals(89.50, bankAccount.getBalance());
        assertThrows(Exception.class, () -> bankAccount.withdraw(300));

        BankAccount bankAccount1 = new BankAccount("a@b.com", 0);
        assertThrows(Exception.class, () -> bankAccount1.withdraw(-10));
        assertEquals(0, bankAccount1.getBalance());
        assertThrows(Exception.class, () -> bankAccount1.withdraw(10));

        BankAccount bankAccount2 = new BankAccount("a@b.com", -5);
        assertThrows(Exception.class, () -> bankAccount2.withdraw(-10));
        assertEquals(-5, bankAccount2.getBalance());
        assertThrows(Exception.class, () -> bankAccount2.withdraw(1));
        assertThrows(Exception.class, () -> bankAccount2.withdraw(-1));
        assertThrows(Exception.class, () -> bankAccount2.withdraw(0));


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
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}