/**
 * 
 */
package eva.coach.timemanager.client.views.widgets;

/**
 * @author Martín Felipe Pérez-Guevara Truskowski
 *
 */
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;


/**
 * @author Daniel Kurka
 *
 */
public abstract class BasicCell<T> implements Cell<T> {

        private static Template TEMPLATE = GWT.create(Template.class);

        public interface Template extends SafeHtmlTemplates {
                @SafeHtmlTemplates.Template("<div class=\"{0}\">{1}</div>")
                SafeHtml content(String classes, String cellContents);

        }

        @Override
        public void render(SafeHtmlBuilder safeHtmlBuilder, final T model) {
                safeHtmlBuilder.append(TEMPLATE.content("", SafeHtmlUtils.htmlEscape(getDisplayString(model))));

        }

        public abstract String getDisplayString(T model);

        @Override
        public boolean canBeSelected(T model) {
                return false;
        }

}