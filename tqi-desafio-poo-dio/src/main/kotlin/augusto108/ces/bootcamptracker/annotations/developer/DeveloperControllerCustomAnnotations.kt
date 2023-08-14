package augusto108.ces.bootcamptracker.annotations.developer

import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "persists a developer",
    responses = [
        ApiResponse(
            description = "Created",
            responseCode = "201",
            content = [Content(schema = Schema(implementation = DeveloperDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class SaveOperation

@Operation(
    summary = "gets all developers",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(array = ArraySchema(schema = Schema(implementation = DeveloperDTO::class)))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindAllOperation

@Operation(
    summary = "get a developer by id",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = DeveloperDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class FindByIdOperation

@Operation(
    summary = "updates developer information",
    responses = [
        ApiResponse(
            description = "Success",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = DeveloperDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class UpdateOperation

@Operation(
    summary = "deletes a developer by id",
    responses = [
        ApiResponse(
            description = "No content",
            responseCode = "204",
            content = [Content(schema = Schema(implementation = DeveloperDTO::class))]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
annotation class DeleteOperation