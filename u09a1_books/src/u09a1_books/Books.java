package u09a1_books;

public class Books {

    private String title;
    private String author;
    private String category;
    private String pages;

    public Books(String title, String author, String category, String pages) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
    
    
    
}
