package CommandPackage;

public enum CommandType implements CommandTypeInfo{
	HELP("help", " * list all commands"),
	EXIT("exit", " * exit programm"),
	ADDI("addi", "<param1> <param2> * simple integer add", int.class, int.class),
	ADDF("addf", "<param1> <param2> * simple float add", float.class, float.class),
	ECHO("echo", "<param1> <param2> * echos param1 string param2 times", String.class, int.class);
	
	private final String name;
	private final String helpText;
	private Class<?> param1 = null;
	private Class<?> param2 = null;
	
	
	private CommandType(String name, String helpText) {
		this.name= name;
		this.helpText = helpText;
	}
	
	private CommandType(String name, String helpText, Class<?> param1, Class<?> param2) {
		this.name= name;
		this.helpText = helpText;
		this.param1 = param1;
		this.param2 = param2;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getHelpText() {
		return helpText;
	}
	
	public String getMethod() {
		return "";
	}
	
	public Class<?>[] getParamTypes() {
		if(param1 == null && param2 == null) {
			return new Class<?>[] {};
		}
		return new Class<?>[] {param1, param2};
	}
}
