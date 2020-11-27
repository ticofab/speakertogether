package io.ticofab.speakertogether
import java.time.LocalDate

import kantan.csv._
import kantan.csv.java8._
import kantan.csv.ops._

object CsvUtil {
  def parseSpeakerCsv(csvFileName: String): List[Speaker] = {
    implicit val decoder: CellDecoder[LocalDate] = localDateDecoder(
      fmt"dd MMM yyyy"
    )

    implicit val speakerDecoder: RowDecoder[Speaker] = RowDecoder.ordered {
      (i: LocalDate, c: String, n: String) =>
        Speaker(i, c, n, StringUtils.normalize(n).toLowerCase)
    }

    getClass
      .getResource("/" + csvFileName)
      .readCsv[List, Speaker](rfc)
      .filter {
        case Left(err) =>
          println("error in parsing:" + err)
          false
        case Right(value) => true
      }
      .map(_.toOption.get)
  }
}
