import Clients.AuthClient;
import Entity.User;
import Helpers.FirebaseInitialize;
import Models.CreateUser;
import Models.LoginUser;

public class MainCLI {
    public static void main(String[] args)
    {
        //Ensure that Firebase is initialized.
        FirebaseInitialize.initDatabase();

        AuthClient client = new AuthClient();
        CreateUser createUser = new CreateUser("xavieroyj", "trickster123", "Xavier", "Ong", "+6586918172");
        LoginUser loginUser = new LoginUser("xavieroyj", "trickster123");
        User user = client.Login(loginUser);
        System.out.println(user.getId());
        //client.Register(createUser);
    }
}
