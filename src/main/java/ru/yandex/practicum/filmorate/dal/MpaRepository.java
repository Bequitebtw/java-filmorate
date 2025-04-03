package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.queries.MpaQueries;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRepository extends BaseRepository<Mpa> {

    public MpaRepository(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper);
    }

    public List<Mpa> findAllMpa() {
        return findMany(MpaQueries.FIND_ALL_MPA_QUERY);
    }

    public Optional<Mpa> findMpaById(long mpaId) {
        return findOne(MpaQueries.FIND_MPA_BY_ID, mpaId);
    }
}
