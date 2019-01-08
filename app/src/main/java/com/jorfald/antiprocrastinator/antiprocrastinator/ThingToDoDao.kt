package com.jorfald.antiprocrastinator.antiprocrastinator

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ThingToDoDao {
    @Query("SELECT * FROM thingtodo")
    fun getAll(): List<ThingToDo>

    @Query("SELECT * FROM thingtodo WHERE uid IN (:Ids)")
    fun loadAllByIds(Ids: IntArray): List<ThingToDo>

    @Query("SELECT * FROM thingtodo WHERE uid LIKE :id LIMIT 1")
    fun findById(id: Int): ThingToDo

    @Query("SELECT * FROM thingtodo WHERE to_do_name LIKE :name LIMIT 1")
    fun findByName(name: String): ThingToDo

    @Insert
    fun insert(thingToDo: ThingToDo)

    @Delete
    fun delete(thingToDo: ThingToDo)
}