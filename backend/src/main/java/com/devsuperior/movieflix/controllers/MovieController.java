package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;
	
	@PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> searchMovies (Pageable pageable,
			@RequestParam (value = "genreId", defaultValue = "0") String genreId){
		Page<MovieCardDTO> page = service.searchMovies(pageable, genreId);
		return ResponseEntity.ok().body(page);
	}
	
	@PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
	@GetMapping (value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> searchById (@PathVariable Long id){
		MovieDetailsDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
    
}
