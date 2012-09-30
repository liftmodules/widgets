/*
 * Copyright 2007-2010 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package webapptest {
package snippet {

import _root_.scala.xml.NodeSeq
import _root_.net.liftmodules.widgets.gravatar.Gravatar
import net.liftmodules.widgets.bootstrap.Modal
import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import net.liftmodules.widgets.bootstrap.ConfirmRemoveDialog
import net.liftweb.http.js.JsCmds.Alert

/**
 * @author tuhlmann@agynamix.de
 * The following shows was to create a Bootstrap Modal Dialog. Please note that the Bootstrap CSS and the Modal
 * JS file (part of Bootstrap) must be present to make this work.
 * For other available dialog wrappers look at BootstrapDialog.scala
 */
class BootstrapDialogDemo {

  def render = {
    "#modal_dialog [onclick]" #> SHtml.ajaxInvoke(()=>Modal(<div>Some Template Here</div>, "keyboard" -> false, "class" -> "max"))

    "#modal_remove [onclick]" #> SHtml.ajaxInvoke(()=>{
      ConfirmRemoveDialog("Delete Item", "Really delete this item?",
        ()=>{ Alert("Item deleted") })
      })

  }
}

}
}







