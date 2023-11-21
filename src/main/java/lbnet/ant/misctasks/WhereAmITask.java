package lbnet.ant.misctasks;

public class WhereAmITask extends TaskBase {

    @Override
    public void execute() {

        log("REFS");
        getProject().getReferences().forEach((t, u) -> {
            log("ref: " + t);
        });

        log("PROPS");
        getProject().getProperties().forEach((t, u) -> {
            log("prop: " + t + "=" + u);
        });

        // getProject().

        // use of the reference to Project-instance
        String message = getProject().getProperty("ant.project.name");

        // Task's log method
        log("Here is project '" + message + "'.");

        // where this task is used?
        log("I am used in: " + getLocation());
    }

}
