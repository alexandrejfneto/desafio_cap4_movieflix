package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllMovies(Pageable pageable) {
		Page<Movie> list = repository.findAll(pageable);
		Page<MovieCardDTO> listDTO = list.map(x -> new MovieCardDTO(x));
		return listDTO;
	}

	@Transactional
	public MovieDetailsDTO findById(Long id) {

		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
		MovieDetailsDTO dto = new MovieDetailsDTO(entity);
		return dto;

	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> searchMovies(Pageable pageable, String genreId) {
		
		if (genreId.equals("0")) {
			genreId = null;
		}
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title"));

		Page<Movie> list = repository.searchMovies(pageRequest, genreId);
		Page<MovieCardDTO> listDTO = list.map(x -> new MovieCardDTO(x));

		return listDTO;
	}

}
