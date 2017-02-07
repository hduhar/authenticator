package authenticator.secretGeneratorImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import secretGeneratorConstants.Constants;

public class TotpImpl {

	private long interval;

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public void setDefaultInterval() {
		this.interval = Constants.DEFAULT_INTERVAL;
	}

	public long getClockCounter() {
		Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
		long currentTimeSeconds = calendar.getTimeInMillis() / 1000;
		return currentTimeSeconds / interval;
	}

	/*
	 * private int hash(String secret, long interval) { byte[] hash = new
	 * byte[0]; try { // Base32 encoding is just a requirement for google
	 * authenticator. // We can remove it on the next releases. hash = new
	 * Hmac(Hash.SHA1, Base32.decode(secret), interval).digest(); } catch
	 * (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
	 * (InvalidKeyException e) { e.printStackTrace(); } catch
	 * (Base32.DecodingException e) { e.printStackTrace(); } return
	 * bytesToInt(hash); }
	 */
}
