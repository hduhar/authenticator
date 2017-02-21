package secretGeneratorImpl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Component;

import secretGeneratorConstants.Constants;

@Component("TotpImpl")
public class TotpImpl {

	private long interval;

	public TotpImpl(){
		System.out.println("Inside");
		this.interval = Constants.DEFAULT_INTERVAL;
	}
	public long getInterval() {
		return interval;
	}

	public void setInterval() {
		this.interval = Constants.DEFAULT_INTERVAL;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getClockCounter() {
		Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
		long currentTimeSeconds = calendar.getTimeInMillis() / 1000;
		return currentTimeSeconds / interval;
	}

	public int generateCode(String secret) throws NoSuchAlgorithmException, InvalidKeyException {
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(secret);
		long clockCounter = getClockCounter();
		int hash = codeIt(decodedKey, clockCounter);
		System.out.println("hash is : " + hash);
		return hash;
	}

	private int codeIt(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}

		SecretKeySpec signKey = new SecretKeySpec(key, Constants.ENCRYPT_TYPE);
		Mac mac = Mac.getInstance(Constants.ENCRYPT_TYPE);
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);

		int offset = hash[20 - 1] & 0xF;

		// We're using a long because Java hasn't got unsigned int.
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			// We are dealing with signed bytes:
			// we just keep the first byte.
			truncatedHash |= (hash[offset + i] & 0xFF);
		}

		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;

		return (int) truncatedHash;
	}

}
