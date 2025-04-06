package model

final case class Airport(id: String, name: String, countryCode: String)

object Airport {
  def fromCsv(line: String): Option[Airport] = {
    line.split(",", -1).toList match {
      case id :: ident :: tpe :: name :: lat :: lon :: elev :: cont :: isoCountry :: _ =>
        Some(Airport(id.trim, name.trim, isoCountry.trim.toUpperCase))
      case _ => None
    }
  }
}
