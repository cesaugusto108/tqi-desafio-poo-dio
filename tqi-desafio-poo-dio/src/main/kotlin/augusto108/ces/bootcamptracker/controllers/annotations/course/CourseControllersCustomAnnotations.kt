package augusto108.ces.bootcamptracker.controllers.annotations.course

import augusto108.ces.bootcamptracker.dto.CourseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "persists a course",
    responses = [
        ApiResponse(
            description = "Created",
            responseCode = "201",
            content = [Content(schema = Schema(implementation = CourseDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class SaveOperation

@Operation(
    summary = "gets all courses",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(array = ArraySchema(schema = Schema(implementation = CourseDTO::class)))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindAllOperation

@Operation(
    summary = "get a course by id",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = CourseDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindByIdOperation

@Operation(
    summary = "updates course information",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = CourseDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class UpdateOperation

@Operation(
    summary = "deletes a course by id",
    responses = [
        ApiResponse(
            description = "No content",
            responseCode = "204",
            content = [Content(schema = Schema(implementation = CourseDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class DeleteOperation