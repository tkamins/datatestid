package datatestid.generator;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

import datatestid.generator.uicontrols.Ahref;
import datatestid.generator.uicontrols.Button;
import datatestid.generator.uicontrols.Checkbox;
import datatestid.generator.uicontrols.DivShowPopover;
import datatestid.generator.uicontrols.LabelButton;
import datatestid.generator.uicontrols.Radio;
import datatestid.generator.uicontrols.Select;
import datatestid.generator.uicontrols.TextInput;
import datatestid.generator.uicontrols.Textarea;
import datatestid.generator.uicontrols.UISelect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public class Main {

    private static final Set<UIControl> uiControls = Set.of(
            new TextInput(),
            new Checkbox(),
            new Button(),
            new UISelect(),
            new Select(),
            new Radio(),
            new DivShowPopover(),
            new LabelButton(),
            new Ahref(),
            new Textarea()
    );

    private static final Document.OutputSettings outputSettings = new Document.OutputSettings();
    static {
        outputSettings.prettyPrint(false);
        outputSettings.syntax(Document.OutputSettings.Syntax.xml);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("A path to a root directory must be provided as the first program argument!");
            return;
        }

        final String rootDirectory = args[0];

        System.out.println("Processing files:");
        final int[] counter = {0,0};

        Files.walkFileTree(Paths.get(rootDirectory), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(final Path filePath, final BasicFileAttributes attrs) throws IOException {

                if (isHtmlFile(filePath)) {
                    System.out.println(filePath);
                    counter[0]++;

                    final Document document = readFile(filePath);

                    boolean isDocumentModified = uiControls.stream()
                            .map(uiControl -> uiControl.process(document))
                            .reduce(false, (a,b) -> a|b);

                    if (isDocumentModified) {
                        saveFile(filePath, document);
                        counter[1]++;
                    }
                }

                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Processed files: " + counter[0]);
        System.out.println("Modified files: " + counter[1]);
    }

    private static boolean isHtmlFile(final Path filePath) {
        return !Files.isDirectory(filePath) && filePath.toString().endsWith(".html");
    }

    private static Document readFile(final Path filePath) throws IOException {
        final String content = Files.readString(filePath);
        return Jsoup.parse(content, Parser.xmlParser());
    }

    private static void saveFile(final Path filePath, final Document document) throws IOException {
        document.outputSettings(outputSettings);
        String htmlContent = document.toString()
                .replaceAll("=\"\"","")
                .replaceAll("&amp;","&");
        Files.writeString(filePath, htmlContent);
    }

}