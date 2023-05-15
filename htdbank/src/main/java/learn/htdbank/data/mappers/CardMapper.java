package learn.htdbank.data.mappers;

import learn.htdbank.models.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet resultSet, int i) throws SQLException {
        Card card = new Card();
        card.setCard_id(resultSet.getInt("card_id"));
        card.setType(resultSet.getString("types"));
        card.setAccount_id(resultSet.getInt("account_id"));
        card.setCustomer_id(resultSet.getInt("customer_id"));
        return card;
    }
}
