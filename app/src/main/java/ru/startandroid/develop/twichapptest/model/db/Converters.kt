package ru.startandroid.develop.twichapptest.model.db

import androidx.room.TypeConverter
import ru.startandroid.develop.twichapptest.model.data.ImageUrl

class Converters {

    @TypeConverter
    fun fromImageUrl(imageUrl: ImageUrl): String {
        return imageUrl.large
    }

    @TypeConverter
    fun toImageUrl(scale: String): ImageUrl {
        return ImageUrl(scale, null, null, null)
    }
}