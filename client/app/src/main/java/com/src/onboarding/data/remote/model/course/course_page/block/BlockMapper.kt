package com.src.onboarding.data.remote.model.course.course_page.block

import com.src.onboarding.data.remote.model.course.course_page.block_item.BlockItemMapper
import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.course_page.Block

class BlockMapper(private val blockItemMapper: BlockItemMapper) : Mapper<Block, BlockResponse> {
    override suspend fun mapFromResponseToModel(data: BlockResponse): Block {
        return Block(
            id = data.id,
            name = data.name,
            itemInBlock = data.itemInBlock.map { blockItemMapper.mapFromResponseToModel(it) })
    }
}