package bet.astral.messagemanager.offline;

import java.util.List;
import java.util.UUID;

public class OfflineMessageProfile {
	private final UUID uniqueId;
	private final List<String> databaseMessageIds;

	public OfflineMessageProfile(UUID uniqueId, List<String> databaseMessageIds) {
		this.uniqueId = uniqueId;
		this.databaseMessageIds = databaseMessageIds;
	}

	public UUID uniqueId() {
		return uniqueId;
	}

	public List<String> databaseMessageIds() {
		return databaseMessageIds;
	}
}
