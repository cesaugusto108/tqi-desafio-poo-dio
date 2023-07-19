package augusto108.ces.bootcamptracker.controllers.annotations.bootcamp

import augusto108.ces.bootcamptracker.dto.BootcampDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "persists a bootcamp",
    responses = [
        ApiResponse(
            description = "Created",
            responseCode = "201",
            content = [Content(schema = Schema(implementation = BootcampDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class SaveOperation

@Operation(
    summary = "gets all bootcamps",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(array = ArraySchema(schema = Schema(implementation = BootcampDTO::class)))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindAllOperation

@Operation(
    summary = "get a bootcamp by id",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = BootcampDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindByIdOperation

@Operation(
    summary = "updates bootcamp information",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = BootcampDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class UpdateOperation

@Operation(
    summary = "deletes a bootcamp by id",
    responses = [
        ApiResponse(
            description = "No content",
            responseCode = "204",
            content = [Content(schema = Schema(implementation = BootcampDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class DeleteOperation