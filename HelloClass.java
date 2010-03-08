public class HelloClass {
	public String message = null;

	public HelloClass(String arg) {
		message = arg;
	}

	public HelloClass() {
		message = "Hello World!";
	}

	public String toString() {
		return message;
	}

	public static void main(String[] args) {
		String message = join(args, " ");
		HelloClass hc = new HelloClass(message);
		System.out.println(hc);
	}
	public static String join(String[] strings, String delimiter) {
	    if (strings == null || strings.length == 0) return "";
	    boolean first = true;
	    StringBuilder builder = new StringBuilder();
	    for (String s : strings) {
	    	if (first) {
	    		first = false;
		    	builder.append(s);
	    	} else {
	    		builder.append(delimiter).append(s);
	    	}
	    }
	    return builder.toString();
	}
}
