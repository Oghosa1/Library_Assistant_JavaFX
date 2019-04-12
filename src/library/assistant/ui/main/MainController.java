package library.assistant.ui.main;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.asssistance.database.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class MainController implements Initializable {

    @FXML
    private JFXTextField bookID;

    @FXML
    private ListView<String> issueDataList;

    @FXML
    private Text memberName;

    @FXML
    private Text memberContact;

    @FXML
    private TextField memberIdInput;

    @FXML
    private HBox book_info, member_info;


    @FXML
    private TextField bookIDInput;

    @FXML
    private Text bookName;

    @FXML
    private Text bookAuthor;

    @FXML
    private Text bookStatus;

    private DatabaseHandler databaseHandler;


        //Class for issuing books out
        //Class for issuing books out
        //Class for issuing books out
        //Class for issuing books out
        //Class for issuing books out
        //Class for issuing books out
        //Class for issuing books out
        //Class for issuing books out
    @FXML
    void loadIssueOperation(ActionEvent event) {
        String memberId = memberIdInput.getText();
        String bookId = bookIDInput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to issue the book" + bookName.getText() + "\n to " + memberName.getText() + " ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String str = "INSERT INTO ISSUE(memberID,bookID) VALUES("
                    + "'" + memberId + "',"
                    + "'" + bookId + "')";
            String str2 = "UPDATE BOOK SET isAVAIL = false WHERE id = '" + bookId + "'";
            System.out.println(str + " and " + str2);
            if (databaseHandler.execAction(str) && databaseHandler.execAction(str2)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Confirm Issue Operation");
                alert1.setHeaderText(null);
                alert1.setContentText("Book issue complete");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Confirm Issue Operation");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue operation failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Canceled");
            alert1.setHeaderText(null);
            alert1.setContentText("Book issue canceled");
        }

    }


    @FXML
    void loadSettings(ActionEvent event) {

    }

    @FXML
    void loadAddBook(ActionEvent event) {
        loadWindow("/library/assistant/ui/addBook/add_book.fxml", "Add New Book");
    }

    @FXML
    void loadAddMember(ActionEvent event) {
        loadWindow("/library/assistant/ui/addmember/member_add.fxml", "Add New Member");
    }

    @FXML
    void loadBookTable(ActionEvent event) {
        loadWindow("/library/assistant/ui/listbook/book_list.fxml", "Book List");
    }

    @FXML
    void loadMemberTable(ActionEvent event) {
        loadWindow("/library/assistant/ui/listmember/list_member.fxml", "Member List");
    }

    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    // To make that part more dense or darker
    public void initialize(URL location, ResourceBundle resources) {
        JFXDepthManager.setDepth(book_info, 1);
        JFXDepthManager.setDepth(member_info, 1);
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
        //Load books if details if available or not
    void loadBookInfo(ActionEvent event) {
        String id = bookIDInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id = '" + id + "'";
        ResultSet resultSet = databaseHandler.execQuery(qu);
        Boolean flag = false;
        while (true) {
            try {
                if (!resultSet.next()) break;
                String bName = resultSet.getString("title");
                String bAuthor = resultSet.getString("author");
                Boolean bStatus = resultSet.getBoolean("isAvail");
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus) ? "Available" : "Not Available";
                bookStatus.setText(status);
                flag = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (!flag) {
            bookName.setText("No Such Book");
            bookStatus.setText("");
            bookAuthor.setText("");
        }
    }

    @FXML
    void loadMemberInfo(ActionEvent event) {
        String id = memberIdInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
        ResultSet resultSet = databaseHandler.execQuery(qu);
        Boolean flag = false;
        while (true) {
            try {
                if (!resultSet.next()) break;
                String mName = resultSet.getString("name");
                String mNO = resultSet.getString("mobile");
                memberContact.setText(mNO);
                memberName.setText(mName);
                flag = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (!flag) {
            memberContact.setText("");
            memberName.setText("No such member");
        }
    }

    @FXML
    void loadBookinfo2(ActionEvent event) throws SQLException {
        ObservableList<String> issueData = FXCollections.observableArrayList();
        String id = bookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(qu);
        while (rs.next()) {
            String mBookID = id;
            String mMemberID = rs.getString("memberID");
            Timestamp mIssueTime = rs.getTimestamp("issueTime");
            int mRenewCount = rs.getInt("renew_count");

            issueData.add("Issue Date and Time: " + mIssueTime.toGMTString());
            issueData.add("Renew Account: " + mRenewCount);

            issueData.add("Book Information:-");

            qu = "SELECT * FROM BOOK WHERE ID = '" + mBookID + "'";
            ResultSet r1 = databaseHandler.execQuery(qu);
            while (r1.next()) {
                issueData.add("Book Name: " + r1.getString("title"));
                issueData.add("Book ID: " + r1.getString("id"));
                issueData.add("Book Author: " + r1.getString("author"));
                issueData.add("Book Publisher: " + r1.getString("publisher"));

            }
            qu = "SELECT * FROM MEMBER WHERE ID = '" + mMemberID + "'";
            r1 = databaseHandler.execQuery(qu);
            issueData.add("Member Information-: ");
            while (r1.next()) {
                issueData.add("Member Name: "+r1.getString("name"));
                issueData.add("Mobile Number: "+r1.getString("mobile"));
                issueData.add("Email: "+r1.getString("email"));
            }
        }

        issueDataList.getItems().setAll(issueData);

    }

}

