package lbnet.ant.misctasks;

import org.apache.tools.ant.Task;

public class TestTask extends MavenTaskBase {

    public void execute() {

        // log("" + getMavenProjectOpt().toString());
        // log("" + getMavenSessionOpt().toString());

        // getMavenProject().getProperties().setProperty("projectProp1", "projectProp1
        // from task");
        // getMavenProject().getProperties().setProperty("projectProp2", "projectProp2
        // from task");

        // use of the reference to Project-instance
        // String message = getProject().getProperty("ant.project.name");

        // Task's log method
        // log("Here is project '" + message + "'.");

        // where this task is used?
        // log("I am used in: " + getLocation());
    }

}
