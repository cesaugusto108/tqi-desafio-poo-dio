package augusto108.ces.bootcamptracker.model.dto

import org.springframework.hateoas.RepresentationModel
import java.util.*

abstract class PersonBaseDto(var id: UUID?) : RepresentationModel<PersonBaseDto>()