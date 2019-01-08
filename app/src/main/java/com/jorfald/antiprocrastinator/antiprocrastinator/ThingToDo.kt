package com.jorfald.antiprocrastinator.antiprocrastinator

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ThingToDo(
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "to_do_name") var toDoName: String,
    @ColumnInfo(name = "to_do_minutes") var minutes: Int = 0
)