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
      SpeakerEnriched(d, c, n, normalizedName, twitterUrls.get(normalizedName))
  }

  // hardcoded stuff FTW 😅
  val twitterUrls: Map[String, String] = Map(
    "adam sandor" -> "https://twitter.com/adamsand0r",
    "alessandro vozza" -> "https://twitter.com/bongo",
    "wiem zine elabidine" -> "https://twitter.com/WiemZin",
    "nicolas rinaudo" -> "https://twitter.com/NicolasRinaudo",
    "pierangelo cecchetto" -> "https://twitter.com/pierangelocecc"
  )

}
