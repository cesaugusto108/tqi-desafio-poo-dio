package augusto108.ces.bootcamptracker.dto.mapper

import augusto108.ces.bootcamptracker.dto.BaseDto
import augusto108.ces.bootcamptracker.entities.BaseEntity
import org.modelmapper.ModelMapper

private object DTOMapper {
    val mapper = ModelMapper()
}

fun <E : BaseEntity, D : BaseDto> E.map(d: Class<D>): D = DTOMapper.mapper.map(this, d)