package model

final case class Country(code: String, name: String)

object Country {
  def fromCsv(line: String): Option[Country] = {
    line.split(",", -1).toList match {
      case id :: code :: name :: _ =>
        Some(Country(code.trim.toUpperCase, name.trim))
      case _ => None
    }
  }
}
