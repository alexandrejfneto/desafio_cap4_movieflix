package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

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
		Review entity = new Review ();
		entity.setText(reviewDto.getText());
		
		Movie movie = movieRepository.findById(reviewDto.getMovieId()).get();
		entity.setMovie(movie);
		
		User user = authService.authenticated();
		entity.setUser(user);
		
		entity = repository.save(entity);
		
		reviewDto = new ReviewDTO(entity);
		return reviewDto;
	}
	
}
