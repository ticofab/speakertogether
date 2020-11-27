package io.ticofab.speakertogether

object StringUtils {

  import java.text.Normalizer

  def normalize(input: String): String =
    Normalizer
      .normalize(input, Normalizer.Form.NFD)
      .replaceAll("[^\\p{ASCII}]", "")
}
