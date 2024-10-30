package com.midgetspinner31.attendance.db.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    val id: UUID? = null

    @Column(name = "name", nullable = false)
    val name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    val trainer: Trainer? = null

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val groupMemberships: List<GroupMembership> = mutableListOf()
}