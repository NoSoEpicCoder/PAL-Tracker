package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;
    private KeyHolder keyHolder;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        keyHolder = new GeneratedKeyHolder();
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours) VALUES ( ?, ?, ?, ? )",
                            new String[]{"ID"});
                    ps.setLong(1, timeEntry.getProjectId());
                    ps.setLong(2, timeEntry.getUserId());
                    ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                    ps.setInt(4, timeEntry.getHours());
                    return ps;
                }, keyHolder);

        return find(keyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {
        return jdbcTemplate.query("SELECT * FROM time_entries WHERE ID = ?",
                new Object[]{id},
                resultSetExtractor);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("SELECT id, project_id, user_id, date, hours FROM time_entries", rowMapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbcTemplate.update("UPDATE time_entries SET project_id=?, user_id=?, date=?, hours=? WHERE id= ?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours(),
                id);

        return find(id);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM time_entries WHERE id=?", id);
    }

    private final RowMapper<TimeEntry> rowMapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> resultSetExtractor =
            (rs) -> rs.next() ? rowMapper.mapRow(rs, 1) : null;
}
