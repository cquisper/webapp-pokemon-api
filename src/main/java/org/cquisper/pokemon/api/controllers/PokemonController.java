package org.cquisper.pokemon.api.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.cquisper.pokemon.api.client.Paginacion;
import org.cquisper.pokemon.api.models.Pokemon;
import org.cquisper.pokemon.api.service.PokemonService;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PokemonController implements Serializable {
    @Inject
    private PokemonService pokemonService;

    private List<Pokemon> pokemons;

    private boolean botonAnterior;

    private String txtBuscar;

    private Pokemon pokemon;

    private int tiempo;

    @PostConstruct
    private void init() {
        this.pokemons = pokemonService.listar(Paginacion.VACIO);
        pokemon = new Pokemon();
        this.tiempo = 4;
    }

    @Produces
    @Model
    public String titulo() {
        return "Pokemon Api";
    }

    public void anterior() {
        System.out.println("Anterior.....");
        List<Pokemon> pokemonList = pokemonService.listar(Paginacion.ANTERIOR);
        if (pokemonList.isEmpty()) {
            this.botonAnterior = true;
        } else {
            this.pokemons = pokemonList;
            this.botonAnterior = false;
        }
        System.out.println(botonAnterior);
    }

    public void siguiente() {
        System.out.println("Siguiente.....");
        this.botonAnterior = false;
        System.out.println(botonAnterior);
        this.pokemons = pokemonService.listar(Paginacion.SIGUIENTE);
    }

    public void buscarPokemon() {
        this.tiempo--;
        if(tiempo < -1) {
            System.out.println("Parando el tiempo!! " + this.tiempo);
            PrimeFaces.current().executeScript("PF('intervalBuscar').stop()");
        }else if(tiempo < 0){
            pokemonService.porNombre(this.txtBuscar).ifPresent(pokemon1 -> this.pokemon = pokemon1);
            PrimeFaces.current().ajax().update("dialogP");
            PrimeFaces.current().executeScript("PF('dialogPokemon').show()");
        }else {
            System.out.println(tiempo);
        }
    }

    public void reset() {
        this.pokemon = new Pokemon();
        this.tiempo = 4;
        PrimeFaces.current().resetInputs("form");
        PrimeFaces.current().ajax().update("dialogP");
        PrimeFaces.current().ajax().update("form");
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public boolean isBotonAnterior() {
        return botonAnterior;
    }

    public void setBotonAnterior(boolean botonAnterior) {
        this.botonAnterior = botonAnterior;
    }


    public String getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(String txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
}
