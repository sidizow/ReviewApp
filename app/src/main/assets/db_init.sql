PRAGMA foreign_keys = ON;

CREATE TABLE "accounts"(
	"id"		INTEGER PRIMARY KEY,
	"email" 	TEXT NOT NULL UNIQUE COLLATE NOCASE,
	"username" 	TEXT NOT NULL UNIQUE,
	"password"  TEXT NOT NULL,
	"created_at" INTEGER NOT NULL
);

CREATE TABLE "films"(
	"id"			INTEGER PRIMARY KEY,
	"title" 		TEXT NOT NULL,
	"description" 	TEXT NOT NULL,
	"summary_score"  REAL NOT NULL,
	"img" 			TEXT NOT NULL
);

CREATE TABLE "accounts_films_reviews"(
	"account_id"	INTEGER NOT NULL,
	"film_id" 		INTEGER NOT NULL,
	"rating" 		INTEGER,
	"review"   		TEXT,
	FOREIGN KEY("account_id") REFERENCES "accounts"("id")
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("film_id") REFERENCES "films"("id")
		ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE("account_id", "film_id")
);

INSERT INTO "accounts"("email", "username", "password", "created_at")
VALUES ("admin@gmail.com", "admin", "123", 0), ("test@gmail.com", "test", "123", 0);

INSERT INTO "films"("title", "description", "summary_score", "img")
VALUES 	("Зеленая миля", "Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», каждый из узников которого однажды проходит «зеленую милю» по пути к месту казни. Пол повидал много заключённых и надзирателей за время работы. Однако гигант Джон Коффи, обвинённый в страшном преступлении, стал одним из самых необычных обитателей блока.", 0.0, "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/acb932eb-c7d0-42de-92df-f5f306c4c48e/1920x"),
		("Побег из Шоушенка", "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения.", 0.0, "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/e26044e5-2d5a-4b38-a133-a776ad93366f/1920x");