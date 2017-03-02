package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.type.UserAction;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by zhambyl on 01/03/2017.
 */
@ApplicationScoped
public class UserActionService {

    @Resource(lookup = "jdbc/datasource")
    private DataSource ds;

    private Connection conn;

    public void save(UserAction userAction) {
        String sql = "INSERT INTO user_action (action, timestamp, username) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = getConn().prepareStatement(sql);
            stmt.setString(1, userAction.getAction());
            stmt.setTimestamp(2, new Timestamp(userAction.getTimestamp()));
            stmt.setString(3, userAction.getUserName());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = ds.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
