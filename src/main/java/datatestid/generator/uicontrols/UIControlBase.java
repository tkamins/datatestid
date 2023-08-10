package datatestid.generator.uicontrols;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import datatestid.generator.UIControl;

abstract class UIControlBase implements UIControl {

    String DATA_TESTID_KEY = "data-testid";

    @Override
    public boolean process(final Document document) {
        return getElements(document).stream()
                .map(this::generateDataTestId)
                .reduce(false, (a,b) -> a|b);
    }

    abstract Elements getElements(final Document document);

    abstract boolean generateDataTestId(final Element element);

}
