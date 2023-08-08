package datatestid.generator.uicontrols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Checkbox extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("input[type=checkbox]")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element checkbox) {
        String id = checkbox.attr("id");
        if (!id.isEmpty()) {
            checkbox.attr(DATA_TESTID_KEY, "checkbox." + id);
            return true;
        } else {
            String ngModel = checkbox.attr("ng-model");
            if (!ngModel.isEmpty()) {
                Matcher matcher = Pattern.compile(".*\\.([A-Za-z]+)")
                        .matcher(ngModel);
                if (matcher.find()) {
                    String ngModelName = matcher.group(1);
                    checkbox.attr(DATA_TESTID_KEY, "checkbox." + ngModelName);
                    return true;
                }
            }
        }
        return false;
    }
}
