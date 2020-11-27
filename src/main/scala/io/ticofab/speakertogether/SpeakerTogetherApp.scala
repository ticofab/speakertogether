package io.ticofab.speakertogether

object SpeakerTogetherApp extends App {

  val speakers = CsvUtil.parseSpeakerCsv("allspeakersallevents.csv")
  val groupedByConf = speakers.groupBy(_.conference)
  val allConferences = groupedByConf.keys.toSet

  val groupedBySpeaker = speakers.groupBy(_.normalizedName)
  val atLeastTwoOccurrences = groupedBySpeaker.filter {
    case (_, occurrences) => occurrences.size > 1
  }
  val myName = "Fabio Tiriticco"

  val forAllConf = allConferences.map { conference =>
    {
      val speakersAtThisConference =
        groupedByConf(conference).toSet.map[String](speaker =>
          speaker.normalizedName
        )
      val timesISpokeWithOtherSpeakerAtThisConference = atLeastTwoOccurrences
        .filter {
          case (normalizedName, _) =>
            speakersAtThisConference.contains(normalizedName)
        }
        .view
        .mapValues(list => (list.head.name, list.size))
        .toMap
        .toList
        .map { case (_, tuple) => tuple }
        .filter { case (name, _) => name != myName }
        .sortBy { case (_, occ) => occ }
        .reverse
      (conference, timesISpokeWithOtherSpeakerAtThisConference)
    }
  }

  val asAMap = forAllConf.filter { case (_, list) => list.size > 1 }.toList

  println(
    "read " + speakers.size + " speakers from " + groupedByConf.keys.size + " conferences"
  )

}
