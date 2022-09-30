package org.cquisper.pokemon.api.client;

import java.util.List;

public interface RestClient<T> {
    List<T> getListar(Paginacion paginacion);

    T getNombre(String nombre);
}
