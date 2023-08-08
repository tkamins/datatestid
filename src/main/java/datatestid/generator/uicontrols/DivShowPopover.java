package datatestid.generator.uicontrols;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DivShowPopover extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("div[ng-mouseover*=showPopover]")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element div) {
        int index = 0;
        div.attr(DATA_TESTID_KEY, "div.showPopover." + index);
        return true;
    }
}