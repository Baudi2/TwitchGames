package ru.startandroid.develop.twichapptest.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.startandroid.develop.twichapptest.model.remote.Game
import ru.startandroid.develop.twichapptest.model.remote.GameItem

data class GameItemWithGame(
    @Embedded val gameItem: GameItem,
    @Relation(
        parentColumn = "viewers",
        entityColumn = "id"
    )
    val games: List<Game>
)
