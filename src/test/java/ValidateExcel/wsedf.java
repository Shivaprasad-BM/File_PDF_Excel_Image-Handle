package ValidateExcel;

public class wsedf {
	public static void main(String[] args) {

		String var1 = "Mismatch in header count. Expected 2, but found 1.\r\n"
				+ "Missing header: \"Indemnity Agreement – Minors\" is expected to be at  Level 2.";
		String var2 = "swdefghjn";
		
		StringBuilder comBuilder=new StringBuilder();
		comBuilder.append(var1)..append("   \n").append("  -------------------------  ").append("   \n").append(var2);
		System.out.println(comBuilder.toString());
	}

}
