package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Course
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copyTo
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class CourseDaoImpl(private val entityManager: EntityManager) : CourseDao {

    override fun saveCourse(course: Course): Course {
        entityManager.persist(course)
        return course
    }

    override fun findAllCourses(): List<Course> {
        "from Course order by id".apply { return entityManager.createQuery(this, Course::class.java).resultList }
    }

    override fun findCourseById(id: Int): Course {
        "from Course c where id = :id".apply {
            return entityManager.createQuery(this, Course::class.java).setParameter("id", id).singleResult
        }
    }

    override fun updateCourse(course: Course): Course {
        val existingCourse: Course = findCourseById(course.id)
        course.copyTo(existingCourse).also {
            entityManager.persist(it)
            return it
        }
    }

    override fun deleteCourse(id: Int): Unit = entityManager.remove(findCourseById(id))
}