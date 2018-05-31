package models.entities;

import java.io.File;

public class User {

	private String name;
	private File directory;
	public User(String name, File directory) {
		super();
		this.name = name;
		this.directory = directory;
	}
	public String getName() {
		return name;
	}
	public File getDirectory() {
		return directory;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", directory=" + directory + "]";
	}
}
