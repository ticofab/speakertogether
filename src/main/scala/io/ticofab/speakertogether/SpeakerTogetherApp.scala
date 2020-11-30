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

  var speakersByAmountOfCrossingsWithMe = groupedBySpeaker
    .toList
    .sortBy { case (_, occ) => occ.size }
    .reverse
    .map { case (_, list) =>
      val head = list.head // don't do for serious things
      val name = head.maybeTwUrl.fold(head.name)(twUrl => "[" + head.name + "](" + twUrl+ ")")
      (name, list.size)
    }
    .filter { case (name, _) => name != myName}

  // used to generate the markdown that I manually copy/paste onto the readme file
  val markdown = createMarkdownTable(
    speakersByAmountOfCrossingsWithMe.filter{ case (_, i) => i > 1 } )

  val forAllConf = allConferences
    .map { conference =>
    {
      val speakersAtThisConference = groupedByConf(conference).toSet.map[String](_.normalizedName)
      val timesISpokeWithOtherSpeakerAtThisConference = atLeastTwoOccurrences
        .filter {
          case (normalizedName, _) =>
            speakersAtThisConference.contains(normalizedName)
        }
        .view

        // map into a tuple with speaker name and times we spoke together
        .mapValues(list => (list.head.name, list.size))
        .toMap
        .toList
        .map { case (_, tuple) => tuple }

        // remove myself from this list
        .filter { case (name, _) => name != myName }

        // sort descending
        .sortBy { case (_, occ) => occ }
        .reverse

      (conference, timesISpokeWithOtherSpeakerAtThisConference)
    }
  }.filter { case (_, list) => list.size > 1 }
    .toMap

  // used to print out a file for a specific conference
  // printOutConferenceCsv("jlove2020", forAllConf("JLove 2020"))

  println("read " + speakers.size + " speakers from " + groupedByConf.keys.size + " conferences")

}
