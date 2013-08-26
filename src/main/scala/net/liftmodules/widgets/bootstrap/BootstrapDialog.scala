package net.liftmodules.widgets.bootstrap

import net.liftweb.common._
import net.liftweb.util.Helpers._
import scala.xml.NodeSeq
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsExp
import net.liftweb.http.js.JsObj
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.jquery.JqJE.JqId
import net.liftweb.http.js.JE.Str
import net.liftweb.http.js.JE.JsFunc
import net.liftweb.http.js.jquery.JqJE.Jq
import net.liftweb.http.js.JsObj
import net.liftweb.http.js.JE._
import scala.xml.Text
import net.liftweb.http.js.jquery.JqJE.JqRemove
import net.liftweb.http.SHtml._
import net.liftweb.http.js.JE
import net.liftweb.util.CssSel

/**
 * @author tuhlmann@agynamix.de
 * A wrapper to easily construct a Bootstrap Modal dialog.
 * Based on https://github.com/jgoday/liftweb-jquery-dialogs-sample
 */
trait BootstrapDialog extends JsCmd {

  def onFull[T](valueBox: Box[T])(selFunc: T => CssSel): CssSel = {
    (for (value <- valueBox) yield {
      selFunc(value)
    }) openOr "#notExistent" #> ""
  }

  lazy val _dialogId: String = "modal_%s".format(nextFuncName)
  def dialogId = _dialogId

  val defaultOptions: Map[String, JsExp] = Map(
    "backdrop" -> "static"
  )

  val onClose: Box[()=>JsCmd] = Empty
  val options: Seq[(String, JsExp)] = List()

  def allOptions = defaultOptions.filterNot(dopt => options.exists(o => dopt._1 == o._1)) ++ options.toMap

  def clsOption: String = option("class")

  def option(name: String): String = {
    val re = allOptions.get(name) match {
      case Some(JE.Str(s)) => s
      case _ => ""
    }
    //println("CLASS %s: %s".format(name, re))
    re
  }

  val body: NodeSeq        = NodeSeq.Empty

  protected def formContent = body

  def cssClass: String = "modal " + clsOption

  def toJsObj(map: Map[String, JsExp]): JsObj = {
    map.foldLeft(JsObj())((jsobj, value) => jsobj +* JsObj(value))
  }

  def buildOnClose(): String = onClose.map(f => ajaxInvoke(f)._2.toJsCmd) openOr ""

  def open: JsCmd = open(formContent)
  def open(content: NodeSeq): JsCmd = {
    val htmlAndJs = HtmlJsSeparator(content)
    val js: JsCmd = (Jq("<div id='%s' class='%s' tabindex='-1' style='display:none'></div>".format(dialogId, cssClass)) ~>
      JsFunc("appendTo", "body") ~> htmlAndJs._1) &
      (JqId(dialogId) ~> JsFunc("modal", toJsObj(allOptions))) & htmlAndJs._2 &
      Run("$('#%s').on('hidden', function () { $('#%s').remove(); %s })".format(dialogId, dialogId, buildOnClose))
    //println("JS: "+js.toJsCmd)
    js
  }
  lazy val toJsCmd = open.toJsCmd

  def close: JsCmd = JqId(Str(dialogId)) ~> JsFunc("modal", "hide") ~> JqRemove()
}

trait StructuredBootstrapDialog extends BootstrapDialog {

  val header: Box[NodeSeq] = Full(<h3>Dialog</h3>)
  val footer: Box[NodeSeq] = Empty

  //val removeDlgCmd = (JqId(Str(dialogId)) ~> JsFunc("modal", "hide") ~> JqRemove()).toJsCmd
  val closeXTpl = <button name="close-x" type="button" class="close" aria-hidden="true" data-dismiss="modal">&times;</button>

  protected def closeX = {
    val cssSel = onFull(onCloseFunc){f => "@close-x [onclick]" #> ajaxInvoke(()=>f())}
    cssSel(closeXTpl)
  }

  protected def onCloseFunc(): Box[()=>JsCmd] = Empty

}

trait Bootstrap2DlgTpl extends BootstrapDialog with StructuredBootstrapDialog {

  override protected def formContent = {
    <xml:group>
      {header match {
        case Full(headSeq) =>
          <div class="modal-header">
            {closeX}
            {headSeq}
          </div>
        case _ => NodeSeq.Empty
      }}
      <div class="modal-body">
        {body}
      </div>
      {footer match {
        case Full(footSeq) =>
          <div class="modal-footer">
            {footSeq}
          </div>
        case _ => NodeSeq.Empty
      }}
    </xml:group>
  }

}

trait Bootstrap3DlgTpl extends BootstrapDialog with StructuredBootstrapDialog {

  override protected def formContent = {
    <div class="modal-dialog">
      <div class="modal-content">
        {header match {
          case Full(headSeq) =>
            <div class="modal-header">
              {closeX}
              {headSeq}
            </div>
          case _ => NodeSeq.Empty
        }}
        <div class="modal-body">
          {body}
        </div>
        {footer match {
          case Full(footSeq) =>
            <div class="modal-footer">
              {footSeq}
            </div>
          case _ => NodeSeq.Empty
        }}
      </div>
    </div>
  }

}

trait DialogHelpers extends BootstrapDialog {

  def genFooter(okTitle: String, okFunc: Box[()=>JsCmd], cancelTitle: String, cancelFunc: Box[()=>JsCmd]): NodeSeq = {
    val okClass = "btn " + option("okClass")
    val cancelClass = "btn " + option("cancelClass")
    val footerTpl =
      <xml:group>
        <button name="cancel" class={cancelClass} data-dismiss="modal" aria-hidden="true" >{cancelTitle}</button>
        <button name="ok" class={okClass} data-dismiss="modal" >{okTitle}</button>
      </xml:group>

    val cssSel =
      onFull(cancelFunc){f => "@cancel [onclick]" #> ajaxInvoke(()=>f())} &
      onFull(okFunc){f => "@ok [onclick]" #> ajaxInvoke(()=>f())}

    cssSel(footerTpl)
  }

}

/**
 * Simplest and most flexible version to construct a dialog:
 * Modal(NodeSeq Template, options to pass).
 * For available options (applies to all other invocation variants, too), see Bootstrap's Modal options.
 * In addition, these keys are supported:
 * - class: classes to add to the top level dialog
 * - okClass: classes to add to the ok button, the button which invokes the followup logic if the user accepts the dialog.
 * - cancelClass: classes to add to the Cancel button
 *
 */
object Modal {
  def apply(body: NodeSeq, options: (String, JsExp)*) = new Modal(body, Empty, options: _*)
}

object Modal2 {
  def apply(body: NodeSeq, onClose: ()=>JsCmd, options: (String, JsExp)*) = new Modal(body, Full(onClose), options: _*)
}

class Modal(override val body: NodeSeq, override val onClose: Box[()=>JsCmd], override val options: (String, JsExp)*) extends BootstrapDialog

class InfoDialog(title: String, override val body: NodeSeq) extends StructuredBootstrapDialog with Bootstrap2DlgTpl {
  override val header = Full(<h3>{title}</h3>)
  override val footer = Full(<button class="btn" aria-hidden="true" data-dismiss="modal">Close</button>)
}

class Bs3InfoDialog(title: String, override val body: NodeSeq) extends StructuredBootstrapDialog with Bootstrap3DlgTpl {
  override val header = Full(<h3>{title}</h3>)
  override val footer = Full(<button class="btn" aria-hidden="true" data-dismiss="modal">Close</button>)
}

object ConfirmDialog {
  def apply(title: String, body: NodeSeq, okFunc: ()=>JsCmd, options: (String, JsExp)*) =
    new ConfirmDialog(Full(<h3>{title}</h3>), body, "Ok", Full(okFunc), "Cancel", Empty, options: _*)

  def apply(title: String, body: NodeSeq, okFunc: ()=>JsCmd, cancelFunc: ()=>JsCmd, options: (String, JsExp)*) =
    new ConfirmDialog(Full(<h3>{title}</h3>), body, "Ok", Full(okFunc), "Cancel", Full(cancelFunc), options: _*)
}

class ConfirmDialog(override val header: Box[NodeSeq], override val body: NodeSeq, okTitle: String, okFunc: Box[()=>JsCmd], cancelTitle: String, cancelFunc: Box[()=>JsCmd], override val options: (String, JsExp)*)
      extends StructuredBootstrapDialog with Bootstrap2DlgTpl with DialogHelpers {
  override val footer = Full(genFooter(okTitle, okFunc, cancelTitle, cancelFunc))
  override protected def onCloseFunc(): Box[()=>JsCmd] = cancelFunc
}

object Bs3ConfirmDialog {
  def apply(title: String, body: NodeSeq, okFunc: ()=>JsCmd, options: (String, JsExp)*) =
    new Bs3ConfirmDialog(Full(<h3>{title}</h3>), body, "Ok", Full(okFunc), "Cancel", Empty, options: _*)

  def apply(title: String, body: NodeSeq, okFunc: ()=>JsCmd, cancelFunc: ()=>JsCmd, options: (String, JsExp)*) =
    new Bs3ConfirmDialog(Full(<h3>{title}</h3>), body, "Ok", Full(okFunc), "Cancel", Full(cancelFunc), options: _*)
}

class Bs3ConfirmDialog(override val header: Box[NodeSeq], override val body: NodeSeq, okTitle: String, okFunc: Box[()=>JsCmd], cancelTitle: String, cancelFunc: Box[()=>JsCmd], override val options: (String, JsExp)*)
      extends StructuredBootstrapDialog with Bootstrap3DlgTpl with DialogHelpers {
  override val footer = Full(genFooter(okTitle, okFunc, cancelTitle, cancelFunc))
  override protected def onCloseFunc(): Box[()=>JsCmd] = cancelFunc
}

object ConfirmRemoveDialog {
  def apply(title: String, msg: String, removeFunc: ()=>JsCmd, options: (String, JsExp)*) = {
    val options2: Seq[(String, JsExp)] = List(("okClass" -> "btn-danger"))
    new ConfirmDialog(Full(<h3>{title}</h3>), NodeSeq.Empty, "Remove", Full(removeFunc), "Cancel", Empty, (options2 ++ options): _*) {
      override val body = <p class="lead">{msg}</p>
    }
  }
}

object Bs3ConfirmRemoveDialog {
  def apply(title: String, msg: String, removeFunc: ()=>JsCmd, options: (String, JsExp)*) = {
    val options2: Seq[(String, JsExp)] = List(("okClass" -> "btn-danger"))
    new Bs3ConfirmDialog(Full(<h3>{title}</h3>), NodeSeq.Empty, "Remove", Full(removeFunc), "Cancel", Empty, (options2 ++ options): _*) {
      override val body = <p class="lead">{msg}</p>
    }
  }
}

