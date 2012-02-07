package eva.coach.timemanager.client.views;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;

import eva.coach.timemanager.client.presenters.CatAndActCreationPresenter;
import eva.coach.timemanager.client.presenters.CatAndActCreationPresenter.MyView;

public class CatAndActCreationView extends ViewImpl implements
		CatAndActCreationPresenter.MyView {

	LayoutPanel main;
	HeaderButton back;
	@Inject
	public CatAndActCreationView() {
		main=new LayoutPanel();
		
		HeaderPanel currentHeader=new HeaderPanel();
		currentHeader.setCenter("Otra pantalla");
		back=new HeaderButton();
		back.setText("back");
		currentHeader.setLeftWidget(back);
		main.add(currentHeader);
	}

	@Override
	public Widget asWidget() {
		return main;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.CatAndActCreationPresenter.MyView#creation()
	 */
	@Override
	public HasClickHandlers creation() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.CatAndActCreationPresenter.MyView#goBack()
	 */
	@Override
	public HasTapHandlers goBack() {
		return back;
	}
}
