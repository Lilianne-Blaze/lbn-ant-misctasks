package lbnet.ant.misctasks;

public class WhatsAvailableTask extends MavenTaskBase {

    @Override
    public void execute() {

        if (getMavenProjectOpt().isPresent()) {
            log("Maven Project is available. We're running inside Maven AntRun.");
        } else {
            log("Maven Project is not available. Are we running outside of Maven AntRun?");
        }

        if (getMavenSessionOpt().isPresent()) {
            log("Maven Session is available. Note it requires customized AntRun.");
        } else {
            log("Maven Session is not available. This is normal, it requires customized AntRun.");
        }

    }

}
