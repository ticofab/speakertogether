package io.ticofab.speakertogether
import java.io.PrintWriter
import java.time.LocalDate

import kantan.csv._
import kantan.csv.java8._
import kantan.csv.ops._

object CsvUtil {

  /**
    * Parses a CSV file from resources where each line is like
    *    dd MM yyyy,conference,speaker
    * @param csvFileName the name of the resource file
    * @return a list of all entries
    */
  def parseSpeakerCsv(csvFileName: String): List[SpeakerCsvEntry] = {
    implicit val decoder: CellDecoder[LocalDate] = localDateDecoder(
      fmt"dd MMM yyyy"
    )

    implicit val speakerDecoder: RowDecoder[SpeakerCsvEntry] = RowDecoder.ordered {
      (i: LocalDate, c: String, n: String) => SpeakerCsvEntry(i, c, n)
    }

    getClass
      .getResource("/" + csvFileName)
      .readCsv[List, SpeakerCsvEntry](rfc)
      .filter {
        case Left(err) =>
          println("error in parsing:" + err)
          false
        case Right(_) => true
      }
      .map(_.toOption.get)
  }

  /**
   * Writes out a CSV file named after the conference
   * @param conference The name of the conference
   * @param data A list of speakers and the amount of time I spoke at the same conference with them before
   *             like
   *             List( (John Doe, 4), (Mario Rossi, 5) )
   */
  def printOutConferenceCsv(conference: String, data: List[(String, Int)]): Unit = {
    val csvStr = data.asCsv(rfc)
    val writer = new PrintWriter(conference + ".csv")
    writer.print(csvStr)
    writer.close()
  }
}
