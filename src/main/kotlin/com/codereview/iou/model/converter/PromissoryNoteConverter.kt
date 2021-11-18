package com.codereview.iou.model.converter

import com.codereview.iou.model.dto.PromissoryNoteDto
import com.codereview.iou.model.entity.PromissoryNote
import org.mapstruct.Mapper

@Mapper
interface PromissoryNoteConverter {

    fun convertToEntity(dto: PromissoryNoteDto): PromissoryNote

    fun convertToDto(user: PromissoryNote): PromissoryNoteDto
}