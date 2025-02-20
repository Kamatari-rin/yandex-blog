package org.example.repository.impl;

import org.example.enums.LikeTargetType;
import org.example.model.Like;
import org.example.repository.LikeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LikeRepositoryImpl extends AbstractRepositoryImpl<Like> implements LikeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Like> likeRowMapper;

    public LikeRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Like> likeRowMapper) {
        super(jdbcTemplate, likeRowMapper);
        this.jdbcTemplate = jdbcTemplate;
        this.likeRowMapper = likeRowMapper;
    }

    @Override
    public String getTableName() {
        return "likes";
    }

    @Override
    public int countLikesByIdAndTarget(Long targetId, LikeTargetType targetType) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE target_id = ? AND target_type_id = ? AND is_liked = true";
        return jdbcTemplate.queryForObject(sql, Integer.class, targetId, targetType.getId());
    }

    @Override
    public Optional<Like> findByUserAndTarget(Long userId, Long targetId, LikeTargetType targetType) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE user_id = ? AND target_id = ? AND target_type_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, likeRowMapper, userId, targetId, targetType.getId()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Like saveOrUpdate(Like like) {
        String sql = "INSERT INTO " + getTableName() + " (user_id, target_id, target_type_id, is_liked) " +
                "VALUES (?, ?, ?, ?) ON CONFLICT (user_id, target_id, target_type_id) " +
                "DO UPDATE SET is_liked = EXCLUDED.is_liked RETURNING *";
        return jdbcTemplate.queryForObject(sql, likeRowMapper, like.getUserId(), like.getTargetId(), like.getTargetType().getId(), like.isLiked());
    }

    @Override
    public List<Like> findByUserIdAndTargetIdsAndType(Long userId, List<Long> targetIds, LikeTargetType targetType) {
        if (targetIds.isEmpty()) {
            return Collections.emptyList();
        }

        String inSql = String.join(",", Collections.nCopies(targetIds.size(), "?"));
        String sql = "SELECT * FROM " + getTableName() +
                " WHERE user_id = ? AND target_id IN (" + inSql + ") AND target_type_id = ?";

        List<Object> params = new ArrayList<>();
        params.add(userId);
        params.addAll(targetIds);
        params.add(targetType.getId());

        return jdbcTemplate.query(sql, likeRowMapper, params.toArray());
    }
}
