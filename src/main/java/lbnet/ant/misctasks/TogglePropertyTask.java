package lbnet.ant.misctasks;

import lombok.Getter;
import lombok.Setter;

public class TogglePropertyTask extends MavenTaskBase {

    @Getter
    @Setter
    private String name = null;

    @Getter
    @Setter
    private String newName = null;

    @Override
    public void execute() {

        String value = getProperty(name);
        boolean newValue = false;
        boolean valueChanged = false;

        // warn("name=" + name);
        // warn("value=" + value);

        if (value == null || value.equals("") || value.equalsIgnoreCase("false")) {
            newValue = true;
            valueChanged = true;
        } else if (value != null || value.equalsIgnoreCase("true")) {
            newValue = false;
            valueChanged = true;
        }

        if (valueChanged) {
            String newValueStr = Boolean.toString(newValue);
            if (newName == null) {
                setProperty(name, newValueStr, true);
            } else {
                setProperty(newName, newValueStr, true);
            }
        }
    }

}
