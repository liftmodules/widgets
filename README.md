Widgets Lift Module
==================

Provides:

* autocomplete
* calendars
* [flot](https://www.assembla.com/wiki/show/liftweb/flot) -- working with the [Flot Javascript plotting library](http://code.google.com/p/flot/).
* gravatar
* [logchanger](https://www.assembla.com/wiki/show/liftweb/logchanger) -- change log levels dynamically at runtime.
* menu
* rssfeed
* sparklines
* tablesorter
* tree
* uploadprogress


To include this module in your Lift project, update your `libraryDependencies` in `build.sbt` to include:

*Lift 2.5.x* for Scala 2.9 and 2.10:

    "net.liftmodules" %% "widgets_2.5" % "1.3"

*Lift 3.0.x* for Scala 2.10:

    "net.liftmodules" %% "widgets_3.0" % "1.3-SNAPSHOT"


Widget documentation
====================

* _Exploring Lift_, chapter 14: [Lift Widgets](http://exploring.liftweb.net/master/index-14.html)

* [Lift wiki page for widgets](https://www.assembla.com/spaces/liftweb/wiki/Widgets).

**Note:** The module package changed from `net.liftweb.widgets` to `net.liftmodules.widgets` in May 2012.  Please consider this when referencing documentation written before that date.




Notes for module developers
===========================

* The [Jenkins build](https://liftmodules.ci.cloudbees.com/job/widgets/) is triggered on a push to master.




