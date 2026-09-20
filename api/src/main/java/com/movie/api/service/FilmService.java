package com.movie.api.service;

import com.movie.api.model.entity.Film;

import java.util.List;

public interface FilmService {

    void save(Film film);

    void deleteById(String id);

    List<Film> findAll();

    List<Film> findByRegionAndType(String region, String type);

    //Get popular films
    List<Film> findHots(Integer limit);

    //Search films by partial name
    List<Film> findLikeName(String name);

    Film findById(String id);

    Film update(Film film);

}
