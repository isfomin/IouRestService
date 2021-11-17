package com.codereview.iou.controller

import com.codereview.iou.model.dto.*
import com.codereview.iou.model.entity.PromissoryNote
import com.codereview.iou.service.PromissoryNoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/iou")
class IouController(
    @Autowired
    var service: PromissoryNoteService
) {

    @GetMapping
    fun getAllIou(): PromissoryNotesDto = service.listPromissoryNotes()

    @GetMapping("/summed")
    fun getAllSummed(): PromissoryNotesSummedDto {
        return service.listCalculatedSumNotes()
    }

    @PostMapping
    fun addIou(@RequestBody promissoryNoteDto: PromissoryNoteCreateDto): PromissoryNote {
        return service.addPromissoryNote(promissoryNoteDto)
    }

    @PutMapping
    fun editIou(): ResponseEntity<MessageDto> {
        return ResponseEntity(MessageDto("method not supported"), HttpStatus.METHOD_NOT_ALLOWED)
    }

    @DeleteMapping
    fun deleteUser(@RequestBody promissoryNoteDto: PromissoryNoteDto): ResponseEntity<RemovedMessageDto> {
        val countRemoved = service.deletePromissoryNote(promissoryNoteDto)
        return ResponseEntity(RemovedMessageDto(if (countRemoved > 0) "success" else "not found", countRemoved), HttpStatus.OK)
    }
}