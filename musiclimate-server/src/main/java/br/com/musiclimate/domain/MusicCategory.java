package br.com.musiclimate.domain;

public enum MusicCategory {
	CLASSICAL("classical"), ROCK("rock"), POP("pop"), PARTY("party");

	private String description;

	private MusicCategory(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}