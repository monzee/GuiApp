GuiApp
======
This is me trying to write an android app without android because the machine I
was using for android dev is unavailable.

Half kidding, this is an exercise in implementing a GUI using MVP with the goal
of making the screens as portable as possible. In this project, I chose to
implement the UI with a text UI framework (Lanterna). It's a pretty cool
framework, reminds me of the TurboVision times before windows 3.1.

Also using Dagger2 for dependency resolution and Retrofit2 for server
communication. I'm trying to find a sensible way to structure the dagger
components. It currently works but I'm not sure yet if I like them being this
split apart.

I intend to port this to android later on after I have done a few more screens
(still figuring out how to launch a new window). The dream is to copy-paste
everything outside of the TUI components (including the view implementations)
then write the activities implementing the view interfaces and wiring them up
to the same exact models and presenters I wrote here.

Building
--------
Run `mvn install` under the project directory, then run
`java -jar target/GuiApp-0.1-SNAPSHOT-shaded.jar`.
