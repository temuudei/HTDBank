package learn.htdbank.data;

import learn.htdbank.data.mappers.CardMapper;
import learn.htdbank.models.Card;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class CardJdbcTemplateRepository implements CardRepository {
    private final JdbcTemplate jdbcTemplate;

    public CardJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<Card> findAll() {
        final String sql = "select card_id, `type` types, account_id, customer_id from Card limit 1000;";
        return jdbcTemplate.query(sql, new CardMapper());
    }

    @Override
    @Transactional
    public Card findById(int card_id) {
        final String sql = "select card_id, `type` types, account_id, customer_id "
                + "from Card where card_id = ?;";
        return jdbcTemplate.query(sql, new CardMapper(), card_id)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public Card add(Card card) {
        final String sql = "insert into Card (`type`, account_id, customer_id) values (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, card.getType());
            ps.setInt(2, card.getAccount_id());
            ps.setInt(3, card.getCustomer_id());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        card.setCard_id(keyHolder.getKey().intValue());
        return card;
    }

    @Override
    @Transactional
    public boolean update(Card card) {
        final String sql = "update Card set "
                + "`type` = ?, "
                + "account_id = ?, "
                + "customer_id = ? "
                + "where card_id = ?;";
        return jdbcTemplate.update(sql,
                card.getType(),
                card.getAccount_id(),
                card.getCustomer_id(),
                card.getCard_id()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int card_id) {
        return jdbcTemplate.update("delete from Card where card_id = ?;", card_id) > 0;
    }
}
