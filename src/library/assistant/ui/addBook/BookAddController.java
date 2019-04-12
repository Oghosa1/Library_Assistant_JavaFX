package library.assistant.ui.addBook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.asssistance.database.DatabaseHandler;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookAddController implements Initializable {


    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private AnchorPane rootPane;

    DatabaseHandler databaseHandler;

    @FXML
    void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter In All Fields");
            alert.showAndWait();
            return;
        }
//        	 +"       id varchar(200) primary key ,\n"
//             +"       title varchar(200),\n"
//             +"       author varchar(200),\n"
//             +"       publisher varchar(100),\n"
//             +"       isAvail boolean default true"
        String qu = "INSERT INTO BOOK VALUES(" +
                "'" + bookName + "'," +
                "'" + bookID + "'," +
                "'" + bookAuthor + "'," +
                "'" + bookPublisher + "'," +
                "" + true + "" +
                ")";
        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        } else//error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();

        }

    }


    @FXML
    void cancel(ActionEvent event) {
        // System.exit(0);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
        checkData();
    }


    //private void checkData() {
    //	String qu="SELECT title FROM BOOK";
    //	ResultSet rs = databaseHandler.execQuery(qu);
    //	try {
    //	while(rs.next()){
    //	    String titlex = rs.getString("title");
    //	    System.out.println(titlex);
    //	}
    //}catch (SQLException e) {
    //TODO Auto-generated catch block
    //e.printStackTrace();
    //}

    // }


    private void checkData() {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = databaseHandler.execQuery(qu);
        while(true) {
            //If you encounter error please recheck video 4 and edit this try and catch
            // block and replace it by using eclipse to generate the try and catch block
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String titlex = null;
            try {
                titlex = rs.getString("title");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(titlex);
        }
    }
}
