package localhost.sandbox.jse8.A0Helper;

/**
 * This class intends to be inter-exchangeable with Log4j2 and Slf4j libraries, 
 * but using only Java SE components.
 * 
 * @author Alberto Romero
 * @since 2023-10-18
 */
public class LogHelper {


	public static void main () {
		LogHelper log = new LogHelper();
		log.info("Hello {}, today is {}, year {}", "Alberto", "wednesday", "2023");
	}


	private String defineString (Object... inMessage) {
		inMessage[0] = ((String) inMessage[0]).replace("{}", "%s");
		String[] auxMessage = new String[inMessage.length-1];
		for (int i = 1 ; i < inMessage.length ; i++) {
			if (inMessage[i] == null) {
				auxMessage[i-1] = "null";
			} else {
				auxMessage[i-1] = inMessage[i].toString();
			}
		}
		String out = String.format(inMessage[0].toString(), (Object[]) auxMessage);
		return out;
	}


	public void error (Object... inMessage) {
		System.err.println(defineString(inMessage));
	}


	public void warn (Object... inMessage) {
		System.out.println(defineString(inMessage));
	}


	public void info (Object... inMessage) {
		System.out.println(defineString(inMessage));
	}


	public void debug (Object... inMessage) {
		System.out.println(defineString(inMessage));
	}


	public void trace (Object... inMessage) {
		System.out.println(defineString(inMessage));
	}

}
