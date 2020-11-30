package io.ticofab.speakertogether

object MarkdownUtil {
  def createMarkdownTable(speakersList: List[(String, Int)]): String = {
    val sb = new StringBuilder
    sb ++= "| Name | Events in common |\n"
    sb ++= "| :--- | :--------------- |\n"
    speakersList.foreach { case (name, occ) => sb ++= "| " + name + " | " + occ + " |\n" }
    sb.toString()
  }
}
