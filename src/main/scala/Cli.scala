package ui

import model.{CsvLoader, DataRepository}
import scala.io.StdIn

object Cli {
  def run(): Unit = {
    println("Loading data...")
    val repo = new DataRepository(
      CsvLoader.loadCountries,
      CsvLoader.loadAirports,
      CsvLoader.loadRunways
    )

    mainMenu(repo)
  }

  private def mainMenu(repo: DataRepository): Unit = {
    println("\n=== Airport Data Explorer ===")
    println("1. Query by country")
    println("2. View reports")
    println("3. Exit")
    print("Choose an option: ")

    StdIn.readLine() match {
      case "1" => queryCountry(repo)
      case "2" => reportsMenu(repo)
      case "3" => println("Goodbye!")
      case _   => mainMenu(repo)
    }
  }

  private def queryCountry(repo: DataRepository): Unit = {
    print("Enter country name/code: ")
    val query = StdIn.readLine()

    repo.findCountry(query) match {
      case Some(country) =>
        println(s"\n${country.name} (${country.code}):")
        repo.getCountryAirportRunways(country.code).foreach { 
          case (airport, runways) =>
            println(s"\n  Airport: ${airport.name}")
            if (runways.isEmpty) println("    No runway data")
            else runways.foreach(r => println(s"    Runway: ${r.surface} (${r.leIdent})"))
        }
      case None =>
        println("Country not found. Try exact name/code.")
    }
    mainMenu(repo)
  }

  private def reportsMenu(repo: DataRepository): Unit = {
    println("\n=== Reports ===")
    println("1. Top 10 countries by airports")
    println("2. Bottom 10 countries by airports")
    println("3. Runway surfaces by country")
    println("4. Top 10 runway idents")
    println("5. Back")
    print("Choose an option: ")

    StdIn.readLine() match {
      case "1" =>
        println("\nTop 10 countries (most airports):")
        repo.topCountriesByAirportCount(10, ascending = false).foreach {
          case (country, count) => println(s"${country.name}: $count")
        }
      case "2" =>
        println("\nBottom 10 countries (least airports):")
        repo.topCountriesByAirportCount(10, ascending = true).foreach {
          case (country, count) => println(s"${country.name}: $count")
        }
      case "3" =>
        println("\nRunway surfaces by country:")
        repo.runwaySurfacesByCountry.foreach {
          case (country, surfaces) => 
            println(s"${country.name}: ${surfaces.mkString(", ")}")
        }
      case "4" =>
        println("\nMost common runway idents:")
        repo.topRunwayIdents(10).foreach {
          case (ident, count) => println(s"$ident ($count occurrences)")
        }
      case "5" => // Return
      case _   => println("Invalid option")
    }
    mainMenu(repo)
  }
}