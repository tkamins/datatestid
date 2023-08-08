package datatestid.generator.uicontrols;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Radio extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("input[type=radio]")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element radio) {
        String ngValue = radio.attr("ng-value");
        if (!ngValue.isEmpty()) {
            radio.attr(DATA_TESTID_KEY, "radio." + ngValue);
            return true;
        }
        return false;
    }
}
