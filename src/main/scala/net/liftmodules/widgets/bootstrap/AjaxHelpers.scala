package net.liftmodules.widgets.bootstrap

import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JsMember
import net.liftweb.http.js.HtmlFixer
import scala.xml.NodeSeq

object HtmlJsSeparator extends HtmlFixer {
  case class Html(val html: String) extends JsCmd with JsMember {
    val toJsCmd = "html(" + html + ")"
  }
  case class JS(val js: JsCmd) extends JsCmd with JsMember {
    val toJsCmd = js.toJsCmd
  }
  def apply(content: NodeSeq): (Html, JS) =
    fixHtmlAndJs("inline", content) match {
      case (str, Nil) => (Html(str), JS(Noop))
      case (str, cmds) => (Html(str), JS(cmds.reduceLeft(_ & _)))
    }
}
