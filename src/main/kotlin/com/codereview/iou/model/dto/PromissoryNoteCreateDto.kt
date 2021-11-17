package com.codereview.iou.model.dto

data class PromissoryNoteCreateDto(
    var id: Long? = null,
    var lender: Long? = null,
    var borrower: Long? = null,
    var amount: Double? = null
)