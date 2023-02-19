package com.src.onboarding.data.remote.model.course.course_page.course

import com.src.onboarding.data.remote.model.course.course_page.block.BlockMapper
import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.course_page.CourseBlock

class CourseBlockMapper(private val blockMapper: BlockMapper) : Mapper<CourseBlock, CourseBlockResponse> {
    override suspend fun mapFromResponseToModel(data: CourseBlockResponse): CourseBlock {
        return CourseBlock(
            id = data.id,
            name = data.name,
            description = data.description,
            countThemes = data.countThemes,
            blocks = data.blocks.map { blockMapper.mapFromResponseToModel(it) }
        )
    }
}