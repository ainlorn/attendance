package com.midgetspinner31.attendance.db.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@Table(name = "persistent_login")
class PersistentLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @Column(name = "username", nullable = false)
    var username: String? = null

    @Column(name = "series", nullable = false, unique = true)
    var series: String? = null

    @Column(name = "token", nullable = false, unique = true)
    var token: String? = null

    @Column(name = "last_used", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    var lastUsed: Date? = null
}
