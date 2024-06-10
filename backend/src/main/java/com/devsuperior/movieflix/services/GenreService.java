package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;

@Service
public class GenreService {

	@Autowired
	private GenreRepository repository;

	public List<GenreDTO> findAllGenres() {
		List<Genre> list = repository.findAll();
		List<GenreDTO> listDTO = list.stream().map(x -> new GenreDTO(x)).collect(Collectors.toList());
		return listDTO;
	}
	
}
