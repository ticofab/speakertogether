package io.ticofab.speakertogether

import java.time.LocalDate

case class Speaker(date: LocalDate, conference: String, name: String, normalizedName: String)
