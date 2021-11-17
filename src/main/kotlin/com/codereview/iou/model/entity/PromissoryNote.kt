package com.codereview.iou.model.entity

import javax.persistence.*

@Entity(name = "promissory_note")
class PromissoryNote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "lender_id", nullable = false)
    var lender: User? = null,

    @OneToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    var borrower: User? = null,

    @Column(name = "amount", nullable = false)
    var amount: Double? = null
)