package com.codereview.iou.service

import com.codereview.iou.model.converter.PromissoryNoteConverter
import com.codereview.iou.model.dto.*
import com.codereview.iou.model.entity.PromissoryNote
import com.codereview.iou.model.validation.checkOnEmptyPromissoryNoteEntity
import com.codereview.iou.repository.PromissoryNoteRepository
import com.codereview.iou.repository.UserRepository
import com.codereview.iou.util.validate
import javassist.NotFoundException
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PromissoryNoteService(
    @Autowired
    var iouRepository: PromissoryNoteRepository,

    @Autowired
    var userRepository: UserRepository
) {

    val converter: PromissoryNoteConverter = Mappers.getMapper(PromissoryNoteConverter::class.java)

    fun addPromissoryNote(promissoryNoteDto: PromissoryNoteCreateDto): PromissoryNote {
        val promissoryNote = PromissoryNote().apply {
            lender = userRepository.findById(promissoryNoteDto.lender!!).get()
            borrower = userRepository.findById(promissoryNoteDto.borrower!!).get()
            amount = promissoryNoteDto.amount!!
        }

        promissoryNote.validate(
            "Entity {$promissoryNote} should not have empty fields.",
            ::checkOnEmptyPromissoryNoteEntity
        ).run {
            return iouRepository.save(this)
        }
    }

    fun getPromissoryNote(id: Long): PromissoryNoteDto {
        iouRepository.findById(id).get().run {
            return converter.convertToDto(this)
        }
    }

    fun deletePromissoryNote(promissoryNoteDto: PromissoryNoteDto): Int {
        if (promissoryNoteDto.id != null) {
            return if (iouRepository.existsById(promissoryNoteDto.id!!)) {
                iouRepository.delete(converter.convertToEntity(promissoryNoteDto))
                1
            } else {
                0
            }
        } else {
            throw NotFoundException("Promissory note {$promissoryNoteDto} not found.")
        }
    }

    fun listPromissoryNotes(): PromissoryNotesDto {
        val list = iouRepository.findAll()
        return PromissoryNotesDto(list.map {
            PromissoryNoteDto().apply {
                id = it.id
                lender = it.lender
                borrower = it.borrower
                amount = it.amount
            }
        })
    }

    /**
     * Подсчёт общей суммы долгов если есть несколько записей для одного и того же заёмщика и давшего в долг
     */
    fun listCalculatedSumNotes(): PromissoryNotesSummedDto {
        val list = iouRepository.findAll()

        val grouping = list
            .groupingBy { "${it.lender.id}-${it.borrower.id}" }
            .reduce { _, accumulator, element ->
                PromissoryNote().apply {
                    lender = accumulator.lender
                    borrower = accumulator.borrower
                    amount = accumulator.amount.plus(element.amount)
                }
            }
            .map {
                PromissoryNoteSummedDto().apply {
                    lender = it.value.lender
                    borrower = it.value.borrower
                    amount = it.value.amount
                }
            }

        return PromissoryNotesSummedDto(grouping)
    }
}