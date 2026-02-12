package edu.services.placehelper.controllers;


import edu.services.placehelper.entities.Place;
import edu.services.placehelper.repositories.PlaceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.*;

@Controller
public class PlaceController
{
    private final PlaceRepository placeRepository;

    PlaceController(PlaceRepository placeRepository)
    {
        this.placeRepository = placeRepository;
    }

    @Get("/places")
    HttpResponse<List<Place>> getPlaces()
    {
        final List<Place> places = placeRepository.findAll();

        if(places.isEmpty())
            return HttpResponse.noContent();

        return HttpResponse.ok(places);
    }

    @Get("/places/{id}")
    HttpResponse getPlace(@QueryValue long id)
    {
        final Optional<Place> place = placeRepository.findById(id);

        if(place.isEmpty())
            return HttpResponse.notFound();

        return HttpResponse.ok(place.get());
    }

    @Post("/places/new_place")
    HttpResponse<Place> createPlace(@Body Place newPlace)
    {
        placeRepository.save(newPlace);

        return HttpResponse.created(newPlace);
    }

    @Put("/places/{id}")
    HttpResponse<Place> updatePlace(@QueryValue long id, @Body Place updatedPlace)
    {
        final Optional<Place> placeBefore = placeRepository.findById(id);

        if(placeBefore.isEmpty())
            return HttpResponse.notFound();

        if(updatedPlace.isTitleSet())
            placeBefore.get().setTitle(updatedPlace.getTitle());
        if(updatedPlace.isTypeSet())
            placeBefore.get().setType(updatedPlace.getType());
        if(updatedPlace.isAddressSet())
            placeBefore.get().setAddress(updatedPlace.getAddress());
        if(updatedPlace.isArchitectSet())
            placeBefore.get().setArchitect(updatedPlace.getArchitect().isPresent()? updatedPlace.getArchitect().get() : null);
        if(updatedPlace.isPopularityScoreSet())
            placeBefore.get().setPopularityScore(updatedPlace.getPopularityScore());
        if(updatedPlace.isDescriptionSet())
            placeBefore.get().setDescription(updatedPlace.getDescription().isPresent()? updatedPlace.getDescription().get() : null);

        placeRepository.update(placeBefore.get());

        return HttpResponse.ok(placeBefore.get());
    }

    @Delete("/places/{id}")
    HttpResponse<Place> deletePlace(@QueryValue long id)
    {
        final Optional<Place> placeToDelete = placeRepository.findById(id);

        if(placeToDelete.isEmpty())
            return HttpResponse.notFound();

        placeRepository.deleteById(id);

        return HttpResponse.ok(placeToDelete.get());
    }

}
