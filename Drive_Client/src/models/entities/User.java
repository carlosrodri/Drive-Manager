package models.entities;

public class User {

	private String name;
	private String directory;
	private String path;
	public User(String name, String directory, String path) {
		this.name = name;
		this.directory = directory;
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public String getDirectory() {
		return directory;
	}
	public String getPath() {
		return path;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", directory=" + directory + "]";
	}
}
