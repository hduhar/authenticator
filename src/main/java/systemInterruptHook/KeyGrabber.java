package systemInterruptHook;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import secretGeneratorImpl.SecretGen;
import secretGeneratorImpl.TotpImpl;
import systemClipboard.CodeKeyToClipboard;

@Component("keyGrabber")
public class KeyGrabber implements NativeKeyListener{
	
	@Autowired
	@Qualifier("codeKeyToClipboard")
	CodeKeyToClipboard codeKeyToClipboard;

	@Autowired
	@Qualifier("TotpImpl")
	TotpImpl totp;
	
	@Autowired
	@Qualifier("SecretGen")
	SecretGen secretGen;
	
	public KeyGrabber(){
		registerNativeHook();
	}
	
	public void registerNativeHook(){
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_B && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0 )&& (e.getModifiers() & NativeKeyEvent.SHIFT_MASK) !=0) {
			System.out.println("Key Grabbed");
			Integer hash = null;
			try {
				hash = totp.generateCode(secretGen.getSecret());
			} catch (InvalidKeyException | NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			codeKeyToClipboard.setClipboard(hash);

		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
//	public static void main(String[] args){
//		new KeyGrabber().registerNativeHook();
//	}
	

}
