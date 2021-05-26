package ru.startandroid.develop.twichapptest.model

import androidx.room.TypeConverter
import ru.startandroid.develop.twichapptest.model.remote.ImageUrl

class Converters {

    @TypeConverter
    fun fromImageUrl(imageUrl: ImageUrl) : String {
        return imageUrl.large
    }

    @TypeConverter
    fun toImageUrl(scale: String) : ImageUrl {
        return ImageUrl(scale, null, null, null)
    }
}