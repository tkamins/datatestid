package datatestid.generator.uicontrols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Ahref extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("a")
                .not(".hidden")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element ahref) {
        boolean isElementModified = false;
        String dataTestId = "a";

        String ngClick = ahref.attr("ng-click");
        if (ngClick.isEmpty()) {
            String uiSref = ahref.attr("ui-sref");
            if (!uiSref.isEmpty()) {
                dataTestId += "." + uiSref.split("\\(")[0];
                isElementModified = true;
            }
        } else {
            Matcher matcher = Pattern.compile(".*\\.([A-Za-z]+)")
                    .matcher(ngClick);
            if (matcher.find()) {
                String ngClickValue = matcher.group(1);
                dataTestId += "." + ngClickValue;
                isElementModified = true;
            }
        }

        if (isElementModified) {
            ahref.attr(DATA_TESTID_KEY, dataTestId);
        }
        return isElementModified;
    }
}
