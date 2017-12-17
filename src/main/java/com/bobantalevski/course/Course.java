package com.bobantalevski.course;

import com.bobantalevski.core.BaseEntity;
import com.bobantalevski.review.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Course extends BaseEntity{

  private String title;
  private String url;
  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private List<Review> reviews;

  protected Course() {
    super();
    reviews = new ArrayList<>();
  }

  public Course(String title, String url) {
    this();
    this.title = title;
    this.url = url;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void addReview(Review review) {
    review.setCourse(this);
    reviews.add(review);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
