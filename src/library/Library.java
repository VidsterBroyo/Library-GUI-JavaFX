// Add full screen option -- change color theme -- have filter search for browse -- Quit
package library;

import static javafx.application.Application.launch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.scene.control.Control;
//import org.controlsfx.control;
//import org.controlsfx.control.Rating;


// BOOK OBJECT
class Book{
    public String title;
    public String author;
    public String genre;
    public double rating;
    public int ratingN;
    public int pages;
    public String status;
    public String image;
    
    Book(String title, String author, String genre, double rating, int ratingN, int pages, String status, String image){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
        this.ratingN = ratingN;
        this.pages = pages;
        this.status = status;
        this.image = image;
    }
    

    
    public String fileWrite(){
        return this.title+"\n"+this.author+"\n"+this.genre+"\n"+this.rating+"\n"+this.pages+"\n";
    }
    
//    @Override
//    public String toString(){
//        return "'"+this.title+"', a "+this.genre+" book by "+this.author+" rated "+this.rating+"/5 stars and "+this.pages+" pages long";
//    }
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Book)){
            return false;
        }
        
        Book book = (Book) o;
        if(this.title.toLowerCase().equals(book.title.toLowerCase())){
            if(this.author.toLowerCase().equals(book.author.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    
}


public class Library extends Application {
    public static Scene scene;
    
    public static ArrayList<Book> booksIn = new ArrayList<>();
    public static ArrayList<Book> booksOut = new ArrayList<>();
    
    public static ObservableList<Book> allBooks;
    public static ObservableList<Book> yourBooks;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
//        loader.setController(new MainController(path));
//        Pane mainPane = loader.load();
        
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("libCss.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        
        stage.setFullScreen(true);
        
        
        
        try {
            FileReader kfr = new FileReader("C:\\Users\\user\\Documents\\Coding\\NetBeansProjects\\library\\src\\library\\books.txt");
            
            BufferedReader br = new BufferedReader(kfr);
            
            
            String line;
            while (!((line = br.readLine()).equals("."))) {
                System.out.print(line+"\n");
                Book book = new Book(line, br.readLine(), br.readLine(), Double.parseDouble(br.readLine()), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()), "in", br.readLine());
                booksIn.add(book);
                allBooks.add(book);
                System.out.println("added in books");
            }
            
            // INPUT OUT BOOKS
            while((line = br.readLine()) != null){
                System.out.print(line+"\n");
                Book book = new Book(line, br.readLine(), br.readLine(), Double.parseDouble(br.readLine()), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()), "out", br.readLine());
                booksOut.add(book);
                yourBooks.add(book);
                allBooks.add(book);
                System.out.println("added out books");
            }
            
            br.close();
            
        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(booksIn);
        System.out.println(booksOut);
    }
    

    
    public static void main(String[] args) {
        System.out.println("main1");
        launch(args);
        System.out.println("main2");
    }
   
}