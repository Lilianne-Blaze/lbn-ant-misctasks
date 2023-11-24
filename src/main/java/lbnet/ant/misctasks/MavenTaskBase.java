package lbnet.ant.misctasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
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

    public String getProperty(String propName) {
        var mpo = getMavenProjectOpt();
        if (mpo.isPresent()) {
            String pv = mpo.get().getProperties().getProperty(propName);
            if (pv != null) {
                return pv;
            }
        }

        return System.getProperty(propName);
    }

    public void setProperty(String propName, String propVal, boolean setSysProps) {
        var mpo = getMavenProjectOpt();
        if (mpo.isPresent()) {
            mpo.get().getProperties().setProperty(propName, propVal);
        }

        if (setSysProps) {
            System.setProperty(propVal, propName);
        }
    }

    public String interpolate(String text) {
        Map<String, StringLookup> slm = new HashMap<>();
        slm.put(StringLookupFactory.KEY_PROPERTIES, StringLookupFactory.INSTANCE.environmentVariableStringLookup());
        slm.put(StringLookupFactory.KEY_SYS, StringLookupFactory.INSTANCE.propertiesStringLookup());
        StringLookup sl = StringLookupFactory.INSTANCE.functionStringLookup(this::getProperty);

        StringSubstitutor ss = new StringSubstitutor(
                StringLookupFactory.INSTANCE.interpolatorStringLookup(slm, sl, false));

        return ss.replace(text);

    }

}
