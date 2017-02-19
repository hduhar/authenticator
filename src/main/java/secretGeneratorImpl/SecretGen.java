package secretGeneratorImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

import secretGeneratorConstants.Constants;

@Component("SecretGen")
public class SecretGen {

	private String secret;

	private String secretFilePath;

	public SecretGen() {
		String userHome = System.getProperty("user.home");
		StringBuilder secretFilePath = new StringBuilder(userHome).append(Constants.PATH_SEPARATOR)
				.append(Constants.GOOGLE_KEY);
		this.secretFilePath = secretFilePath.toString();
		setSecretFromFile();
	}

	public void setSecretFilePath(String secretFilePath) {
		this.secretFilePath = secretFilePath;
	}

	public String getSecretFilePath() {
		return secretFilePath;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecretFromFile() {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(secretFilePath));
			String secretLine = bufferedReader.readLine();
			this.secret = secretLine;

		} catch (FileNotFoundException e) {
			System.out.println("Exception FileNotFound " + e);
		} catch (IOException e) {
			System.out.println("Exception IOException" + e);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
