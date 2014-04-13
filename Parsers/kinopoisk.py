#!/usr/bin/python
# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import psycopg2


conn_string = "host='localhost' dbname='sentiment3d' user='postgres' password='postgres'"
conn = psycopg2.connect(conn_string)
cursor = conn.cursor()

def create_table():
    cursor.execute("CREATE TABLE checked_3d_opinions (id serial PRIMARY KEY, film_id int, message text, sent_grade smallint);")
    print "Creating table"


def putData(row_id, filmid, message, grade):
    cursor.execute("INSERT INTO checked_3d_opinions (film_id, message, sent_grade) VALUES (%s, %s, %s)", (filmid, message, grade))


def parse(n):
    row_id = 0
    requests.get("http://www.kinopoisk.ru/reviews/type/comment/period/month/perpage/200/")
    for i in range (1, n):
        r  = requests.get("http://www.kinopoisk.ru/reviews/type/comment/period/month/page/" + `i`)
        data = r.text
        soup = BeautifulSoup(data)

        for link in soup.find_all(attrs = {"class" : "response bad"}):
            message = link.find("span", class_ = "_reachbanner_").get_text()
            rating = 2
            print(message)
            print("rating: " + rating.get("data-content"))
            print('--------------------------------------')
            row_id += 1
            # putData(row_id, filmid, message, rating.get("data-content"))


#create_table()

parse(30)
# conn.commit()
# cursor.close()
# conn.close()




