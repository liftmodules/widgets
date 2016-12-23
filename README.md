Widgets Lift Module
==================

[![Build Status](https://travis-ci.org/liftmodules/widgets.svg?branch=master)](https://travis-ci.org/liftmodules/widgets)

Provides:

* autocomplete
* calendars
* flot -- working with the [Flot Javascript plotting library](http://code.google.com/p/flot/).
* gravatar
* logchanger -- change log levels dynamically at runtime.
* menu
* rssfeed
* sparklines
* tablesorter
* tree
* uploadprogress


To include this module in your Lift project, add the following to `build.sbt`:

    libraryDependencies += "net.liftmodules" %% "widgets_3.0" % "1.4.0"

Releases
========

| Lift Version | Scala Version | Module Version |
|--------------|---------------|----------------|
| 3.0.x        | 2.12, 2.11    | 1.4.0          |
| 2.6.x        | 2.10, 2.9     | 1.3            |
| 2.5.x        | 2.10, 2.9     | 1.3            |

Historic Snapshots
------------------

Lift 3.0.x for Scala 2.10:

    "net.liftmodules" %% "widgets_3.0" % "1.3-SNAPSHOT"


Widget documentation
====================

General documentation and starting points:

* [Lift wiki page for widgets](https://www.assembla.com/spaces/liftweb/wiki/Widgets).

* _Exploring Lift_, chapter 14: [Lift Widgets](http://exploring.liftweb.net/master/index-14.html)

* _Lift in Action_ gives short examples of autocomplete and gravatar widgets: [http://www.manning.com/perrett/](http://www.manning.com/perrett/).

**Note:** The module package changed from `net.liftweb.widgets` to `net.liftmodules.widgets` in May 2012.  Please consider this when referencing documentation written before that date.


Autocomplete
------------

* _Lift Cookbook_ on [Making Suggestions with Autocomplete](http://cookbook.liftweb.net/#Autocomplete).

* [Using Lift’s AutoComplete Widget](http://timperrett.com/2010/10/13/using-lifts-autocomplete-widget/), Timothy Perrett's blog, 13 October 2010.


Calendar
--------

* Exploring Lift, [chapter 14](http://exploring.liftweb.net/onepage/index.html#toc-Subsection-14.1.2).

Flot
----

* [Lift Wiki page](https://www.assembla.com/wiki/show/liftweb/flot).

Gravatar
--------

* Exploring Lift, [chapter 14](http://exploring.liftweb.net/onepage/index.html#toc-Subsection-14.1.4).

* _Lift in Action_ includes a short gravatar example: [http://www.manning.com/perrett/](http://www.manning.com/perrett/).

* The [Lift Extras module](https://github.com/eltimn/lift-extras) includes an alternative Gravatar implementation.


Log changer
-----------

* [Lift Wiki Page](https://www.assembla.com/wiki/show/liftweb/logchanger).


RSS Widget
----------

* Exploring Lift, [chapter 14](http://exploring.liftweb.net/onepage/index.html#toc-Subsection-14.1.3).


Sparklines
----------

* Exploring Lift, [chapter 14](http://exploring.liftweb.net/onepage/index.html#toc-Subsection-14.1.6).


Table Sorter
------------

* Exploring Lift, [chapter 14](http://exploring.liftweb.net/onepage/index.html#toc-Subsection-14.1.1).

TreeView
--------

* Exploring Lift, [chapter 14](http://exploring.liftweb.net/onepage/index.html#toc-Subsection-14.1.5).



Notes for module developers
===========================

Merge to master will trigger a Travis build and publish a SNAPSHOT (if the version is a -SNAPSHOT).




