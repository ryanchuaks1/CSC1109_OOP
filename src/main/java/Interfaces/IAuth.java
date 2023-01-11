package Interfaces;

import Models.User;

public interface IAuth {
    public User Login(String username, String password);
    public void Register(String Username, String Email, String password);
    public void ResetPassword(String email);
}
