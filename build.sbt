name := "widgets"

liftVersion <<= liftVersion ?? "2.4"

version <<= liftVersion apply { _ + "-1.0-SNAPSHOT" }

organization := "net.liftmodules"
 
scalaVersion := "2.9.1"
 
crossScalaVersions := Seq("2.8.1", "2.9.0-1", "2.9.1")

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

libraryDependencies <++= liftVersion { v =>
  "net.liftweb" %% "lift-webkit" % v % "compile->default" ::
  Nil
}    


libraryDependencies <++= scalaVersion { sv => 
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "provided" ::
  "log4j" % "log4j" % "1.2.16" % "provided" ::
  "org.scala-tools.testing" %% "specs" %  (sv match { 
      case "2.8.0" => "1.6.5"
      case "2.9.1" => "1.6.9"
      case _ => "1.6.8"
      }) % "test" :: 
  "org.scalacheck" %% "scalacheck" % (sv match { 
      case "2.8.0" => "1.7"
      case "2.8.1" | "2.8.2" => "1.8"
      case _ => "1.9"
      }) % "test" ::
  Nil
}

publishTo <<= version { _.endsWith("SNAPSHOT") match {
 	case true  => Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
 	case false => Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
  }
 } 


// For local deployment:

credentials += Credentials( file("sonatype.credentials") )

// For the build server:

credentials += Credentials( file("/private/liftmodules/sonatype.credentials") )

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }


pomExtra := (
	<url></url>
	<licenses>
		<license>
	      <name>Apache 2.0 License</name>
	      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
	      <distribution>repo</distribution>
	    </license>
	 </licenses>
	 <scm>
	    <url>git@github.com:liftmodules/widgets.git</url>
	    <connection>scm:git:git@github.com:liftmodules/widgets.git</connection>
	 </scm>
	 <developers>
	    <developer>
	      <id>liftmodules</id>
	      <name>Lift Team</name>
	      <url>http://www.liftmodules.net</url>
	 	</developer>
	 </developers> 
 )
  
