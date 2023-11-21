package lbnet.ant.misctasks;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class TaskBase extends Task {

    public void debug(String s) {
        log(s, Project.MSG_DEBUG);
    }

    public void warn(String s) {
        log(s, Project.MSG_WARN);
    }

    public Map<String, String> copyPropsToMap(Hashtable<?, ?> props) {
        var m = new TreeMap<String, String>();
        props.forEach((t, u) -> {
            m.put(t.toString(), u.toString());
        });
        return m;
    }

}
