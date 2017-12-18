package com.bobantalevski.core;

import com.bobantalevski.course.Course;
import com.bobantalevski.course.CourseRepository;
import com.bobantalevski.review.Review;
import com.bobantalevski.user.User;
import com.bobantalevski.user.UserRepository;
import java.util.Arrays;
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
  private final UserRepository users;

  @Autowired
  public DatabaseLoader(CourseRepository courses, UserRepository users) {
    this.courses = courses;
    this.users = users;
  }

//  // Why not just
//  @Autowired
//  private CourseRepository courses;
//  @Autowired
//  private UserRepository users;

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

    List<User> students = Arrays.asList(
        new User("jacobproffer", "Jacob",  "Proffer", "password", new String[] {"ROLE_USER"}),
        new User("mlnorman", "Mike",  "Norman", "password", new String[] {"ROLE_USER"}),
        new User("k_freemansmith", "Karen",  "Freeman-Smith", "password", new String[] {"ROLE_USER"}),
        new User("seth_lk", "Seth",  "Kroger", "password", new String[] {"ROLE_USER"}),
        new User("mrstreetgrid", "Java",  "Vince", "password", new String[] {"ROLE_USER"}),
        new User("anthonymikhail", "Tony",  "Mikhail", "password", new String[] {"ROLE_USER"}),
        new User("boog690", "AJ",  "Teacher", "password", new String[] {"ROLE_USER"}),
        new User("faelor", "Erik",  "Faelor Shafer", "password", new String[] {"ROLE_USER"}),
        new User("christophernowack", "Christopher",  "Nowack", "password", new String[] {"ROLE_USER"}),
        new User("calebkleveter", "Caleb",  "Kleveter", "password", new String[] {"ROLE_USER"}),
        new User("richdonellan", "Rich",  "Donnellan", "password", new String[] {"ROLE_USER"}),
        new User("albertqerimi", "Albert",  "Qerimi", "password", new String[] {"ROLE_USER"})
    );
    users.save(students);
    users.save(new User("bobtal", "Boban", "Talevski", "12345", new String[] {"ROLE_USER", "ROLE_ADMIN"}));

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
          Random random = new Random();
          String template = templates[random.nextInt(5)];
          String buzzword = buzzwords[random.nextInt(5)];
          String title = String.format(template, buzzword);
          Course c = new Course(title, "http://www.example.com");
          Review review = new Review((i % 5) + 1, String.format("Moar %s please!!!", buzzword));
          review.setReviewer(students.get(random.nextInt(students.size())));
          c.addReview(review);
          return c;
        })
        .forEach(courses::save);
  }
}
