package c.app;

import M.Client;
import M.Compte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class BankingSceneController {

    @FXML
    private Label nameLabel,balanceLabel,idLabel;

    private Client client;
    private Compte compte;


    // TODO: find a way to remove boiler code when switching scenes ++
    public void deposit(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/c/app/DepositScene.fxml"));
            Parent root = loader.load();

            // send customer info to next scene
            DepositSceneController depositSceneController=loader.getController();
            depositSceneController.setClient(getClient());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withdrawal(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/c/app/WithdrawalScene.fxml"));
            Parent root = loader.load();

            // send customer info to next scene
            WithdrawalSceneController withdrawalSceneController=loader.getController();
            withdrawalSceneController.setClient(getClient());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNameLabel(String text) {
        this.nameLabel.setText(text);
    }

    public void setBalanceLabel(String text) {
        this.balanceLabel.setText(text);
    }

    public void setIdLabel(String text) {
        this.idLabel.setText(text);
    }

    public void transfer(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/c/app/TransferScene.fxml"));
            Parent root = loader.load();

            // send customer info to next scene
            TransferSceneController transferSceneController=loader.getController();
            transferSceneController.setClient(getClient());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToLogInScene(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/c/app/LogInScene.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setClient(Client client){
        this.client = client;
        setNameLabel("Name: "+getClient().getNom()+" "+getClient().getPrenom());
        //setBalanceLabel("Balance: "+ Helper.formatCurrency((float) getCompte().getSolde()));
        setIdLabel("Identification number: "+ getClient().getNum());
    }

    public Client getClient(){
        return this.client;
    }

    public Compte getCompte(){
        return this.compte;
    }


}
