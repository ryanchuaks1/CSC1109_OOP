module com.rjdxbanking.rjdxbank {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires com.google.api.apicommon;
    requires firebase.admin;
    requires password4j;
    requires google.cloud.core;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires html2pdf;
    requires twilio;
    requires kernel;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens com.rjdxbanking.rjdxbank to javafx.fxml;

    exports com.rjdxbanking.rjdxbank;
    exports com.rjdxbanking.rjdxbank.Clients;
    exports com.rjdxbanking.rjdxbank.Entity;
    exports com.rjdxbanking.rjdxbank.Models;
    exports com.rjdxbanking.rjdxbank.Controllers;

    opens com.rjdxbanking.rjdxbank.Entity to google.cloud.firestore, com.fasterxml.jackson.databind;
    opens com.rjdxbanking.rjdxbank.Models to google.cloud.firestore;
    opens com.rjdxbanking.rjdxbank.Controllers to javafx.fxml;
}