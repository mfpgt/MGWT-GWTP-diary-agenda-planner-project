/**
 * 
 */
package eva.coach.timemanager.client.views.widgets;

/**
 * @author Martín Felipe Pérez-Guevara Truskowski
 *
 */
import java.util.ArrayList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.dialog.Dialog;
import com.googlecode.mgwt.ui.client.dialog.DialogPanel;
import com.googlecode.mgwt.ui.client.dialog.HasTitleText;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.theme.base.DialogCss;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MRadioButton;
import com.googlecode.mgwt.ui.client.widget.MSearchBox;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

/**
 * A simple confirm dialog with ok and cancel buttons
 * 
 * @author Daniel Kurka
 * @version $Id: $
 */
public class ConfirmDialogWithTextBox implements HasText, HasTitleText, Dialog {
	/**
	 * The callback used when buttons are taped
	 * 
	 * @author Daniel Kurka
	 * 
	 */
	public interface ConfirmCallback {
		/**
		 * Called if ok button is taped
		 */
		public void onOk();

		/**
		 * called if cancel button is taped
		 */
		public void onCancel();
		
		/**
		 * called if start button is taped
		 */
		public void onStart();
		
	}

	private PopinDialog popinDialog;
	private DialogPanel dialogPanel1;
	private Label textLabel;
	private ConfirmCallback callback;
	private MTextArea textArea=new MTextArea();
	private MTextBox textBox=new MTextBox();
	private MSearchBox filter=new MSearchBox();
	private WidgetList list=new WidgetList();
	private ArrayList<String> listNames;
	private Button start=new Button();

	/**
	 * Construct a Confirmdialg
	 * 
	 * @param title - the title of the dialog
	 * @param text - the text of the dialog
	 * @param callback - the callback used when a button of the dialog is taped
	 */
	
	//customization 1 with textbox for commentary (starting and stopping activities)
	//customization 2 with filtered checkbox list - label replaced by textBox and third button added (activities creation and possible start)
	//customization 3 with filtered checkbox list - label replaced by textBox (list creation)
	//customization 4 with filtered checkbox list (activities and list editing)
	public ConfirmDialogWithTextBox(String title, String text,ConfirmCallback callback, int option) {
		this(MGWTStyle.getTheme().getMGWTClientBundle().getDialogCss(), title, text, callback,option);
	}

	/**
	 * Construct a Confirmdialg
	 * 
	 * @param css . css to use
	 * @param title - the title of the dialog
	 * @param text - the text of the dialog
	 * @param callback - the callback used when a button of the dialog is taped
	 */
	public ConfirmDialogWithTextBox(DialogCss css, String title, String text, ConfirmCallback callback, int option) {
		this.callback = callback;
		popinDialog = new PopinDialog(css);
		dialogPanel1 = new DialogPanel();
		dialogPanel1.showCancelButton(true);
		dialogPanel1.showOkButton(true);

		textLabel = new Label();
		if(option==1){
			dialogPanel1.getContent().add(textLabel);
			dialogPanel1.getContent().add(textArea);
		}
		else if(option==2){
			dialogPanel1.getContent().add(textBox);
			filter.addValueChangeHandler(new ValueChangeHandler<String>(){
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					filterList();
				}
			});
			dialogPanel1.getContent().add(filter);
			
			ScrollPanel scrollPanel = new ScrollPanel();
            scrollPanel.setWidget(list);
            scrollPanel.setScrollingEnabledX(false);
            //FlowPanel flow=new FlowPanel();
            //flow.add(scrollPanel);
            //flow.setHeight("200px");
            scrollPanel.setHeight("300px");
            scrollPanel.setWidth("100%");
            scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
            dialogPanel1.getContent().add(scrollPanel);
            
            dialogPanel1.getContent().add(textArea);
            
            
            start.setConfirm(true);
            start.setText("start");
            start.setSmall(true);
            start.setRound(true);
            //start.setWidth("30%");
            dialogPanel1.getContent().add(start);
            start.addTapHandler(new TapHandler() {
    			@Override
    			public void onTap(TapEvent event) {
    				popinDialog.hide();
    				if (ConfirmDialogWithTextBox.this.callback != null)
    					ConfirmDialogWithTextBox.this.callback.onStart();
    			}
    		});
            
		}
		else if(option==3){
			dialogPanel1.getContent().add(textBox);
			filter.addValueChangeHandler(new ValueChangeHandler<String>(){
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					filterList();
				}
			});
			dialogPanel1.getContent().add(filter);
			
			ScrollPanel scrollPanel = new ScrollPanel();
            scrollPanel.setWidget(list);
            scrollPanel.setScrollingEnabledX(false);
            scrollPanel.setHeight("300px");
            scrollPanel.setWidth("100%");
            scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
            dialogPanel1.getContent().add(scrollPanel);
		}
		else if(option==4){
			dialogPanel1.getContent().add(textLabel);
			filter.addValueChangeHandler(new ValueChangeHandler<String>(){
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					filterList();
				}
			});
			dialogPanel1.getContent().add(filter);
			
			ScrollPanel scrollPanel = new ScrollPanel();
            scrollPanel.setWidget(list);
            scrollPanel.setScrollingEnabledX(false);
            scrollPanel.setHeight("300px");
            scrollPanel.setWidth("100%");
            scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
            dialogPanel1.getContent().add(scrollPanel);
		}
		popinDialog.add(dialogPanel1);

		dialogPanel1.getOkButton().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				popinDialog.hide();
				if (ConfirmDialogWithTextBox.this.callback != null)
					ConfirmDialogWithTextBox.this.callback.onOk();
			}
		});

		dialogPanel1.getCancelButton().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				popinDialog.hide();
				if (ConfirmDialogWithTextBox.this.callback != null)
					ConfirmDialogWithTextBox.this.callback.onCancel();
			}
		});

		setText(text);
		setTitleText(title);

		
	}
	
	public void filterList(){
		if(filter.getValue().equalsIgnoreCase("")){
			for(Widget wid:this.list){
				wid.setVisible(true);
			}
		}
		ArrayList<String> filtered=new ArrayList<String>();
		for(Widget wid:this.list){
			if(((MRadioButton)wid).getText().contains(filter.getValue())){
				wid.setVisible(true);
			}
			else{
				wid.setVisible(false);
			}
		}
	}
	
	public void setList(ArrayList<String> list){
		this.listNames=list;
		drawList(list);
	}
	
	public void drawList(ArrayList<String> list){
		this.list.clear();
		for(String item:list){
			MRadioButton box=new MRadioButton(item);
			box.setText(item);
			box.setValue(false);
			this.list.add(box);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.dialog.HasTitleText#setTitleText(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public void setTitleText(String title) {
		dialogPanel1.getDialogTitle().setHTML(title);

	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public void setText(String text) {
		textLabel.setText(text);

	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.dialog.HasTitleText#getTitleText()
	 */
	/** {@inheritDoc} */
	@Override
	public String getTitleText() {
		return dialogPanel1.getDialogTitle().getHTML();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasText#getText()
	 */
	/** {@inheritDoc} */
	@Override
	public String getText() {
		return textLabel.getText();
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.dialog.Dialog#show()
	 */
	/**
	 * <p>
	 * show
	 * </p>
	 */
	public void show() {
		popinDialog.center();
	}
	
	public String getCommentary(){
		if(textArea.getValue().isEmpty()) return "";
		return textArea.getValue();
	}
	
	public String getItemName(){
		return textBox.getText();
	}
	
	public ArrayList<String> getSelectedItems(){
		ArrayList<String> selected=new ArrayList<String>();
		for(Widget wid:this.list){
			if(((MRadioButton)wid).getValue()){
				selected.add(((MRadioButton)wid).getText());
			}
		}
		return selected;
	}

}