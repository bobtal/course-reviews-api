package com.bobantalevski.review;

import com.bobantalevski.core.BaseEntity;
import com.bobantalevski.course.Course;
import com.bobantalevski.user.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review extends BaseEntity{
  private int rating;
  private String description;
  @ManyToOne
  private Course course;
  @ManyToOne
  private User reviewer;

  // TODO: we are duplicating code here for every single entity. Share it?
  protected Review() {
    super();
  }

  public Review(int rating, String description) {
    this.rating = rating;
    this.description = description;
  }

  public User getReviewer() {
    return reviewer;
  }

  public void setReviewer(User reviewer) {
    this.reviewer = reviewer;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
