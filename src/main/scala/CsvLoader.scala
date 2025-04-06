package model

import scala.io.Source
import scala.io.Codec

object CsvLoader {
  def loadCountries: List[Country] = {
    val source = Source.fromResource("countries.csv")(Codec.UTF8)
    val lines = source.getLines().drop(1).toList
    val countries = lines.flatMap(Country.fromCsv)
    source.close()
    countries
  }

  def loadAirports: List[Airport] = {
    val source = Source.fromResource("airports.csv")(Codec.UTF8)
    val lines = source.getLines().drop(1).toList
    val airports = lines.flatMap(Airport.fromCsv)
    source.close()
    airports
  }

  def loadRunways: List[Runway] = {
    val source = Source.fromResource("runways.csv")(Codec.UTF8)
    val lines = source.getLines().drop(1).toList
    val runways = lines.flatMap(Runway.fromCsv)
    source.close()
    runways
  }
}
