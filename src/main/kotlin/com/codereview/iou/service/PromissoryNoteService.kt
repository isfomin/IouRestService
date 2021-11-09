package com.codereview.iou.service

import com.codereview.iou.model.converter.PromissoryNoteConverter
import com.codereview.iou.model.dto.*
import com.codereview.iou.model.entity.PromissoryNote
import com.codereview.iou.repository.PromissoryNoteRepository
import com.codereview.iou.repository.UserRepository
import javassist.NotFoundException
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PromissoryNoteService {

    @Autowired
    lateinit var iouRepository: PromissoryNoteRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val converter: PromissoryNoteConverter = Mappers.getMapper(PromissoryNoteConverter::class.java)

    fun addPromissoryNote(promissoryNoteDto: PromissoryNoteCreateDto): PromissoryNote {
        val promissoryNote = PromissoryNote().apply {
            lender = userRepository.findByIdOrNull(promissoryNoteDto.lender)
            borrower = userRepository.findByIdOrNull(promissoryNoteDto.borrower)
            amount = promissoryNoteDto.amount
        }

        if (validatePromissoryNoteEntity(promissoryNote)) {
            return iouRepository.save(promissoryNote)
        } else {
            throw IllegalArgumentException("Entity {$promissoryNote} not valid.")
        }
    }

    fun getPromissoryNote(id: Long): PromissoryNoteDto {
        val user = iouRepository.findById(id).get()
        return converter.convertToDto(user)
    }

    fun deletePromissoryNote(promissoryNoteDto: PromissoryNoteDto) {
        if (promissoryNoteDto.id != null) {
            iouRepository.delete(converter.convertToEntity(promissoryNoteDto))
        } else {
            throw NotFoundException("Promissory note $promissoryNoteDto not found.")
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
            .groupingBy { "${it.lender?.id}-${it.borrower?.id}" }
            .reduce { _, accumulator, element ->
                PromissoryNote().apply {
                    lender = accumulator.lender
                    borrower = accumulator.borrower
                    amount = accumulator.amount?.plus(element.amount!!)
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

    fun validatePromissoryNoteEntity(promissoryNote: PromissoryNote?): Boolean {
        return promissoryNote?.lender != null
                && promissoryNote.borrower != null
                && promissoryNote.amount != null
    }
}