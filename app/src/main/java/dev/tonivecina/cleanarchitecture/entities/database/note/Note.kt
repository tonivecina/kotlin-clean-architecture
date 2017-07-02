package dev.tonivecina.cleanarchitecture.entities.database.note

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * @author Toni Vecina on 6/25/17.
 */
@Entity(tableName = "note")
class Note: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "createdAt")
    var createdAt: Long? = null

    constructor(): super()

    constructor(title: String, description: String, createdAt: Date) {
        this.title = title
        this.description = description

        val milliseconds = createdAt.time
        this.createdAt = milliseconds
    }

    @Ignore
    fun createdAtDate(): Date? {

        if (createdAt == null) {
            return null
        }

        val dateUpdated = createdAt!!

        return Date(dateUpdated)
    }

}