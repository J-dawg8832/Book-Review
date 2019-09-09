package u09a1_books;

public class Review {

    private String title;
    private String reviewer;
    private String review;

    public Review(String title, String reviewer, String review) {
        this.title = title;
        this.reviewer = reviewer;
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
    
    
}
