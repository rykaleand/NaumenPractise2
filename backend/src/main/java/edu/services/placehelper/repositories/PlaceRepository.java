package edu.services.placehelper.repositories;

import edu.services.placehelper.entities.Place;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long>
{
}
