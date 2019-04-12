package library.assistant.ui.listbook;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.asssistance.database.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class booklistController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private TableColumn<Book, String> publisherCol;

    @FXML
    private TableColumn<Book, Boolean> availabilityCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
    }

    // for loading the data in the database
    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM BOOK";
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
                String titlex = rs.getString("title");
                String author = rs.getString("author");
                String id = rs.getString("id");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");

                list.add(new Book(titlex, id, author, publisher, avail));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableView.getItems().setAll(list);
             System.out.println(qu);
        }
    }

    private void initCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }


    public static class Book {
        private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;

        Book(String title, String id, String author, String pub, Boolean avail) {
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(pub);
            this.availability = new SimpleBooleanProperty(avail);
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }


        public boolean isAvailability() {
            return availability.get();
        }

    }
}

