package datatestid.generator.uicontrols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Button extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("button")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element button) {
        boolean isElementModified = false;
        String dataTestId = button.nodeName();
        String ngClick = button.attr("ng-click");
        if (ngClick.isEmpty()) {
            Element spanEl = button.selectFirst("span");
            if (spanEl != null) {
                String[] splitSpan = spanEl.text().split("\\.");
                if (splitSpan.length > 1) {
                    dataTestId += "." + spanEl.text().split("\\.")[1];
                } else {
                    dataTestId += "." + spanEl.text().split("\\.")[0];
                }
                isElementModified = true;
            }
            String uiSref = button.attr("ui-sref");
            if (!uiSref.isEmpty()) {
                dataTestId += "." + uiSref.split("\\(")[0];
                isElementModified = true;
            }
        } else {
            String testId = ngClick.replace("$", "").split("\\(")[0];
            if (testId.startsWith("vm.")) {
                Matcher matcher = Pattern.compile(".*\\.([A-Za-z]+)")
                        .matcher(testId);
                if (matcher.find()) {
                    testId = matcher.group(1);
                }
            }
            dataTestId += "." + testId;
            isElementModified = true;
        }

        if (isElementModified) {
            button.attr(DATA_TESTID_KEY, dataTestId);
        }
        return isElementModified;
    }
}