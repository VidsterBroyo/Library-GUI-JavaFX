/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;
//import static JavaToDo.JavaToDoController.items;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
/**
 *
 * @author user
 */
public class libItemCellFactory extends ListCell<Book> {
    
    @FXML
    private Label bookItemTitle;
    
    @FXML
    private Pane cellPane;
    
    private FXMLLoader mlLoader;
//    private String cellText;
    
    @Override
    protected void updateItem(Book book, boolean empty){
        super.updateItem(book, empty);
        
        if(empty || book == null){
            setText(null);
            setGraphic(null);
        }
        else{
            if(mlLoader == null){
                mlLoader = new FXMLLoader(getClass().getResource("libListItem.fxml"));
                mlLoader.setController(this);

                try {
                    mlLoader.load();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            
        bookItemTitle.setText(book.title+", by "+book.author);
        setText(null);
        setGraphic(cellPane);
        }
    }
}
