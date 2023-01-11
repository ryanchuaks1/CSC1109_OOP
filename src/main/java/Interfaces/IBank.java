package Interfaces;

public interface IBank {
    public void Transfer(String receiverBankNo, double amount);
    public void CloseAccount();
    public void Withdraw(double amount);
    public void Deposit(double amount);
    public void GetBalance();
}
