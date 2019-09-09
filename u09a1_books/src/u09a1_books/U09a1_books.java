package u09a1_books;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale.Category;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;


public class U09a1_books {

    private static MongoCollection<Document> collection;
    
    public static void main(String[] args) {
        Logger log = Logger.getLogger(U09a1_books.class.getName());
        log.setLevel(Level.CONFIG);
        
        //Declare an empty string for the MongoURI
        String mongoURI = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String user = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();
        
        try{
            mongoURI = "mongodb://" + user + ":" + URLEncoder.encode(pass, "UTF-8") + 
                    "@localhost:27017/books";
        }
        catch(Exception ex) {
            log.log(Level.SEVERE, "Could not access database...");
        }
        
        //Set the name of the database that holds the information
        String database = "books";
        //Set the name of the collection that holds the data
        String collectionName = "bookInfo";
        
        //Create MongoDB client connection
        MongoClientURI mongoConnURI = new MongoClientURI(mongoURI);
        MongoClient mongoClient = new MongoClient(mongoConnURI);
        MongoDatabase mongoDB = mongoClient.getDatabase(database);
        MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
        
        
        
        //Find a book in the bookInfo database
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Title of a Book: ");
        String book = input.nextLine();
        
        
        MongoCursor<Document> cursor = findBook(book);
        String selection = getBookChoice(cursor);
        if(!selection.equals("0")) {
            Books b = getBookInfo(selection);
            System.out.println(b);
            
        }
    }
        

    static public MongoCursor<Document> findBook(String book) {
        //pulls out the required information according to the query
        String[] includeList = {"Pages", "Category", "Date", "Author", "Title"};
        //created a new BasicDBObject
        BasicDBObject query = new BasicDBObject();
        //Put Where I wanted the program to look followed by the regular 
        //expreesion for the book and told it to ignore capitalization 
        query.put("Title", 
                new BasicDBObject("$regex", book)
                .append("$options", "i"));
        //It searches the document in mongodb for the query we have told it.
        MongoCursor<Document> cursor = collection.find(query)
                .projection(fields(include(includeList)))
                .iterator();
        //returns the cursor
        return cursor;
    }
    
    static public Books getBookInfo(String Title) {
        //creates a new BasicDBObject
        BasicDBObject query = new BasicDBObject();
        //puts in the field and the criteria 
        query.put("Title", Title);
        //Gives the user a cuirsor that gives them access to the restaurant_id
        MongoCursor<Document> cursor = collection.find(query).iterator();
        //If the cursor has a next the program continues if not it displays the information
        Document Books = cursor.next();
        
        //Gets the book title and puts it in the variable Title
        Title = Books.getString("Title");
        //gets the author a puts it under the variable author
        String author = Books.getString("Author");
        //gets the category and puts it under the variable category
        String category = Books.getString("Category");
        //gets the pages and puts them under the variable pages
        String pages = Books.getString("Pages");
        
        //returns all of those under Books b
        Books b = new Books(Title, author, category, pages);
        
        return b;
    }
    
    static public String getBookChoice(MongoCursor<Document> cursor) {
        ArrayList<String> id = new ArrayList<>();
        //query to add the title as long as its in the database
        int referenceNum = 0;
        //while loop to display all the book titles that have the specific set of characters in them 
        //specified in the query
        //and add them to the arraylist
        while(cursor.hasNext()) {
            Document bookInfo = cursor.next();
            referenceNum++;
            System.out.println(referenceNum + ") " + bookInfo.getString("Title"));
            id.add(bookInfo.getString("Title"));
        }
        
        //code to get the selection number and display information
        System.out.print("\nEnter selection number (0 to quit): ");
        Scanner inp = new Scanner(System.in);
        int choice = inp.nextInt();
        String bookNum;
        // choice not 0 and not too high
        if(choice > 0 && choice <= referenceNum) {
            bookNum = id.get(choice - 1);
        }
        else {
            bookNum = "0";
        }
        return bookNum;
    }
}
