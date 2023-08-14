package augusto108.ces.bootcamptracker.model.dto

import org.springframework.hateoas.RepresentationModel

abstract class BaseDto(
    var id: Int
) : RepresentationModel<BaseDto>()