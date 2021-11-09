package com.codereview.iou.repository

import com.codereview.iou.model.entity.PromissoryNote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PromissoryNoteRepository : JpaRepository<PromissoryNote, Long>