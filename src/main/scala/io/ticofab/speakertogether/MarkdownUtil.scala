package io.ticofab.speakertogether

object MarkdownUtil {
  def createMarkdownTable(speakersList: List[(String, Int, List[String])]): String = {
    val sb = new StringBuilder
    sb ++= "| Name | # of crossings | Events in common |\n"
    sb ++= "| :--- | :------------- | :--------------- |\n"
    speakersList.foreach { case (name, occ, conferences) => sb ++= "| " + name + " | " + occ + " |" + conferences.map(" " + _).mkString(",") + " |\n" }
    sb.toString()
  }
}
