package recipies;

import java.sql.Connection;
import recipies.dao.DbConnection;

public class Recipies {

	public static void main(String[] args) {
		
		Connection conn = DbConnection.getConnection();

	}

}
