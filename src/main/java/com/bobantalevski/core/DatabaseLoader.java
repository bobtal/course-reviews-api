package com.bobantalevski.core;

import com.bobantalevski.course.Course;
import com.bobantalevski.course.CourseRepository;
import com.bobantalevski.review.Review;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {

  private final CourseRepository courses;

  @Autowired
  public DatabaseLoader(CourseRepository courses) {
    this.courses = courses;
  }

//  // Why not just
//  @Autowired
//  private CourseRepository courses;

  // Field vs Constructor injection.
  // Constructor is generally safer, but more boilerplate (Lombok helps?)
  // https://stackoverflow.com/questions/40620000/spring-autowire-on-properties-vs-constructor
  // http://olivergierke.de/2013/11/why-field-injection-is-evil/

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Course course = new Course("Java Basics", "https://teamtreehouse.com/library/jaba-basics");
    course.addReview(new Review(3, "You ARE a dork!!!"));
    courses.save(course);
    String[] templates = {
        "Up and Running with %s",
        "%s Basics",
        "%s for Beginners",
        "%s for Neckbeards",
        "Under the hood: %s",
    };

    String[] buzzwords = {
        "Spring REST Data",
        "Java 9",
        "Scala",
        "Groovy",
        "Hibernate"
    };

//    Craig's way
//    List<Course> bunchOfCourses = new ArrayList<>();
//    IntStream.rangeClosed(0, 100)
//        .forEach(i -> {
//          String template = templates[new Random().nextInt(5)];
//          String buzzword = buzzwords[new Random().nextInt(5)];
//          String title = String.format(template, buzzword);
//          Course c = new Course(title, "http://www.example.com");
//          c.addReview(new Review((i % 5) + 1, String.format("Moar %s please!!!", buzzword)));
//          bunchOfCourses.add(c);
//        });
//    courses.save(bunchOfCourses);

    // my way - without declaring a list at all and just feeding the stream which was mapped
    // to Course objects into a consumer which does save for each. Probably a huge performance loss
    // due to multiple hits to the database for each courses.save call,
    // but wanted to make sure it works this way as well
    IntStream.rangeClosed(0, 100)
        .mapToObj(i -> {
          String template = templates[new Random().nextInt(5)];
          String buzzword = buzzwords[new Random().nextInt(5)];
          String title = String.format(template, buzzword);
          Course c = new Course(title, "http://www.example.com");
          c.addReview(new Review((i % 5) + 1, String.format("Moar %s please!!!", buzzword)));
          return c;
        })
        .forEach(courses::save);
  }
}
