package amao.krzysek.inviteme.mysql;

import java.sql.*;

public class MySQL {

    protected Connection connection;

    public MySQL() {}

    public void connect(final String user, final String password, final String host, final String port, final String database) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
            Statement statement = connection.createStatement();
            final String query = "CREATE TABLE IF NOT EXISTS `users` (`nickname` VARCHAR(100) NOT NULL, `invites` INT(11) NOT NULL, `points` INT(11) NOT NULL, `recommended` BOOLEAN NOT NULL,`ip` VARCHAR(100) NOT NULL, PRIMARY KEY (`nickname`));";
            statement.executeUpdate(query);
            statement.close();
        }
    }

    public Connection getConnection() { return this.connection; }

}

