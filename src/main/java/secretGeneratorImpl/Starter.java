package secretGeneratorImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Starter {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		ApplicationContext appCon = (ApplicationContext)new ClassPathXmlApplicationContext("application-config.xml");
		appCon.getBean("keyGrabber");
		

		System.out.println("hello");
	}

}
