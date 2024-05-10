package bet.astral.messenger.message;

public enum MessageType {
	CHAT,
	ACTION_BAR,
	TITLE,
	SUBTITLE;

	@Override
	public String toString() {
		return name();
	}
}
