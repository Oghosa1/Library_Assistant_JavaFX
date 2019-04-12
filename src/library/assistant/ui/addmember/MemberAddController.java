package library.assistant.ui.addmember;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import library.asssistance.database.DatabaseHandler;

public class MemberAddController implements Initializable {
    DatabaseHandler handler;
    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton saveButton;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handler = DatabaseHandler.getInstance();


    }

    @FXML
    private JFXButton cancelButton;

    @FXML
    void addMember(ActionEvent event) {
        String mName = name.getText();
        String mID = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();

        Boolean flag = mName.isEmpty() || mID.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty();
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter In All Fields");
            alert.showAndWait();
            return;
        }

			/*stmt.execute("CREATE TABLE " + TABLE_NAME +"("
					+"       id varchar(200) primary key ,\n"
					+"       name varchar(200),\n"
					+"       mobile varchar(20),\n"
					+"       email varchar(100),\n"
					+")");*/
        String st = "INSERT INTO MEMBER VALUES (" +
                "'" + mID + "'," +
                "'" + mName + "'," +
                "'" + mMobile + "'," +
                "'" + mEmail + "'" +
                ")";
        if (handler.execAction(st)) {
            System.out.println(st);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Saved");
            alert.showAndWait();
            return;
        }else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            return;
        }

    }


    @FXML
    void cancel(ActionEvent event) {
        System.exit(0);

    }

}


