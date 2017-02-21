package systemClipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.springframework.stereotype.Component;

@Component("CodeKeyToClipboard")
public class CodeKeyToClipboard {
	private Clipboard clpbrd ;

	public CodeKeyToClipboard() {
		this.clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	public void setClipboard(int code){
		StringSelection stringSelection = new StringSelection(Integer.toString(code));
		this.clpbrd.setContents(stringSelection, stringSelection);
	}
	
	
}
