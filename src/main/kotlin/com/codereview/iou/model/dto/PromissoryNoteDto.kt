package com.codereview.iou.model.dto

import com.codereview.iou.model.entity.User

class PromissoryNoteDto() {
    var id: Long? = null
    var lender: User? = null
    var borrower: User? = null
    var amount: Double? = null
}