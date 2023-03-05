package com.nanamare.data.local.db

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.room.RoomSQLiteQuery
import androidx.room.paging.LimitOffsetPagingSource
import androidx.room.util.getColumnIndexOrThrow
import com.nanamare.data.model.mapper.toDomainModel
import com.nanamare.domain.model.MovieModel
import javax.inject.Inject

/**
 * generate code
 */
@SuppressLint("RestrictedApi")
class TrendingMovieLimitOffsetPagingSource @Inject constructor(movieDatabase: MovieDatabase) :
    LimitOffsetPagingSource<MovieModel>(
        RoomSQLiteQuery.acquire("SELECT * FROM movie", 0),
        movieDatabase,
        "movie"
    ) {

    private val converter by lazy { ListConverter() }

    override fun convertRows(cursor: Cursor): List<MovieModel> {
        val _cursorIndexOfPrimaryKey = getColumnIndexOrThrow(cursor, "primaryKey")
        val _cursorIndexOfAdult = getColumnIndexOrThrow(cursor, "adult")
        val _cursorIndexOfBackdropPath = getColumnIndexOrThrow(cursor, "backdropPath")
        val _cursorIndexOfGenreIds = getColumnIndexOrThrow(cursor, "genreIds")
        val _cursorIndexOfId = getColumnIndexOrThrow(cursor, "id")
        val _cursorIndexOfOriginalLanguage = getColumnIndexOrThrow(cursor, "originalLanguage")
        val _cursorIndexOfOriginalTitle = getColumnIndexOrThrow(cursor, "originalTitle")
        val _cursorIndexOfOverview = getColumnIndexOrThrow(cursor, "overview")
        val _cursorIndexOfPopularity = getColumnIndexOrThrow(cursor, "popularity")
        val _cursorIndexOfPosterPath = getColumnIndexOrThrow(cursor, "posterPath")
        val _cursorIndexOfReleaseDate = getColumnIndexOrThrow(cursor, "releaseDate")
        val _cursorIndexOfTitle = getColumnIndexOrThrow(cursor, "title")
        val _cursorIndexOfVideo = getColumnIndexOrThrow(cursor, "video")
        val _cursorIndexOfVoteAverage = getColumnIndexOrThrow(cursor, "voteAverage")
        val _cursorIndexOfVoteCount = getColumnIndexOrThrow(cursor, "voteCount")
        val _result: MutableList<MovieEntity> = ArrayList(cursor.count)
        while (cursor.moveToNext()) {
            val _item: MovieEntity
            val _tmpPrimaryKey: Long = cursor.getLong(_cursorIndexOfPrimaryKey)
            val _tmpAdult: Boolean
            val _tmp: Int = cursor.getInt(_cursorIndexOfAdult)
            _tmpAdult = _tmp != 0
            val _tmpBackdropPath: String? = if (cursor.isNull(_cursorIndexOfBackdropPath)) {
                null
            } else {
                cursor.getString(_cursorIndexOfBackdropPath)
            }
            val _tmpGenreIds: List<Int>
            val _tmp_1: String? = if (cursor.isNull(_cursorIndexOfGenreIds)) {
                null
            } else {
                cursor.getString(_cursorIndexOfGenreIds)
            }
            _tmpGenreIds = converter.decode(_tmp_1.orEmpty())
            val _tmpId: Long
            _tmpId = cursor.getLong(_cursorIndexOfId)
            val _tmpOriginalLanguage: String? = if (cursor.isNull(_cursorIndexOfOriginalLanguage)) {
                null
            } else {
                cursor.getString(_cursorIndexOfOriginalLanguage)
            }
            val _tmpOriginalTitle: String? = if (cursor.isNull(_cursorIndexOfOriginalTitle)) {
                null
            } else {
                cursor.getString(_cursorIndexOfOriginalTitle)
            }
            val _tmpOverview: String? = if (cursor.isNull(_cursorIndexOfOverview)) {
                null
            } else {
                cursor.getString(_cursorIndexOfOverview)
            }
            val _tmpPopularity: Double = cursor.getDouble(_cursorIndexOfPopularity)
            val _tmpPosterPath: String? = if (cursor.isNull(_cursorIndexOfPosterPath)) {
                null
            } else {
                cursor.getString(_cursorIndexOfPosterPath)
            }
            val _tmpReleaseDate: String? = if (cursor.isNull(_cursorIndexOfReleaseDate)) {
                null
            } else {
                cursor.getString(_cursorIndexOfReleaseDate)
            }
            val _tmpTitle: String? = if (cursor.isNull(_cursorIndexOfTitle)) {
                null
            } else {
                cursor.getString(_cursorIndexOfTitle)
            }
            val _tmpVideo: Boolean
            val _tmp_2: Int = cursor.getInt(_cursorIndexOfVideo)
            _tmpVideo = _tmp_2 != 0
            val _tmpVoteAverage: Double = cursor.getDouble(_cursorIndexOfVoteAverage)
            val _tmpVoteCount: Int = cursor.getInt(_cursorIndexOfVoteCount)
            _item = MovieEntity(
                _tmpPrimaryKey, _tmpAdult,
                _tmpBackdropPath!!, _tmpGenreIds, _tmpId,
                _tmpOriginalLanguage!!,
                _tmpOriginalTitle!!,
                _tmpOverview!!, _tmpPopularity, _tmpPosterPath, _tmpReleaseDate,
                _tmpTitle!!, _tmpVideo, _tmpVoteAverage, _tmpVoteCount
            )
            _result.add(_item)
        }
        return _result.map(MovieEntity::toDomainModel)
    }
}