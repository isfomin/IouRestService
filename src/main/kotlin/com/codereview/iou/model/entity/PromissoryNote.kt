package com.codereview.iou.model.entity

import javax.persistence.*

@Entity(name = "promissory_note")
class PromissoryNote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @OneToOne
    @JoinColumn(name = "lender_id", nullable = false)
    var lender: User = User(),

    @OneToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    var borrower: User = User(),

    @Column(name = "amount", nullable = false)
    var amount: Double = 0.00
)