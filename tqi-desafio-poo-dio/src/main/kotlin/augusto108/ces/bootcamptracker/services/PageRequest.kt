package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.BaseDto
import augusto108.ces.bootcamptracker.model.dto.PersonBaseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

object PageRequest {
    fun <T : BaseDto> getPageRequest(currentPage: Int, pageSize: Int, list: List<T>): Page<T> =
        pageImplementation(currentPage, pageSize, list)

    fun <T : PersonBaseDto> getPersonPageRequest(currentPage: Int, pageSize: Int, list: List<T>): Page<T> =
        pageImplementation(currentPage, pageSize, list)

    private fun <T> pageImplementation(currentPage: Int, pageSize: Int, list: List<T>): PageImpl<T> {
        val pageable: Pageable = PageRequest.of(currentPage, pageSize)
        val start: Int = pageable.offset.toInt()
        val end: Int = (start + pageable.pageSize).coerceAtMost(list.size)
        val pageContent: List<T> = list.subList(start, end)

        return PageImpl(pageContent, pageable, (start + pageable.pageSize).toLong())
    }
}