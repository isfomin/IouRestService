package com.codereview.iou.model.entity

import javax.persistence.*

@Entity(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String = ""
)