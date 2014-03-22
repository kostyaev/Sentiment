#!/usr/bin/python
# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import psycopg2


conn_string = "host='localhost' dbname='sentiment' user='postgres' password='postgres'"
conn = psycopg2.connect(conn_string)
cursor = conn.cursor()

def create_table():
    cursor.execute("CREATE TABLE opinions (id serial PRIMARY KEY, film_id int, message text, film_rate smallint);")
    print "Creating table"


def putData(row_id, filmid, message, rating):
    cursor.execute("INSERT INTO  opinions (film_id, message, film_rate) VALUES (%s, %s, %s)", (filmid, message, rating))

def parse(filmid, n):
    row_id = 0
    for i in range (1, n):
        r  = requests.get("http://films.imhonet.ru/element/" + `filmid` + "/opinions/?page=" + `i`)
        data = r.text
        soup = BeautifulSoup(data)
        for link in soup.find_all(attrs = {"class" : "m-comments-item-body-container"}):
            message = link.find(attrs = {"class" : "m-comments-content"}).get_text()[:-1]
            rating = link.find("span", class_ = "is-masked")
            if u"Отзыв, возможно, содержит нецензурную лексику" in message: continue
            if rating == None: continue
            print(message)
            print("rating: " + rating.get("data-content"))
            print('--------------------------------------')
            row_id += 1
            putData(row_id, filmid, message, rating.get("data-content"))


#create_table()
parse(1104147, 47)
conn.commit()
cursor.close()
conn.close()




