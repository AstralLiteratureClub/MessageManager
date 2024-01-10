
package bet.astral.messagemanager.database;

import bet.astral.messagemanager.offline.OfflineMessage;
import bet.astral.messagemanager.offline.ParsedOfflineMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface MessageDatabase<P extends JavaPlugin> {
	/**
	 * Returns the plugin for this database
	 * @return plugin
	 */
	P plugin();

	/**
	 * Saves the parsed message to the database and returns the Hashed message key
	 * @param parsedOfflineMessage message to save
	 * @return hashed message key
	 */
	String save(ParsedOfflineMessage parsedOfflineMessage);
	/**
	 * Parses the message
	 * and then saves the parsed instance of the message using {@link #save(ParsedOfflineMessage)}.
	 * Hashes the parsed message and returns a key based on the messages
	 * @param parsedOfflineMessage message to save
	 * @return hashed message key
	 */
	String save(OfflineMessage parsedOfflineMessage);

	/**
	 * Allows updating of a message in the database.
	 * Allows changing of the entire message or just one part.
	 * Rehashes the message and then returns the key.
	 * @param hashedMessageKey hashed message key
	 * @param offlineMessage offline message consumer allowing editing of the message
	 * @return new hashed message for the message
	 */
	CompletableFuture<String> update(String hashedMessageKey, Consumer<OfflineMessage> offlineMessage);

	/**
	 * Loads a message from the database with given hashed message key
	 * @param hashedMessageId message key
	 * @return parsed message
	 */
	CompletableFuture<ParsedOfflineMessage> load(String hashedMessageId);
	/**
	 * Loads a message from the database with given hashed message key.
	 * After loading the message, it deletes the message from the database
	 * @param hashedMessageId message key
	 * @return parsed message
	 */
	CompletableFuture<ParsedOfflineMessage> loadAndDelete(String hashedMessageId);
	void delete(String hashedMessageId);


	/**
	 * Creates a new instance of a message database.
	 * If the system does not support the database type, it will throw IllegalStateException
	 * @param plugin plugin
	 * @param type database type
	 * @return instance of message manager, else IllegalStateException
	 * @param <T> owning plugin
	 * @throws IllegalStateException if the server does not have drivers for the database
	 */
	@Contract(pure = true)
	static <T extends JavaPlugin> @Nullable MessageDatabase<T> createDatabaseInstance(T plugin, DatabaseType type) throws IllegalStateException{
		if (!type.driverFound){
			throw new IllegalStateException("Could not find drivers for new message database instance! No class found: "+ type.getDriverClass() + "; Is local: "+ type.isLocalFile());
		}
		switch (type){
			case H2 -> {
			}
			case MYSQL -> {
			}
			case SQLITE -> {
			}
			case POSTGRESQL -> {
			}
		}
		return null;
	}

}
