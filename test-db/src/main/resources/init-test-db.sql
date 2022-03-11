INSERT INTO CUSTOMER(LOGIN, PASSWORD, ROLE, IS_ACTUAL)
VALUES
    ('admin1', '$2a$11$JEtcrXoUjypxNABqjFOUNeq5Vge4iyLK/9AgtEFM4tQuE3xe28UTa', 'ROLE_ADMIN', TRUE),
    ('customer1', '$2a$11$Z1O/klpFowFL6fFBflJlre/wrj73fmhsglEOInaFvTtXBdyxTwFPq', 'ROLE_USER', TRUE);

INSERT INTO PRODUCT(PICTURE, SHORT_DESCRIPTION, DETAIL_DESCRIPTION, PRICE, CREATION_DATE, UPDATE_DATE, CHANGED_BY)
VALUES
    ('Pic1', 'Short description for product 1', 'Detail description for product 1', 1.11, now(), now(), 1),
    ('Pic2', 'Short description for product 2', 'Detail description for product 2', 2.22, now(), now(), 1),
    ('Pic3', 'Short description for product 3', 'Detail description for product 3', 3.33, now(), now(), 1),
    ('Pic4', 'Short description for product 4', 'Detail description for product 4', 4.44, now(), now(), 1),
    ('Pic5', 'Short description for product 5', 'Detail description for product 5', 5.55, now(), now(), 1);


INSERT INTO CART_RECORDS(CUSTOMER_ID, PRODUCT_ID, QUANTITY)
VALUES
    (1, 2, 1),
    (1, 3, 5),
    (2, 1, 4),
    (2, 2, 6),
    (2, 3, 3);