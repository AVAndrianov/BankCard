package ru.minusd.restservicecors;

public class Greeting {

	private final long id;
	private final String content;

	public Greeting() {
		this.id = -1;
		this.content = "";
	}

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
		System.out.println("Hello "+ content);

	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
