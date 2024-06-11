package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query ("Select obj FROM Movie obj JOIN FETCH obj.genre WHERE (:genreId IS NULL OR obj.genre.id = :genreId)")
	Page<Movie> searchMovies(PageRequest pageRequest, String genreId);

}
