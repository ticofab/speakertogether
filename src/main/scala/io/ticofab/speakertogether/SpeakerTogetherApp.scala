package io.ticofab.speakertogether

import io.ticofab.speakertogether.CsvUtil.parseSpeakerCsv
import io.ticofab.speakertogether.MarkdownUtil.createMarkdownTable

object SpeakerTogetherApp extends App {

  val speakers = parseSpeakerCsv("allspeakersallevents.csv")
    .map(SpeakerEnriched.fromSpeakerCsvEntry)
  val groupedByConf = speakers.groupBy(_.conference)
  val allConferences = groupedByConf.keys.toSet

  val groupedBySpeaker = speakers.groupBy(_.normalizedName)
  val atLeastTwoOccurrences = groupedBySpeaker.filter { case (_, occ) => occ.size > 1 }
  val myName = "Fabio Tiriticco"
  val latestEvent = "DevNexus 2021"

  var speakersByAmountOfCrossingsWithMe = groupedBySpeaker.toList
    .sortBy { case (_, occ) => occ.size }
    .reverse
    .map {
      case (_, list) =>
        val head = list.head // don't do for serious things
        val name = head.maybeTwUrl.fold(head.name)(twUrl => "[" + head.name + "](" + twUrl + ")")
        (name, list.size, list.map(_.conference))
    }
    .filter { case (name, _, _) => name != myName }

  // used to generate the markdown that I manually copy/paste onto the readme file
  val markdownAll = createMarkdownTable(speakersByAmountOfCrossingsWithMe.filter {
    case (_, i, _) => i > 1
  })

  // same as above but only for the latest event I spoke at
  val markdownLatest = createMarkdownTable(speakersByAmountOfCrossingsWithMe.filter {
    case (_, i, events) => i > 1 && events.contains(latestEvent)
  })

  println("read " + speakers.size + " speakers from " + groupedByConf.keys.size + " conferences")

}
