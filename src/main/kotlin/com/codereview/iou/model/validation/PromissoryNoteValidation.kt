package com.codereview.iou.model.validation

import com.codereview.iou.model.entity.PromissoryNote

fun checkOnEmptyPromissoryNoteEntity(promissoryNote: PromissoryNote?): Boolean {
    return promissoryNote?.lender != null
            && promissoryNote.borrower.name != ""
}