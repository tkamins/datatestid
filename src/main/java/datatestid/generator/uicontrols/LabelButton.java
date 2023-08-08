package datatestid.generator.uicontrols;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LabelButton extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("label.btn")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element label) {
        String ngClick = label.attr("ng-click");
        if (!ngClick.isEmpty()) {
            String action = ngClick.split("'")[1].toLowerCase();
            label.attr(DATA_TESTID_KEY, "label.btn." + action);
            return true;
        }
        return false;
    }
}
