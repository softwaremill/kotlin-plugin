TaskKey[Unit]("check-classes") := {
  val classes   = (Compile / classDirectory).value
  val classList = (classes ** "*.class").get
//  if (classList.size != 9) {
  if (classList.size != 8) { // XXX ignoring kts files?
    throw new MessageOnlyException(s"Incorrect number of classes: ${classList.size} =>\n${classList.mkString("\n")}")
  }
}
