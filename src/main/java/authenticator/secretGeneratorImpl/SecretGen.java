package authenticator.secretGeneratorImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SecretGen {

	private String secret;

	private String secretFilePath;

	public String getSecretFilePath() {
		return secretFilePath;
	}

	public void setSecretFilePath(String secretFilePath) {
		this.secretFilePath = secretFilePath;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setSecretFromFile(){
		BufferedReader bufferedReader = null;
		try{
			bufferedReader = new BufferedReader(new FileReader(secretFilePath));
			String secretLine = bufferedReader.readLine();
			secret = secretLine;
			
		} catch(FileNotFoundException e){
			System.out.println("Exception FileNotFound " + e);
		} catch(IOException e){
			System.out.println("Exception IOException" + e);
		} finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
