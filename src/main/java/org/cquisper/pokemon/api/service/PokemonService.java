package org.cquisper.pokemon.api.service;

import org.cquisper.pokemon.api.client.Paginacion;
import org.cquisper.pokemon.api.models.Pokemon;

import java.util.List;
import java.util.Optional;

public interface PokemonService {
    List<Pokemon> listar(Paginacion paginacion);

    Optional<Pokemon> porNombre(String nombre);
}
