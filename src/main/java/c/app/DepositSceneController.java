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

public class DepositSceneController implements Initializable {

    private CustomerDbUtil customerDbUtil;

    @FXML
    private Label depositValueLabel;

    @FXML
    private Label errorLabel;

    private float moneyToDeposit;

    private Client client;
    private Compte compte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDbUtil=new CustomerDbUtil();
    }

    public float getMoneyToDeposit(){
        return this.moneyToDeposit;
    }

    public void setMoneyToDeposit(float moneyToDeposit) {
        this.moneyToDeposit = moneyToDeposit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public Compte getCompte(){
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public void setDepositValueLabel(String text){
        this.depositValueLabel.setText(text);
    }

    //TODO: TESTING
    public void processDeposit(ActionEvent event){

        // get value to be added without the '+' symbol
        float buttonPressed = Float.parseFloat(((Button) event.getSource()).getText().substring(1));
        float newMoneyToDeposit = getMoneyToDeposit()+buttonPressed;


        if (newMoneyToDeposit <= 2000){
            setMoneyToDeposit(newMoneyToDeposit);
            setDepositValueLabel(Helper.formatCurrency(newMoneyToDeposit));
        } else {
            displayError();
        }
    }

    public void reset(){
        this.moneyToDeposit=0;
        depositValueLabel.setText(Helper.formatCurrency(getMoneyToDeposit()));
    }

    public void deposit(ActionEvent event){
        // previous balance + new deposit
        float updatedCustomerBalance = (float) (getCompte().getSolde()+getMoneyToDeposit());

        // update database balance
        customerDbUtil.updateBalance(updatedCustomerBalance, (int) getClient().getNum());

        // update currently logged Customer's balance inside the app
        getCompte().setSolde(updatedCustomerBalance);

        switchToBankingScene(event);
    }

    //boiler
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
    // Customers can only deposit up to 2,000 at once
    public void displayError() {
        this.errorLabel.setText("You cannot deposit more than 2,000!");
    }

}
