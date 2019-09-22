package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class EchoSignPDFPage extends BasePage{
	
	public EchoSignPDFPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	String success_message = "You have successfully signed the agreement “%s Acknowledgement Letter”.";
	//**********Buttons Selector*************//
	@FindBy(how = How.CSS, using = ".down")
	private WebElement downButton;
	
	@FindBy(how = How.CSS, using = ".next")
	private WebElement nextButton;
	
	@FindBy(how = How.CSS, using = ".apply")
	private WebElement applyButton;
	
	@FindBy(how = How.CSS, using = ".click-to-esign")
	private WebElement clictoesignButton;
	
	//**********Fields Selector*************//
	@FindBy(how = How.CSS, using = ".todo-signature")
	private WebElement textbox_sign;
	
	@FindBy(how = How.CSS, using = ".signature-type-name")
	private WebElement textbox_signature;
	
	@FindBy(how = How.CSS, using = "h1.title")
	private WebElement text_successMessage;
	
	//**********Methods *******************//
	
	public void goToEchoSignPDF(String URL)
	{
		openURLInNewTab(URL);
	}
	
	public void clickNextButton()
	{
		click(downButton);
		click(nextButton);
	}
	
	public void clickApplyButton()
	{
		click(applyButton);
	}
	
	public void clickClickToEsignButton()
	{
		click(clictoesignButton);
	}
	
	public void setSignatureField(String signature)
	{
		click(textbox_sign);
		type(textbox_signature,signature);
	}
	
	public void closeCurentTab()
	{
		closeCurrentTab();
	}
	
	public void assertSuccessMessage(String tsmcrecord) {
		String message = String.format(success_message, tsmcrecord);
		assertText(text_successMessage,message);
	}
}