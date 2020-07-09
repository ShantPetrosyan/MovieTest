package com.example.mymovies.view.mapper

import com.example.mymovies.data.model.moviedetail.MovieDetailInfo
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.data.response.moviedetail.*
import com.example.mymovies.data.response.movielist.MoviesResponse

object MoviesMapper {
    fun convertToModel(response: MoviesResponse): List<MovieInfo> {
        val result = ArrayList<MovieInfo>()
        response.results.map { responseMovieItem ->
            result.add(
                MovieInfo(
                    responseMovieItem.id,
                    responseMovieItem.posterPath,
                    responseMovieItem.adult,
                    responseMovieItem.title,
                    responseMovieItem.voteAverage,
                    responseMovieItem.overview,
                    responseMovieItem.releaseDate,
                    response.page
                )
            )
        }

        return result
    }

    fun convertToModel(response: MovieDetailResponse): MovieDetailInfo {
        return MovieDetailInfo(
            response.backdropPath,
            response.budget,
            toGenreModel(response.genres),
            response.homepage,
            response.id,
            response.originalTitle,
            response.overview,
            response.posterPath,
            toCompanyModel(response.productionCompanies),
            toCountriesModel(response.productionCountries),
            response.releaseDate,
            toLanguageModel(response.spokenLanguages),
            response.voteAverage,
            response.voteCount
        )
    }

    private fun toLanguageModel(spokenLanguages: List<SpokenLanguage>): String {
        var result = ""
        spokenLanguages.map {
            result += it.name + ", "
        }
        return result
    }

    private fun toCountriesModel(productionCountries: List<ProductionCountry>): String {
        var result = ""
        productionCountries.map {
            result += it.name + ", "
        }
        return result
    }

    private fun toCompanyModel(productionCompanies: List<ProductionCompany>): String {
        var result = ""
        productionCompanies.map {
            result += "${it.name} (${it.originCountry}), "
        }
        return result
    }

    private fun toGenreModel(genres: List<Genre>): String {
        var result = ""
        genres.map {
            result += it.name + ", "
        }
        return result
    }
}