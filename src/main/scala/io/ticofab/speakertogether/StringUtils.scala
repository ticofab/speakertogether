package io.ticofab.speakertogether

object StringUtils {

  import java.text.Normalizer

  /**
    * Replaces accented characters from the given string to the non-accented version.
    * Removes them if no alternative is found.
    * @param input The string to normalize
    * @return The normalized string.
    */
  def normalize(input: String): String =
    Normalizer
      .normalize(input, Normalizer.Form.NFD)
      .replaceAll("[^\\p{ASCII}]", "")
}
