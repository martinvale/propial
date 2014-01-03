package com.ibiscus.propial.domain.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PublicationRepository {

  /** Search for the publications in the database.
   *
   * @param start The start position of the hits.
   * @param count The count of hits to retrieve.
   * @param order The order to search.
   * @param asc The direction of the order to apply.
   * @param filters The filters to apply to the search.
   * @return The list of publications.
   */
  public List<Publication> getPublications(final int start, final int count,
      final String order, final boolean asc,
      final Map<String, String> filters) {
    List<Publication> advertises = new ArrayList<Publication>();
    advertises.add(new Publication());
    advertises.add(new Publication());
    return advertises;
  }
}
