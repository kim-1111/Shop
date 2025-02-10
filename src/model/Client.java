/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Payable;

/**
 *
 * @author YixinHuang
 */
public class Client extends Person implements Payable {

    private int memberid;
    private Amount balance;
    
    final int MEMBER_ID = 456;
    final Amount BALANCE = new Amount(50.00);

    public Client(int memberid, Amount balance, String nombre) {
        super(nombre);
        this.memberid = memberid;
        this.balance = balance;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public int getMemberid() {
        return this.MEMBER_ID;
    }

    public Amount getBalance() {
        return balance;
    }
    
    @Override
    public String toString() {
        return "Client{" + "memberid=" + memberid + ", balance=" + balance + ", MEMBER_ID=" + MEMBER_ID + ", BALANCE=" + BALANCE + '}';
    }


    @Override
    public boolean pay(Amount amount) {

       double saldoFinal = balance.getValue() - amount.getValue();
       
        if (saldoFinal <= 0) {
            System.out.println("Saldo insuficiente, el cliente debe " + saldoFinal);
            return false;
        } else{
            setBalance(new Amount(saldoFinal));
            return true;
        }

    }

}
