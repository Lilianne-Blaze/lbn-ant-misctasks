package lbnet.ant.misctasks;

import java.util.Optional;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugins.antrun.MavenAntRunProject;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.Project;

public class MavenTaskBase extends TaskBase {

    /**
     * Alias for getProject to make it immediately clear which one is it when using both. This never returns null unless
     * for some reason the task is used outside Ant.
     */
    public Project getAntProject() {
        return getProject();
    }

    public MavenProject getMavenProject() {
        MavenAntRunProject marp = getProject().getReference("maven.project.ref");
        MavenProject mp = marp.getMavenProject();
        return mp;
    }

    public Optional<MavenProject> getMavenProjectOpt() {
        try {
            return Optional.of(getMavenProject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public MavenSession getMavenSession() {
        Object ref = getProject().getReference("maven.session.ref");
        try {
            MavenSession session = (MavenSession) MethodUtils.invokeMethod(ref, "getMavenSession");
            return session;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Optional<MavenSession> getMavenSessionOpt() {
        try {
            return Optional.of(getMavenSession());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
