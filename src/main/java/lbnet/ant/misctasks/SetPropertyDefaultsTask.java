package lbnet.ant.misctasks;

import java.util.Properties;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class SetPropertyDefaultsTask extends MavenTaskBase {

    @Getter
    @Setter
    private String prefix = "defaults.";

    @Getter
    @Setter
    private String suffix = ".default";

    @Getter
    @Setter
    private boolean setSystem = false;

    @Override
    public void execute() {
        debug("prefix = '" + prefix + "'.");
        debug("suffix = '" + suffix + "'.");

        Properties props = getMavenProject().getProperties();

        processProps(props);
    }

    private void processProps(Properties props) {
        Set<String> keys = getMavenProject().getProperties().stringPropertyNames();
        for (String key : keys) {
            processProp(key, props);
        }
    }

    private void processProp(String key, Properties props) {
        debug("Processing property '" + key + "'.");
        String keyBase = null;
        if (suffix != null && suffix.length() > 0 && key.endsWith(suffix)) {
            keyBase = key.substring(0, key.length() - suffix.length());
        } else if (prefix != null && prefix.length() > 0 && key.startsWith(prefix)) {
            keyBase = key.substring(prefix.length());
        }

        if (keyBase != null) {
            debug("Property '" + key + "' found.");
            if (props.contains(keyBase)) {
                debug("Property '" + keyBase + "' is already set.");
            } else {
                String newValue = props.getProperty(key);
                debug("Property '" + keyBase + "' not found, setting to '" + newValue + "'.");
                props.setProperty(keyBase, newValue);
                if (setSystem) {
                    System.setProperty(keyBase, newValue);
                }
            }
        }

    }

}
