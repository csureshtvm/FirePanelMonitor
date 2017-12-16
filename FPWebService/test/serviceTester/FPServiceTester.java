package serviceTester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vision.fpservices.db.service.AlarmEventService;
import com.vision.fpservices.util.ApplicationContextProvider;

public class FPServiceTester {
	
	@Autowired
	AlarmEventService alarmEventService;
	
	public FPServiceTester(){
		new ClassPathXmlApplicationContext("SpringContext.xml");
	}
	
	public void test(){
		alarmEventService.resetAlarm(2, "Resetting Alarm", "visionfocus");
	}
	
	public static void main(String[] args) {		
		
		FPServiceTester tester=new FPServiceTester();
		tester.test();
		
	}

}
