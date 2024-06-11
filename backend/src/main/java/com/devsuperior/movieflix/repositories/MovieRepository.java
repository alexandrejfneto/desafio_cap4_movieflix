package com.devsuperior.movieflix.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("Select obj FROM Movie obj JOIN FETCH obj.genre WHERE (:genreId IS NULL OR obj.genre.id = :genreId)")
	Page<Movie> searchMovies(PageRequest pageRequest, String genreId);

	@Query ("Select obj FROM Movie obj JOIN FETCH obj.reviews WHERE (obj.id = :id)")
	Optional<Movie> searchById(Long id);

//	@Query (nativeQuery = true, value = "SELECT tb_movie.id, tb_movie.movie_year, tb_movie.img_url, tb_movie.sub_title, tb_movie.synopsis, tb_movie.title, tb_user.email AS username, tb_review.text FROM TB_MOVIE "
//			+ "INNER JOIN TB_REVIEW "
//			+ "INNER JOIN TB_USER "
//			+ "WHERE tb_movie.id = :id"
//			+ "AND tb_review.user_id = tb_user.id "
//			+ "AND tb_review.movie_id = tb_movie.id")
//	MovieProjection searchByIdNative(Long id);

}
