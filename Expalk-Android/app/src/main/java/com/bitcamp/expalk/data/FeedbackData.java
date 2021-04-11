package com.bitcamp.expalk.data;

public class FeedbackData {

    String rating, feedback, timestamp, mentor, mentee;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public FeedbackData(String rating, String feedback, String timestamp, String mentor, String mentee) {
        this.rating = rating;
        this.feedback = feedback;
        this.timestamp = timestamp;
        this.mentor = mentor;
        this.mentee = mentee;
    }
}
