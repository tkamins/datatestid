package datatestid.generator.uicontrols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextInput extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("input[type=text]")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element input) {
        String ngModel = input.attr("ng-model");
        if (!ngModel.isEmpty()) {
            Matcher matcher = Pattern.compile(".*\\.([A-Za-z]+)")
                    .matcher(ngModel);
            if (matcher.find()) {
                String ngModelName = matcher.group(1);
                input.attr(DATA_TESTID_KEY, "input." + ngModelName);
                return true;
            }
        }
        return false;
    }
}
