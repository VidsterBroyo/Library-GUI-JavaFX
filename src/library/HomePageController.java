package library;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.awt.event.*;import java.awt.*;import javax.swing.*;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.lang.Integer;
import javafx.scene.media.AudioClip;
/**
 * FXML Controller class
 *
 * @author user
 */
public class HomePageController implements Initializable {

    private Book selectedBook;
    private int rating;
    private Book ratingBook;
    FileWriter bookFileWrite = null;
    String bookCoverFilePath = "";
    
    @FXML
    private AnchorPane homePane, browsePane, yourBooksPane, donatePane;
    
    private AnchorPane currentPane = homePane;
    
    @FXML
    private ListView browseList, yourList;
    
    @FXML
    private Pane ratePaneBr, takePane, ratePaneYo, returnPane;
    
    @FXML
    private Button rateButtonBr, takeButton, rateButtonYo, returnButton;
    
    @FXML
    private Label titleBr, authorBr, genreBr, statusBr, ratingBr;
    
    @FXML
    private Label titleYo, authorYo, genreYo, statusYo, ratingYo;
    
    @FXML
    private Label titleDoP, authorDoP, genreDoP, ratingDoP;
    
    @FXML
    private Text donateBookText;
    
    @FXML
    private TextField ratingFieldBr, ratingFieldYo, titleDo, authorDo, genreDo, pagesDo, ratingDo;
    
    @FXML
    private ImageView coverBr, coverYo;
     
    
    @FXML
    private void menuAction(ActionEvent event){
        Button buttonClicked = (Button) event.getSource();
        
        switch(buttonClicked.getId()){
            
            // DISPLAY HOME PAGE
            case "homeButton":
                if(currentPane != null){
                    currentPane.setVisible(false);
                }
                homePane.setVisible(true);
                currentPane=browsePane;
                break;
            
            // DISPLAY BROWSE BOOKS PAGE
            case "browseButton":
                if(currentPane != null){
                    currentPane.setVisible(false);
                }
                browsePane.setVisible(true);
                currentPane=browsePane;
                break;
                
            // DISPLAY YOUR BOOKS PAGE
            case "yourBooksButton":
                if(currentPane != null){
                    currentPane.setVisible(false);
                }
                yourBooksPane.setVisible(true);
                currentPane=yourBooksPane;
                break;
                
            // DISPLAY DONATE BOOK PAGE
            case "donateButton":
                if(currentPane != null){
                    currentPane.setVisible(false);
                }
                donateBookText.setText("");
                donatePane.setVisible(true);
                currentPane=donatePane;
                break;
            
            // RATE + RETURN POP-UPS FOR BROWSE PAGE
            case "rateButtonBr":
                ratePaneBr.setVisible(true);
                ratingBook=selectedBook;
                break;
            
            case "takeButton":
                takePane.setVisible(true);
                break;
                
            // RATE + RETURN POP-UPS FOR YOUR BOOKS PAGE
            case "rateButtonYo":
                ratePaneYo.setVisible(true);
                ratingBook=selectedBook;
                break;
                
            case "returnButton":
                returnPane.setVisible(true);
                break;
        }

    }
    
    
    @FXML
    private void filer(ActionEvent event){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        
        if (selectedFile != null){
            bookCoverFilePath = selectedFile.getPath();
        }
    }
    
    
    @FXML
    private void submitAction(ActionEvent event){
        Button buttonClicked = (Button) event.getSource();
        
        switch(buttonClicked.getId()){
            
            // SUBMIT RATING FOR BROWSE PAGE
            case "ratingSubmitBr":
                
                // get rating
                rating = Integer.parseInt(ratingFieldBr.getText());
                
                // validate rating
                if (rating < 0 || rating > 5){
                    System.out.println("Invalid rating");
                    ratePaneBr.setVisible(false);
                    break;
                }
                ratePaneBr.setVisible(false);
                
                // update book attributes
                ratingBook.rating += rating;
                ratingBook.ratingN += 1;
                
                // calulate and display rating
                ratingBr.setText(Double.toString(Math.rint(ratingBook.rating/ratingBook.ratingN*100)/100)+"/5");
                break;
            
            // TAKE BOOK
            case "takeYes":
                // change status of book by moving lists
                Library.booksIn.remove(Library.booksIn.indexOf(selectedBook)); Library.yourBooks.add(selectedBook);
                Library.booksOut.add(selectedBook);
                
                // change book status attribute
                selectedBook.status="out";
                takePane.setVisible(false);
                
                // update listview
                yourList.setItems(Library.yourBooks);
                selectBook();
                break;
            
            // NO TAKE BOOK
            case "takeNo":
                takePane.setVisible(false);
                break;
                
            // SUBMIT RATING FOR YOUR BOOKS PAGE
            case "ratingSubmitYo":
                
                // get rating
                rating = Integer.parseInt(ratingFieldYo.getText());
                
                // validate rating
                if (rating < 0 || rating > 5){
                    System.out.println("Invalid rating");
                    ratePaneYo.setVisible(false);
                    break;
                }
                ratePaneYo.setVisible(false);
                
                // update book attributes
                ratingBook.rating += rating;
                ratingBook.ratingN += 1;
                
                // calulate and display rating
                ratingYo.setText(Double.toString(Math.rint(ratingBook.rating/ratingBook.ratingN*100)/100)+"/5");
                break;

            // RETURN BOOK
            case "returnYes":
                // change status of book by moving lists
                Library.booksOut.remove(Library.booksOut.indexOf(selectedBook)); Library.yourBooks.remove(Library.yourBooks.indexOf(selectedBook));
                Library.booksIn.add(selectedBook);
             
                // change book status attribute
                selectedBook.status="in";
                returnPane.setVisible(false);
                
                // update listview
                yourList.setItems(Library.yourBooks);
                selectBook();
                break;
                
            // NO RETURN BOOK
            case "returnNo":
                returnPane.setVisible(false);
                break;
            
            // DONATE BOOK
            case "donateBook":
                // create book object with user's input
                Book book = new Book(titleDo.getText(), authorDo.getText(), genreDo.getText(), Double.parseDouble(ratingDo.getText()), 1, Integer.parseInt(pagesDo.getText()), "in", bookCoverFilePath);
                
                // add book to database
                Library.booksIn.add(book);
                Library.allBooks.add(book);
                
                // update listview
                browseList.setItems(Library.allBooks);
                donateBookText.setText("Your book's been succesfully added!");
                break;
        }
    }

    
    @FXML
    private void selectBook(){
        //click on item list save selected burger display same apge but adjust as needed
        if (currentPane == browsePane){
            rateButtonBr.setDisable(false); rateButtonBr.setOpacity(1);
            selectedBook = (Book) browseList.getSelectionModel().getSelectedItem();
            titleBr.setText(selectedBook.title); authorBr.setText(selectedBook.author); genreBr.setText(selectedBook.genre);
            ratingBr.setText(Double.toString(Math.rint(selectedBook.rating/selectedBook.ratingN*100)/100)+"/5"); statusBr.setText("This book is currently "+selectedBook.status);
            try{
                FileInputStream inputstream = new FileInputStream(selectedBook.image);
                coverBr.setImage(new Image(inputstream));
            }
                
            catch (Exception e) {
                System.out.println(e);
            }  
        }
        
        else{
            rateButtonYo.setDisable(false); rateButtonYo.setOpacity(1);
            selectedBook = (Book) yourList.getSelectionModel().getSelectedItem();
            titleYo.setText(selectedBook.title); authorYo.setText(selectedBook.author); genreYo.setText(selectedBook.genre);
            ratingYo.setText(Double.toString(Math.rint(selectedBook.rating/selectedBook.ratingN*100)/100)+"/5"); statusYo.setText("This book is currently "+selectedBook.status);
            try{
                FileInputStream inputstream = new FileInputStream(selectedBook.image);
                coverYo.setImage(new Image(inputstream));
            }
                
            catch (Exception e) {
                System.out.println(e);
            }  
        }

        if(selectedBook.status == "out"){
            takeButton.setDisable(true);
            takeButton.setOpacity(0.5);
        }
        
        else{
            takeButton.setDisable(false); takeButton.setOpacity(1);
        }
    }
    
    
    @FXML
    private void save(ActionEvent event){
        
        try {
            bookFileWrite = new FileWriter("C:\\Users\\user\\Documents\\NetBeansProjects\\library\\src\\library\\books.txt");
            for (int i=0; i<Library.booksIn.size(); i++) {
                bookFileWrite.write(Library.booksIn.get(i).title+"\n");
                bookFileWrite.write(Library.booksIn.get(i).author+"\n");
                bookFileWrite.write(Library.booksIn.get(i).genre+"\n");
                bookFileWrite.write(Double.toString(Library.booksIn.get(i).rating)+"\n");
                bookFileWrite.write(Library.booksIn.get(i).ratingN+"\n"); 
                bookFileWrite.write(Library.booksIn.get(i).pages+"\n"); 
                bookFileWrite.write(Library.booksIn.get(i).image+"\n");
            }
            
            bookFileWrite.write(".\n");
            for (int i=0; i<Library.booksOut.size(); i++) { 
                bookFileWrite.write(Library.booksOut.get(i).title+"\n");
                bookFileWrite.write(Library.booksOut.get(i).author+"\n");
                bookFileWrite.write(Library.booksOut.get(i).genre+"\n");
                bookFileWrite.write(Double.toString(Library.booksOut.get(i).rating)+"\n");
                bookFileWrite.write(Library.booksOut.get(i).ratingN+"\n");
                bookFileWrite.write(Library.booksOut.get(i).pages+"\n");
                bookFileWrite.write(Library.booksOut.get(i).image+"\n");
            }
        }
        
        catch(IOException e){
            System.out.println("Error");
        }
        
        finally {
            
                try {
                    bookFileWrite.close();
                    System.out.println("closed");
                }
                catch(IOException ioe){
                    System.out.println("Error");
                }
            }
        System.exit(0);
    }
 
    
    @FXML
    private void donateBookPreview(){
        titleDoP.setText(titleDo.getText()); authorDoP.setText(authorDo.getText()); genreDoP.setText(genreDo.getText()); ratingDoP.setText(ratingDo.getText()+"/5");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Library.allBooks = FXCollections.observableArrayList();
        browseList.setItems(Library.allBooks);
        browseList.setCellFactory(itemCellView -> new libItemCellFactory());
        
        Library.yourBooks = FXCollections.observableArrayList();
        yourList.setItems(Library.yourBooks);
        yourList.setCellFactory(itemCellView -> new libItemCellFactory());
    }    
    
}
