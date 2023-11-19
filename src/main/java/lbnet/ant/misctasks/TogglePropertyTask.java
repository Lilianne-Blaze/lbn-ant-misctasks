package lbnet.ant.misctasks;

import lombok.Getter;
import lombok.Setter;

public class TogglePropertyTask extends MavenTaskBase {

    @Getter
    @Setter
    private String name = null;

    @Getter
    @Setter
    private String newPropName = null;

    @Override
    public void execute() {

        String value = getProperty(name);
        boolean newValue = false;
        boolean valueChanged = false;

        warn("name=" + name);
        warn("value=" + value);

        if (value == null || value.equals("") || value.equalsIgnoreCase("false")) {
            newValue = true;
            valueChanged = true;
        } else if (value != null || value.equalsIgnoreCase("true")) {
            newValue = false;
            valueChanged = true;
        }

        if (valueChanged) {
            if (newPropName == null) {
                setProperty(name, newValue);
            } else {
                setProperty(newPropName, newValue);
            }
        }
    }

    private String getProperty(String propName) {
        return getMavenProject().getProperties().getProperty(propName);
    }

    private void setProperty(String propName, boolean newValue) {
        getMavenProject().getProperties().setProperty(propName, "" + newValue);
    }

}
