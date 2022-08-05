package ru.netology.nework.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.google.gson.Gson
import ru.netology.nework.data.local.PostEntity
import ru.netology.nework.model.Coordinates
import ru.netology.nework.model.attachment.Attachment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Dao
interface PostDao {

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun pagingSource(): DataSource.Factory<Int, PostEntity>

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM PostEntity WHERE isNew = 'true' ORDER BY id ")
    fun getNewer(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: List<PostEntity>)

    @Query("DELETE FROM PostEntity")
    suspend fun removeAll()
}


class Converters {
    @TypeConverter
    fun toAttachment(value: String): Attachment? {
        return Gson().fromJson(value, Attachment::class.java)
    }

    @TypeConverter
    fun fromAttachment(attachment: Attachment?): String {
        return Gson().toJson(attachment)
    }


    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return if (value.isBlank()) {
            listOf()
        } else
            value
                .trim()
                .splitToSequence(',')
                .map {
                    it.trim().toInt()
                }
                .toList()
    }

    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        return list.toIntArray().joinToString()
    }


    @TypeConverter
    fun toLocalDateTime(localDateTime: String): LocalDateTime {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(localDateTime, LocalDateTime::from)
    }

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date)
    }

    @TypeConverter
    fun toCoordinates(value: String): Coordinates? {
        return Gson().fromJson(value, Coordinates::class.java)
    }

    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates?): String {
        return Gson().toJson(coordinates)
    }
}