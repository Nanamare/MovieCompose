package com.nanamare.data.model

import com.google.common.truth.Truth.assertThat
import com.nanamare.data.TestData
import com.nanamare.data.model.mapper.toDomainModel
import com.nanamare.domain.model.GenreListModel
import com.nanamare.domain.model.GenreModel
import org.junit.Test

class GenreTest {

    @Test
    fun action_genre_dto_can_be_mapped_domain_model() {
        val actionGenreDto: GenreDto = TestData.Genre.actionGenreDto

        val actionGenreDomainModel: GenreModel = actionGenreDto.toDomainModel()

        assertThat(actionGenreDomainModel.id).isEqualTo(0)
        assertThat(actionGenreDomainModel.name).isEqualTo("Action")
    }

    @Test
    fun comedy_genre_dto_can_be_mapped_domain_model() {
        val comedyGenreDto: GenreDto = TestData.Genre.comedyGenreDto

        val comedyGenreDomainModel: GenreModel = comedyGenreDto.toDomainModel()

        assertThat(comedyGenreDomainModel.id).isEqualTo(1)
        assertThat(comedyGenreDomainModel.name).isEqualTo("Comedy")
    }

    @Test
    fun genre_list_dto_can_be_mapped_domain_model() {
        val genreListDto: GenreListDto = TestData.Genre.genreListDto

        val genreListDomainModel: GenreListModel = genreListDto.toDomainModel()

        assertThat(genreListDomainModel.genres.size).isEqualTo(2)
        assertThat(genreListDomainModel.genres[0]).isEqualTo(TestData.Genre.actionGenreDto.toDomainModel())
        assertThat(genreListDomainModel.genres[1]).isEqualTo(TestData.Genre.comedyGenreDto.toDomainModel())
    }

}