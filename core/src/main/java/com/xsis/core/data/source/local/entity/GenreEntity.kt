package com.xsis.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "genre", indices = [androidx.room.Index(value = ["id"], unique = true)])
data class GenreEntity(
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String?,
)