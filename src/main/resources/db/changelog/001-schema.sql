create table users (
     id 	    SERIAL primary key,
     name       VARCHAR NOT NULL
);

create table promissory_note (
    id              SERIAL primary key,
    lender_id       INTEGER NOT NULL,
    borrower_id     INTEGER NOT NULL,
    amount          DOUBLE PRECISION NOT NULL
)