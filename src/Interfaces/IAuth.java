package Interfaces;

public interface IAuth {
    public void Login(String username, String password);
    public void Register(String Username, String Email, String password);
    public void ResetPassword(String email);
}
