package com.codereview.iou.model.dto

class PromissoryNoteCreateDto() {
    var id: Long? = null
    var lender: Long? = null
    var borrower: Long? = null
    var amount: Double? = null
}