package com.ibiscus.propial.domain.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.ibiscus.propial.domain.business.Location;
import com.ibiscus.propial.domain.business.LocationRepository;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.domain.filters.Dimension;
import com.ibiscus.propial.domain.filters.Member;
import com.ibiscus.propial.web.utils.QueryResults;

public class FilterService {

  final PublicationRepository publicationRepository;

  final LocationRepository locationRepository;

  public FilterService(final PublicationRepository thePublicationRepository,
      final LocationRepository theLocationRepository) {
    Validate.notNull(thePublicationRepository, "The publication repository"
        + " cannot be null");
    Validate.notNull(theLocationRepository, "The location repository"
        + " cannot be null");
    publicationRepository = thePublicationRepository;
    locationRepository = theLocationRepository;
  }

  /** Get the list of filters that can be applied to the current hits to refine
   * the results.
   *
   * @param filters The filters applied to the search.
   *
   * @return The list of filters that can be applied to the hits.
   */
  public List<Dimension> getDimensions(final Map<String, Object> filters,
      final int size) {
    List<Dimension> dimensions = new LinkedList<Dimension>();

    Dimension dimension = new Dimension("location", "Ubicacion");
    Location location = (Location) filters.get("location");
    QueryResults<Location> locations = locationRepository.find(location, 0, -1);
    Map<String, Object> auxFilters = new HashMap<String, Object>(filters);
    for (Location childLocation : locations.getItems()) {
      auxFilters.put("location", childLocation);
      int count = publicationRepository.getCount(auxFilters);
      if (count > 0 && count < size) {
        dimension.getMembers().add(new Member(childLocation.getName(),
            childLocation.getId().toString(), count));
      }
    }
    if (!dimension.getMembers().isEmpty()) {
      dimensions.add(dimension);
    }

    if (!filters.containsKey("type")) {
      dimension = new Dimension("type", "Tipo");
      auxFilters = new HashMap<String, Object>(filters);
      for (Publication.TYPES type : Publication.TYPES.values()) {
        auxFilters.put("type", type.toString().toLowerCase());
        int count = publicationRepository.getCount(auxFilters);
        if (count > 0 && count < size) {
          dimension.getMembers().add(new Member(type.toString().toLowerCase(),
              type.toString().toLowerCase(), count));
        }
      }
      if (!dimension.getMembers().isEmpty()) {
        dimensions.add(dimension);
      }
    }

    return dimensions;
  }
}
