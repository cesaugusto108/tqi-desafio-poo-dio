package augusto108.ces.bootcamptracker.annotations.instructor

import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "persists an instructor",
    responses = [
        ApiResponse(
            description = "Created",
            responseCode = "201",
            content = [Content(schema = Schema(implementation = InstructorDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class SaveOperation

@Operation(
    summary = "gets all instructors",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(array = ArraySchema(schema = Schema(implementation = InstructorDTO::class)))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindAllOperation

@Operation(
    summary = "get an instructor by id",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = InstructorDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindByIdOperation

@Operation(
    summary = "updates instructor information",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = InstructorDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class UpdateOperation

@Operation(
    summary = "deletes an instructor by id",
    responses = [
        ApiResponse(
            description = "No content",
            responseCode = "204",
            content = [Content(schema = Schema(implementation = Unit::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class DeleteOperation

@Operation(
    summary = "makes an instructor active",
    responses = [
        ApiResponse(
            description = "No content",
            responseCode = "204",
            content = [Content(schema = Schema(implementation = Unit::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class ActivateInstructor

@Operation(
    summary = "makes an instructor inactive",
    responses = [
        ApiResponse(
            description = "No content",
            responseCode = "204",
            content = [Content(schema = Schema(implementation = Unit::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class DeactivateInstructor