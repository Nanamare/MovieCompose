package com.nanamare.data.model

import com.google.common.truth.Truth.assertThat
import com.nanamare.data.TestData
import com.nanamare.data.source.local.db.MovieEntity
import com.nanamare.data.source.local.db.MovieRemoteKeyEntity
import com.nanamare.data.model.mapper.toDomainModel
import com.nanamare.data.model.mapper.toEntity
import com.nanamare.domain.model.DatesModel
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieRemoteKeyModel
import com.nanamare.domain.model.MovieResponseModel
import org.junit.Test
import java.time.Instant

class MovieTest {

    @Test
    fun date_dto_can_be_mapped_to_domain_model() {
        val dateDto: DatesDto = TestData.Movie.datesDto

        val dataModel: DatesModel = dateDto.toDomainModel()

        assertThat(dataModel.maximum).isEqualTo("2022-05-21")
        assertThat(dataModel.minimum).isEqualTo("2022-04-21")
    }

    @Test
    fun movie_dto_can_be_mapped_to_domain_model() {
        val movieDto: MovieDto = TestData.Movie.movieDto

        val movieModel: MovieModel = movieDto.toDomainModel()

        assertThat(movieModel.originalTitle).isEqualTo("Matrix")
        assertThat(movieModel.title).isEqualTo("Matrix")
        assertThat(movieModel.id).isEqualTo(50)
        assertThat(movieModel.overview).isEqualTo("String recommended")
        assertThat(movieModel.genreIds).isEqualTo(listOf(1, 2, 3))
        assertThat(movieModel.releaseDate).isEqualTo("2022-05-21")
        assertThat(movieModel.voteAverage).isEqualTo(7.7)
        assertThat(movieModel.voteCount).isEqualTo(5111)
    }

    @Test
    fun movie_response_dto_can_be_mapped_to_domain_model() {
        val movieResponse: MovieResponseDto = TestData.Movie.movieResponseDto

        val movieResponseModel: MovieResponseModel = movieResponse.toDomainModel()

        assertThat(movieResponseModel.page).isEqualTo(1)
        assertThat(movieResponseModel.totalPages).isEqualTo(1)
        assertThat(movieResponseModel.totalResults).isEqualTo(2)
        assertThat(movieResponseModel.dates).isEqualTo(TestData.Movie.datesDto.toDomainModel())
        assertThat(movieResponseModel.movies[0]).isEqualTo(TestData.Movie.movieDto.toDomainModel())
    }

    @Test
    fun movie_entity_can_be_mapped_to_domain_model() {
        val movieEntity: MovieEntity = TestData.Movie.movieEntity

        val movieDomainModel = movieEntity.toDomainModel()

        assertThat(movieDomainModel.originalTitle).isEqualTo("Matrix")
        assertThat(movieDomainModel.title).isEqualTo("Matrix")
        assertThat(movieDomainModel.id).isEqualTo(50)
        assertThat(movieDomainModel.overview).isEqualTo("String recommended")
        assertThat(movieDomainModel.genreIds).isEqualTo(listOf(1, 2, 3))
        assertThat(movieDomainModel.releaseDate).isEqualTo("2022-05-21")
        assertThat(movieDomainModel.voteAverage).isEqualTo(7.7)
        assertThat(movieDomainModel.voteCount).isEqualTo(5111)
    }

    @Test
    fun movie_domain_can_be_mapped_to_entity() {
        val movieModel: MovieModel = TestData.Movie.movieModel

        val movieEntity = movieModel.toEntity()

        assertThat(movieEntity.originalTitle).isEqualTo("Matrix")
        assertThat(movieEntity.title).isEqualTo("Matrix")
        assertThat(movieEntity.id).isEqualTo(50)
        assertThat(movieEntity.overview).isEqualTo("String recommended")
        assertThat(movieEntity.genreIds).isEqualTo(listOf(1, 2, 3))
        assertThat(movieEntity.releaseDate).isEqualTo("2022-05-21")
        assertThat(movieEntity.voteAverage).isEqualTo(7.7)
        assertThat(movieEntity.voteCount).isEqualTo(5111)
    }

    @Test
    fun movie_remote_key_entity_can_be_mapped_to_domain_model() {
        val movieRemoteKeyEntity: MovieRemoteKeyEntity = TestData.Movie.movieRemoteKeyEntity

        val movieRemoteKeyDomainModel = movieRemoteKeyEntity.toDomainModel()

        assertThat(movieRemoteKeyDomainModel.id).isEqualTo(7)
        assertThat(movieRemoteKeyDomainModel.prevPage).isEqualTo(1)
        assertThat(movieRemoteKeyDomainModel.nextPage).isEqualTo(3)
        assertThat(movieRemoteKeyDomainModel.lastUpdated).isEqualTo(Instant.EPOCH.epochSecond)
    }

    @Test
    fun movie_remote_key_domain_model_can_be_mapped_to_entity() {
        val movieRemoteKeyModel: MovieRemoteKeyModel = TestData.Movie.movieRemoteKeyModel

        val movieRemoteKeyEntity = movieRemoteKeyModel.toEntity()

        assertThat(movieRemoteKeyEntity.id).isEqualTo(7)
        assertThat(movieRemoteKeyEntity.prevPage).isEqualTo(1)
        assertThat(movieRemoteKeyEntity.nextPage).isEqualTo(3)
        assertThat(movieRemoteKeyEntity.lastUpdated).isEqualTo(Instant.EPOCH.epochSecond)
    }

}