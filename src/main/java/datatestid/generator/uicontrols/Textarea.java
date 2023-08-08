package datatestid.generator.uicontrols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Textarea extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("textarea")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element textarea) {
        String ngModel = textarea.attr("ng-model");
        if (!ngModel.isEmpty()) {
            Matcher matcher = Pattern.compile(".*\\.([A-Za-z]+)")
                    .matcher(ngModel);
            if (matcher.find()) {
                String ngModelName = matcher.group(1);
                textarea.attr(DATA_TESTID_KEY, "textarea." + ngModelName);
                return true;
            }
        }
        return false;
    }
}
