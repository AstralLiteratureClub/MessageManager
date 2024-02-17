package bet.astral.messenger.database;

import lombok.Getter;

/**
 * Returns all the available database types for the message manager API.
 */
@Getter
public enum DatabaseType {
	/**
	 * MySQL database type
	 */
	MYSQL("com.mysql.jdbc.Driver", false),
	/**
	 * PostgreSQL database type
	 */
	POSTGRESQL("org.postresql.Driver", false),
	/**
	 * SQLite database type
	 */
	SQLITE("org.sqlite.SQLiteJDBCLoader", true),
	/**
	 * h2 database type
	 */
	H2("org.h2.Driver", true)
	;

	private final String driverClass;
	final boolean driverFound;
	private final boolean localFile;

	DatabaseType(final String driverClass, final boolean local) {
		this.localFile = local;
		boolean driverFound1;
		this.driverClass = driverClass;
		try {
			Class.forName(driverClass);
			driverFound1 = true;
		} catch (ClassNotFoundException e){
			driverFound1 = false;
		}
		driverFound = driverFound1;
	}
}
