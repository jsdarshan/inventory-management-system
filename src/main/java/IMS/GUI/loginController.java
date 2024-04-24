package IMS.GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import IMS.logic.login;

import java.io.IOException;
import java.sql.SQLException;


interface LoginFactory {
    login createLoginInstance();
}
class DefaultLoginFactory implements LoginFactory {
    @Override
    public login createLoginInstance() {
        try {
            return new login();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
            return null;
        }
    }
}

public class loginController {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button loginBtn;
    @FXML
    FontAwesomeIcon closeBtn;
    Stage stage;

    public loginController() throws SQLException {
    }

    private final LoginFactory loginFactory = new DefaultLoginFactory();

    public void loginBtnClicked(ActionEvent event) throws IOException, SQLException {
        username.setStyle("-fx-border-color:black;");
        password.setStyle("-fx-border-color:black;");
        String uid = username.getText();
        String pass = password.getText();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        login loginInstance = loginFactory.createLoginInstance();

        if (loginInstance != null && loginInstance.verifyCredentials(uid, pass)) {
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.show();
        } else {
           username.requestFocus();
        }
    }

    public void enterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (username.isFocused()) {
                if ((username.getText()).equals("")) {
                    username.requestFocus();
                    username.setStyle("-fx-border-color:Red;");
                } else {
                    password.requestFocus();
                }
            } else if (password.isFocused()) {
                if ((password.getText()).equals("")) {
                    password.requestFocus();
                    password.setStyle("-fx-border-color:Red;");
                } else {
                    loginBtn.requestFocus();
                    loginBtn.fire();
                }
            }
        }
    }

    public void exit(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
