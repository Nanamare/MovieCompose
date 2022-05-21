package com.nanamare.data.model

import com.google.common.truth.Truth.assertThat
import com.nanamare.data.TestData
import com.nanamare.data.model.mapper.toDomainModel
import com.nanamare.domain.model.DatesModel
import com.nanamare.domain.model.MovieModel
import com.nanamare.domain.model.MovieResponseModel
import org.junit.Test

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

}