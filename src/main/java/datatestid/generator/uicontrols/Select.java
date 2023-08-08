package datatestid.generator.uicontrols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Select extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("select")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element select) {
        boolean isElementModified = false;
        String dataTestId = "select";
        String name = select.attr("name");
        if (!name.isEmpty()) {
            dataTestId += "." + name;
            isElementModified = true;
        }
        String ngModel = select.attr("ng-model");
        if (!ngModel.isEmpty()) {
            Matcher matcher = Pattern.compile(".*\\.([A-Za-z]+)")
                    .matcher(ngModel);
            if (matcher.find()) {
                String ngModelName = matcher.group(1);
                dataTestId += "." + ngModelName;
                isElementModified = true;
            }
        }

        if (isElementModified) {
            select.attr(DATA_TESTID_KEY, dataTestId);
        }
        return isElementModified;
    }
}
