package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private AuthService authService;

	@Transactional
	public ReviewDTO insert(ReviewDTO reviewDto) {

		User user = authService.authenticated();
		try {
			Movie movie = movieRepository.getReferenceById(reviewDto.getMovieId());
			Review entity = new Review();
			entity.setText(reviewDto.getText());
			entity.setUser(user);
			entity.setMovie(movie);

			entity = repository.save(entity);

			reviewDto = new ReviewDTO(entity);
			return reviewDto;
		} catch (EntityNotFoundException e) {
			throw new DatabaseException("MovieId not found: " + reviewDto.getMovieId());
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("DataIntegrityViolationException, MovieId not found: " + reviewDto.getMovieId());
		}
		
	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> searchByIdMovie(Long idMovie) {
		if (!movieRepository.existsById(idMovie)) {
			throw new ResourceNotFoundException("Id not found: " + idMovie);
		}
		List<Review> result = repository.searchByIdMovie(idMovie);
		List<ReviewDTO> listDTO = result.stream().map(x -> new ReviewDTO(x.getUser().getUsername(), x.getText()))
				.collect(Collectors.toList());
		return listDTO;
	}

}
