package Clients;

public class BankIdentificationClient {

    public Boolean CheckBIN(String BIN) {
        if (BIN.equals("622925")) {
            return true;
        }
        else {
            return false;
        }
    }
}