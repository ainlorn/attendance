package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.GroupDto
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
class Group(name: String) {
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

    companion object {
        fun Group.toDto(): GroupDto {
            return GroupDto(
                id = this.id,
                name = this.name,
                trainer = this.trainer?.toDto(),
                students = this.groupMemberships.map { it.student!!.toDto() }.toMutableList()
            )
        }
    }
}