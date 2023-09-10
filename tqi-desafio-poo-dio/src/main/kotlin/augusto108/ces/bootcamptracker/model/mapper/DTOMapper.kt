package augusto108.ces.bootcamptracker.model.mapper

import augusto108.ces.bootcamptracker.model.dto.BaseDto
import augusto108.ces.bootcamptracker.model.dto.PersonBaseDto
import augusto108.ces.bootcamptracker.model.entities.BaseEntity
import augusto108.ces.bootcamptracker.model.entities.PersonBaseEntity
import org.modelmapper.ModelMapper

private object DTOMapper {
    val mapper = ModelMapper()
}

fun <E : BaseEntity, D : BaseDto> E.map(d: Class<D>): D = DTOMapper.mapper.map(this, d)

fun <E : PersonBaseEntity, D : PersonBaseDto> E.personMap(d: Class<D>): D = DTOMapper.mapper.map(this, d)