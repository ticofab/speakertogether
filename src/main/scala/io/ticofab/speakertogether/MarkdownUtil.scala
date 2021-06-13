package io.ticofab.speakertogether

object MarkdownUtil {
  def createMarkdownTable(title: String, speakersList: List[(String, Int, List[String])]): String = {
    val sb = new StringBuilder
    sb ++= "## " + title + "\n"
    sb ++= "\n"
    sb ++= "| Name | Events in common | Events |\n"
    sb ++= "| :--- | :--------------- | :----- |\n"
    speakersList.foreach { case (name, occ, conferences) => sb ++= "| " + name + " | " + occ + " |" + conferences.map(" " + _).mkString(",") + " |\n" }
    sb.toString()
  }
}
