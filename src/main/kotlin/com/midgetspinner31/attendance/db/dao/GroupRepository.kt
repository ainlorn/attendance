package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GroupRepository : JpaRepository<Group, UUID>