package library.assistant.ui.listmember;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.assistant.ui.listbook.booklistController;
import library.asssistance.database.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MemberListController implements Initializable {
    ObservableList<Member> list= FXCollections.observableArrayList();
    @FXML
    private TableView<Member> tableView;

    @FXML
    private TableColumn<Member, String> nameCol;

    @FXML
    private TableColumn<Member, String> idCol;

    @FXML
    private TableColumn<Member, String> mobileCol;

    @FXML
    private TableColumn<Member, String> emailCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
    }
    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    // for loading the data in the database
    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM MEMBER";
        ResultSet rs = handler.execQuery(qu);
        while (true) {
            //If you encounter error please recheck video 4 and edit this try and catch
           // block and replace it by using eclipse to generate the try and catch block
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //String titlex = null;
            try {
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String id = rs.getString("id");
                String email = rs.getString("email");

                list.add(new Member(name, id, mobile, email));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableView.getItems().setAll(list);
            System.out.println(qu);
        }
    }
    public static class Member {
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

        Member(String name, String id, String mobile, String email) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

    }

}

