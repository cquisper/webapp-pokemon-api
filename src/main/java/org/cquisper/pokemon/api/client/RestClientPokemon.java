package org.cquisper.pokemon.api.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.cquisper.pokemon.api.models.Pokemon;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class RestClientPokemon implements RestClient<Pokemon>{
    private static String REST_URI_POKEMON = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=12";
    private static String NEXT_URI = "", PREVIUS_URI = "";
    @Inject
    private Client client;

    @Override
    public List<Pokemon> getListar(Paginacion paginacion) {
        List<Pokemon> pokemons = new ArrayList<>();
        switch (paginacion) {
            case ANTERIOR -> {
                REST_URI_POKEMON = PREVIUS_URI;
            }
            case SIGUIENTE -> {
                REST_URI_POKEMON = NEXT_URI;
            }
        }
        System.out.println(PREVIUS_URI);
        System.out.println(NEXT_URI);
        System.out.println(REST_URI_POKEMON);
        if(!Objects.equals(REST_URI_POKEMON, "null")) {
            Response response = client.target(REST_URI_POKEMON).request(MediaType.APPLICATION_JSON).get(Response.class);

            if (response.getStatusInfo().equals(Response.Status.OK)) {

                String jsonPokemon = response.readEntity(String.class);

                JSONObject jsonObjectPokemon = new JSONObject(jsonPokemon);

                PREVIUS_URI = jsonObjectPokemon.get("previous").toString();
                NEXT_URI = jsonObjectPokemon.getString("next");

                System.out.println(PREVIUS_URI);
                System.out.println(NEXT_URI);

                JSONArray jsonArrayPokemon = jsonObjectPokemon.getJSONArray("results");

                jsonArrayPokemon.forEach(o -> {
                    pokemons.add(getPokemon(((JSONObject) o).getString("url")));
                });
            }
        }

        return pokemons;
    }

    @Override
    public Pokemon getNombre(String nombre) {
        String urlTarget = "https://pokeapi.co/api/v2/pokemon/" + nombre.toLowerCase();

        Pokemon pokemon = getPokemon(urlTarget);

        return pokemon;
    }

    private Pokemon getPokemon(String urlTarget){
        Pokemon pokemon = new Pokemon();
        Response response = client.target(urlTarget).request(MediaType.APPLICATION_JSON).get(Response.class);
        if(response.getStatusInfo().equals(Response.Status.OK)){
            String jsonPokemon = response.readEntity(String.class);
            JSONObject jsonObjectPokemon = new JSONObject(jsonPokemon);

            Long id = jsonObjectPokemon.getLong("id");
            String nombre = jsonObjectPokemon.getString("name");
            String imagenUrl = jsonObjectPokemon.getJSONObject("sprites").getJSONObject("other")
                    .getJSONObject("dream_world").getString("front_default");
            String tipo = jsonObjectPokemon.getJSONArray("types").getJSONObject(0)
                    .getJSONObject("type").getString("name");

            pokemon.setId(id);
            pokemon.setNombre(nombre);
            pokemon.setImagenUrl(imagenUrl);
            pokemon.setTipo(tipo);
        }
        return pokemon;
    }
}
