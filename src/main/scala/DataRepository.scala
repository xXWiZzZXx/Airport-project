package model

class DataRepository(countries: List[Country], airports: List[Airport], runways: List[Runway]) {
  // Exact match for country code/name
  def findCountry(query: String): Option[Country] = {
    val upperQuery = query.toUpperCase
    countries.find(c => c.code.toUpperCase == upperQuery || c.name.toUpperCase == upperQuery)
  }

  // Get airports + runways for a country
  def getCountryAirportRunways(countryCode: String): List[(Airport, List[Runway])] = {
    airports
      .filter(_.countryCode == countryCode)
      .map(airport => (airport, runways.filter(_.airportRef == airport.id)))
  }

  // Reports
  def topCountriesByAirportCount(limit: Int, ascending: Boolean): List[(Country, Int)] = {
    val counts = airports.groupBy(_.countryCode).map {
      case (code, airports) => (countries.find(_.code == code), airports.size)
    }.collect { case (Some(country), count) => (country, count) }.toList

    val sorted = if (ascending) counts.sortBy(_._2) else counts.sortBy(-_._2)
    sorted.take(limit)
  }

  def runwaySurfacesByCountry: Map[Country, List[String]] = {
    airports.groupBy(_.countryCode).flatMap {
      case (code, airports) =>
        countries.find(_.code == code).map { country =>
          val surfaces = airports.flatMap(a => runways.filter(_.airportRef == a.id).map(_.surface)).distinct
          (country, surfaces)
        }
    }
  }

  def topRunwayIdents(limit: Int): List[(String, Int)] = {
    runways.groupBy(_.leIdent).map {
      case (ident, group) => (ident, group.size)
    }.toList.sortBy(-_._2).take(limit)
  }
}