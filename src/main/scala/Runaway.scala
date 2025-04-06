package model

final case class Runway(id: String, airportRef: String, surface: String, leIdent: String)

object Runway {
  def fromCsv(line: String): Option[Runway] = {
    line.split(",", -1).toList match {
      case id :: airportRef :: _ :: _ :: _ :: surface :: _ :: _ :: leIdent :: _ =>
        Some(
          Runway(
            id.trim,
            airportRef.trim,
            surface.replace("\"", "").trim,
            leIdent.replace("\"", "").trim
          )
        )
      case _ => None
    }
  }
}

