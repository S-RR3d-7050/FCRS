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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransferSceneController implements Initializable {

    @FXML
    private Button transactionButton, idButton;

    @FXML
    Label transactionValueLabel, receiverIdLabel;

    @FXML
    Label errorLabel;

    private CustomerDbUtil customerDbUtil;

    private Client client;
    private Compte compte;

    private ImageView upGreenArrow, downGreenArrow, upGreyArrow, downGreyArrow;


    // true -> keyboard input goes to Transaction value
    // false -> keyboard input goes to Receiver ID
    private boolean labelUsed;


    @Override
    public void initialize(URL url, ResourceBundle resources) {

        labelUsed = true;
        upGreenArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/upGreenArrow.png")));
        downGreenArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/downGreenArrow.png")));
        upGreyArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/upGreyArrow.png")));
        downGreyArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/downGreyArrow.png")));

        customerDbUtil= new CustomerDbUtil();

        transactionButton.setGraphic(upGreenArrow);
        idButton.setGraphic(downGreyArrow);

    }

    public CustomerDbUtil getClientDbUtil() {
        return customerDbUtil;
    }

    public Client getClient() {
        return client;
    }

    public String getTransactionValueLabel(){
        return this.transactionValueLabel.getText();
    }
    public int getReceiverIdLabel(){
        return Integer.parseInt(this.receiverIdLabel.getText());
    }
    public void setTransactionValueLabel(String number){ //TODO: PROBLEM
        this.transactionValueLabel.setText(number);
    }

    public boolean getLabelUsed() {
        return labelUsed;
    }

    public void setLabelUsed(boolean labelUsed) {
        this.labelUsed = labelUsed;
    }

    public void setReceiverIdLabel(String receiverIdLabel) {
        this.receiverIdLabel.setText(receiverIdLabel);
    }

    public void setErrorLabel(String text){
        this.errorLabel.setText(text);
    }

    public void processNumbers(ActionEvent event) {

        // get Button pressed
        String buttonDigit = ((Button) event.getSource()).getText();

        if (getLabelUsed()){

            //  get withdrawal label without '£' symbol in int
            String currentWithdrawalAmount = Helper.toCleanNumber(getTransactionValueLabel().substring(1));

            if (Integer.parseInt(currentWithdrawalAmount) == 0) {
                setTransactionValueLabel("£" + buttonDigit + ".00"); //TODO: PROBLEM
            } else {
                String newWithdrawalAmount = currentWithdrawalAmount + buttonDigit;

                if (Integer.parseInt(newWithdrawalAmount) < 2500) {
                    setTransactionValueLabel("£" + newWithdrawalAmount + ".00");

                } else {
                    setErrorLabel("YOU CAN ONLY TRANSFER UP TO £2500.00");
                    setTransactionValueLabel("£2500.00");
                }
            }
        } else {
            String currentIdNumber = String.valueOf(getReceiverIdLabel());
            if ((currentIdNumber + buttonDigit).length() > 3) {
                setErrorLabel("ID CANNOT BE HIGHER THAN 999!");
                setReceiverIdLabel("999");
            } else if (Integer.parseInt(currentIdNumber)==0) {
                setReceiverIdLabel(buttonDigit);
            } else {
                setReceiverIdLabel(currentIdNumber+buttonDigit);
            }
        }
    }

    public void back(ActionEvent event){
        switchToBankingScene(event);
    }

    public void reset() {

        if (getLabelUsed()) {
            setTransactionValueLabel("£0.00");
        } else {
            setReceiverIdLabel("0");
        }
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public void transfer(ActionEvent event) {
        float currentTransferAmount = Float.parseFloat(getTransactionValueLabel().substring(1));
        float customerFundsAfterWithdrawal = (float) (getCompte().getSolde() - currentTransferAmount);

        if (!(getReceiverIdLabel() == getClient().getNum())) {

            if (getClientDbUtil().customerExists(getReceiverIdLabel())) {

                if (currentTransferAmount <= getCompte().getSolde()) {

                    // set Sender Customer balance inside the app
                    getCompte().setSolde(customerFundsAfterWithdrawal);

                    // set Sender Customer balance inside Database
                    getClientDbUtil().updateBalance(customerFundsAfterWithdrawal, (int) getClient().getNum());

                    // set Receiver Customer balance inside Database
                    getClientDbUtil().receiveTransfer(currentTransferAmount, getReceiverIdLabel());

                    // switch scene
                    switchToBankingScene(event);
                } else {
                    setErrorLabel("NOT ENOUGH FUNDS!");
                }
            } else {
                setErrorLabel("CUSTOMER WITH THIS ID DOES NOT EXIST!");
            }
        } else {
            setErrorLabel("YOU CANNOT SEND MONEY TO YOURSELF! CHANGE ID!");
        }
    }

    private Compte getCompte() {
        return compte;
    }

    public void switchToTransactionLabel(){
        if (getLabelUsed()){
            setLabelUsed(false);
            transactionButton.setGraphic(upGreyArrow);
            idButton.setGraphic(downGreenArrow);
        }
    }

    public void switchToReceiverIdLabel(){
        if (!getLabelUsed()){
            setLabelUsed(true);
            transactionButton.setGraphic(upGreenArrow);
            idButton.setGraphic(downGreyArrow);
        }
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
