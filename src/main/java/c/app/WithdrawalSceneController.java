package c.app;

import M.Client;
import M.Compte;
import c.app.data.CustomerDbUtil;
import c.app.data.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawalSceneController implements Initializable {

    private Client client;
    private Compte compte;
    private CustomerDbUtil customerDbUtil;

    @FXML
    Label withdrawalValueLabel,errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         customerDbUtil = new CustomerDbUtil();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client customer){
        this.client = client;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public String getWithdrawalValueLabel(){
        return this.withdrawalValueLabel.getText(); //something like £2500.00
    }

    public void setWithdrawalValueLabel(String number){ //TODO: PROBLEM
        this.withdrawalValueLabel.setText(number);
    }

    public void setErrorLabel(String text) {
        this.errorLabel.setText(text);
    }

    public CustomerDbUtil getClientDbUtil() {
        return customerDbUtil;
    }

    public void processNumbers(ActionEvent event) {

        // get Button pressed
        String buttonDigit = ((Button) event.getSource()).getText();

        //  get withdrawal label without '£' symbol in int
        String currentWithdrawalAmount = Helper.toCleanNumber(getWithdrawalValueLabel().substring(1));

        if (Integer.parseInt(currentWithdrawalAmount) == 0) {
            setWithdrawalValueLabel("£" + buttonDigit + ".00"); //TODO: PROBLEM
        } else {
            String newWithdrawalAmount = currentWithdrawalAmount + buttonDigit;

            if (Integer.parseInt(newWithdrawalAmount) < 2500) {
                setWithdrawalValueLabel("£" + newWithdrawalAmount + ".00");

            } else {
                setErrorLabel("YOU CAN ONLY WITHDRAW UP TO £2500.00");
                setWithdrawalValueLabel("£2500.00");
            }
        }
    }

    public void withdraw(ActionEvent event){

        float currentWithdrawalAmount = Float.parseFloat(getWithdrawalValueLabel().substring(1));
        float customerFundsAfterWithdrawal = (float) (getCompte().getSolde()-currentWithdrawalAmount);


        if (currentWithdrawalAmount <= getCompte().getSolde()){

            // set Customer balance inside the app
            getCompte().setSolde(customerFundsAfterWithdrawal);

            // set Customer balance inside Database
            getClientDbUtil().updateBalance(customerFundsAfterWithdrawal, (int) getClient().getNum());

            // switch scene
            switchToBankingScene(event);
        } else {
            setErrorLabel("NOT ENOUGH FUNDS");
        }
    }
    public void back(ActionEvent event){
        switchToBankingScene(event);
    }
    public void reset(){
        setWithdrawalValueLabel("£0.00");
    }

    public void switchToBankingScene(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/c/app/BankingScene.fxml"));
            Parent root = loader.load();

            BankingSceneController bankingSceneController=loader.getController();

            bankingSceneController.setClient(getClient());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
