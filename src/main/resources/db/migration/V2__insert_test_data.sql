--Technically this should be a flyway only applying to tests, however, since we're using in-memory database, it's nice having some test data to start with.

INSERT INTO game (name) VALUES
    ('Sample Game');

INSERT INTO hand (player_name, card_count, game_id) VALUES
    ('Player 1', 6, 1),
    ('Player 2', 6, 1),
    ('Player 3', 6, 1);

INSERT INTO hand_cards (hand_id, card_type) VALUES
    (1, 'WHITE'),
    (1, 'MUSTARD'),
    (1, 'GREEN'),
    (1, 'CANDLESTICK'),
    (1, 'BALLROOM'),
    (1, 'CONSERVATORY');

INSERT INTO question (card_type_shown, showing_card, game_id, hand_id) VALUES
    (null, true, 1, 2),
    ('LIBRARY', true, 1, 2),
    (null, false, 1, 2);

INSERT INTO question_card_types (question_id, card_type) VALUES
    (1, 'HALL'),
    (1, 'MUSTARD'),
    (1, 'KNIFE'),
    (2, 'LIBRARY'),
    (2, 'SCARLET'),
    (2, 'ROPE'),
    (3, 'MUSTARD'),
    (3, 'CANDLESTICK'),
    (3, 'REVOLVER');