package lbnet.ant.misctasks;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class WriteProjectPropertiesTask extends MavenTaskBase {

    @Getter
    @Setter
    private String filePath = "${project.build.directory}/project-properties.txt";

    @Getter
    @Setter
    private boolean maskPrivates = true;

    @Override
    public void execute() {
        var filePath2 = interpolate(filePath);

        var props = copyPropsToMap(getProject().getProperties());

        if (maskPrivates) {
            maskPrivates(props);
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath2, false))) {
            log("Writing properties to " + filePath2);
            for (var entry : props.entrySet()) {
                pw.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    private void maskPrivates(Map<String, String> map) {
        map.replaceAll(this::maskPrivate);
    }

    private String maskPrivate(String key, String value) {
        String keyLcase = key.toLowerCase();
        if (keyLcase.contains("pass") || keyLcase.contains("token") || keyLcase.contains("key")) {
            return "*****";
        }
        return value;
    }

}
