package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.entities.Course
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class CourseDaoImpl(private val entityManager: EntityManager) : CourseDao {
    override fun saveCourse(course: Course): Course {
        entityManager.persist(course)

        return course
    }

    override fun findAllCourses(page: Int, max: Int): List<Course> =
        entityManager.createQuery("from Course order by id", Course::class.java).resultList

    override fun findCourseById(id: Int): Course =
        entityManager
            .createQuery("from Course c where id = :id", Course::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateCourse(course: Course): Course {
        var c: Course = findCourseById(course.id)
        c = course.copyProperties(c)

        entityManager.persist(c)

        return c
    }

    override fun deleteCourse(id: Int): Any = entityManager.remove(findCourseById(id))
}