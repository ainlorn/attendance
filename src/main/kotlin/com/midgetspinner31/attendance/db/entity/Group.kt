package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.GroupDto
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    var trainer: Trainer? = null

    companion object {
        fun Group.toDto(): GroupDto {
            return GroupDto(
                id = this.id,
                name = this.name,
                trainer = this.trainer?.toDto()
            )
        }
    }
}
