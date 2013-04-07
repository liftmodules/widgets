Widgets Lift Module
==================

Provides:

* autocomplete
* calendars
* flot -- working with the [Flot Javascript plotting library](http://code.google.com/p/flot/).
* gravatar
* logchanger
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


Widget details
==============

**Note:** The module package changed from `net.liftweb.widgets` to `net.liftmodules.widgets` in May 2012.  Please consider this when referencing documentation written before that date.


Flot
----

See the [Lift Wiki page on Flot](http://www.assembla.com/spaces/liftweb/wiki/Flot).


Notes for module developers
===========================

* The [Jenkins build](https://liftmodules.ci.cloudbees.com/job/widgets/) is triggered on a push to master.




