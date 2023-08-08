package datatestid.generator.uicontrols;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UISelect extends UIControlBase {

    @Override
    Elements getElements(final Document document) {
        return document
                .select("ui-select")
                .not("[" + DATA_TESTID_KEY + "]");
    }

    @Override
    boolean generateDataTestId(final Element uiSelect) {
        String id = uiSelect.attr("id");
        if (!id.isEmpty()) {
            uiSelect.attr(DATA_TESTID_KEY, id);
            return true;
        }
        return false;
    }
}
