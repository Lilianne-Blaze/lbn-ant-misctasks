package lbnet.ant.misctasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import lombok.Getter;
import lombok.Setter;

public class PrepareEmptyAgentFilesTask extends MavenTaskBase {

    @Getter
    @Setter
    private String preset = "preset2";

    @Override
    public void execute() {
        log("Preparing empty Graal Nativeimage Agent files.");

        try {
            if (preset.equals("preset1")) {
                prepareFiles(interpolate("${project.build.directory}/native/agent-output/main"));
                prepareFiles(interpolate("${project.build.directory}/native/agent-output/test"));
                prepareFiles(interpolate(
                        "${project.build.sourceDirectory}/../resources/META-INF/native-image/${project.groupId}/${project.artifactId}"));
            } else if (preset.equals("preset2")) {
                prepareFiles(interpolate("${project.build.directory}/native/agent-output/main"));
                prepareFiles(interpolate("${project.build.directory}/native/agent-output/test"));
                prepareFiles(interpolate(
                        "${project.build.sourceDirectory}/../resources/META-INF/native-image/${project.groupId}/${project.artifactId}/generated"));
            }
        } catch (IOException e) {
            warn("Exception preparing Agent files: " + e.toString());
            throw new RuntimeException(e);
        }
    }

    private void prepareFiles(String dirStr) throws IOException {
        log("Preparing: " + dirStr);
        Path dir = Path.of(dirStr);

        writeStringNoOverwrite(dir.resolve("jni-config.json"), EMPTY_ARRAY_JSON);
        writeStringNoOverwrite(dir.resolve("predefined-classes-config.json"), PREDEFINED_CLASSES_JSON);
        writeStringNoOverwrite(dir.resolve("proxy-config.json"), EMPTY_ARRAY_JSON);
        writeStringNoOverwrite(dir.resolve("reflect-config.json"), EMPTY_ARRAY_JSON);
        writeStringNoOverwrite(dir.resolve("resource-config.json"), RESOURCE_CONFIG_JSON);
        writeStringNoOverwrite(dir.resolve("serialization-config.json"), SERIALIZARION_CONFIG_JSON);
    }

    private void writeStringNoOverwrite(Path filePath, String text) throws IOException {
        Path parentPath = filePath.getParent();
        Files.createDirectories(parentPath);
        if (!Files.exists(filePath)) {
            Files.writeString(filePath, text, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    private final String EMPTY_ARRAY_JSON = """
            []
            """;

    private final String RESOURCE_CONFIG_JSON = """
            {
              "resources":{
              "includes":[]},
              "bundles":[]
            }
            """;

    private final String SERIALIZARION_CONFIG_JSON = """
            {
              "types":[
              ],
              "lambdaCapturingTypes":[
              ],
              "proxies":[
              ]
            }
            """;

    private final String PREDEFINED_CLASSES_JSON = """
            [
              {
                "type":"agent-extracted",
                "classes":[
                ]
              }
            ]
            """;

}
