package com.bobantalevski.course;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
