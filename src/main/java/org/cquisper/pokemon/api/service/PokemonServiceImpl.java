package org.cquisper.pokemon.api.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.cquisper.pokemon.api.client.Paginacion;
import org.cquisper.pokemon.api.client.RestClient;
import org.cquisper.pokemon.api.models.Pokemon;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class PokemonServiceImpl implements PokemonService{
    @Inject
    private RestClient<Pokemon> restClient;

    @Override
    public List<Pokemon> listar(Paginacion paginacion) {
        return restClient.getListar(paginacion);
    }

    @Override
    public Optional<Pokemon> porNombre(String nombre) {
        return Optional.ofNullable(restClient.getNombre(nombre));
    }
}
