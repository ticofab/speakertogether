package io.ticofab.speakertogether

import java.time.LocalDate

case class SpeakerCsvEntry(date: LocalDate, conference: String, name: String)

case class SpeakerEnriched(
    date: LocalDate,
    conference: String,
    name: String,
    normalizedName: String,
    maybeTwUrl: Option[String]
)

object SpeakerEnriched {
  val fromSpeakerCsvEntry: SpeakerCsvEntry => SpeakerEnriched = {
    case SpeakerCsvEntry(d, c, n) =>
      val normalizedName = StringUtils.normalize(n).toLowerCase
      val maybeTwitterUrl = twitterHandles.get(normalizedName).map("https://twitter.com/" + _)
      SpeakerEnriched(d, c, n, normalizedName, maybeTwitterUrl)
  }

  // hardcoded stuff FTW 😅
  val twitterHandles: Map[String, String] = Map(
    "adam sandor" -> "adamsand0r",
    "alessandro vozza" -> "bongo",
    "wiem zine elabidine" -> "WiemZin",
    "nicolas rinaudo" -> "NicolasRinaudo",
    "pierangelo cecchetto" -> "pierangelocecc",
    "paulo lopes" -> "pml0pes",
    "jakub kozowski" -> "kubukoz",
    "mary grygleski" -> "mgrygles",
    "jon pretty" -> "propensive",
    "renato cavalcanti" -> "octonato"
  )

}
