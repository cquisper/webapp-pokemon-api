package org.cquisper.pokemon.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.primefaces.PrimeFaces;

@ApplicationScoped
public class ProducerResources {

    @Produces
    @RequestScoped
    public Client beanRestClient(){
        return ClientBuilder.newClient();
    }

    private void close(@Disposes Client client){
        client.close();
    }

    @Produces
    @RequestScoped
    public PrimeFaces beanPrimeFaces(){
        return PrimeFaces.current();
    }
}
