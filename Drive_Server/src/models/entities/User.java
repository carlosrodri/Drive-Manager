package models.entities;

public class User {

	private String name;
	private String directory;
	public User(String name, String directory) {
		super();
		this.name = name;
		this.directory = directory;
	}
	public String getName() {
		return name;
	}
	public String getDirectory() {
		return directory;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", directory=" + directory + "]";
	}
}
