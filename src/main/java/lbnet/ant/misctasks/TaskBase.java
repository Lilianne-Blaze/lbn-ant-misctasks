package lbnet.ant.misctasks;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class TaskBase extends Task {

    public void debug(String s) {
        log(s, Project.MSG_DEBUG);
    }

    public void warn(String s) {
        log(s, Project.MSG_WARN);
    }

}
