package model

final case class Runway(id: String, airportRef: String, surface: String, leIdent: String)

object Runway {
  def fromCsv(line: String): Option[Runway] = {
    line.split(",", -1).toList match {
      case id :: airportRef :: airportIdent :: lenFt :: widthFt :: surface :: lighted :: closed :: leIdent :: _ =>
        Some(Runway(id.trim, airportRef.trim, surface.trim, leIdent.trim))
      case _ => None
    }
  }
}
