#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
import json
import traceback
import getopt
import numbers
import psycopg2
import psycopg2.extras
from xml.dom.minidom import Document
def json2xml(json_obj, line_padding=""):
    result_list = list()

    json_obj_type = type(json_obj)

    if json_obj_type is list:
        for sub_elem in json_obj:
            result_list.append(json2xml(sub_elem, line_padding))

        return "\n".join(result_list)

    if json_obj_type is dict:
        for tag_name in json_obj:
            sub_obj = json_obj[tag_name]
            result_list.append("%s<%s>" % (line_padding, tag_name))
            result_list.append(json2xml(sub_obj, "\t" + line_padding))
            result_list.append("%s</%s>" % (line_padding, tag_name))

        return "\n".join(result_list)

    return "%s%s" % (line_padding, json_obj)

con = None

try:
     
    con = psycopg2.connect(database='mysanadb', user='kundan')    
    
    cur = con.cursor()
  
    
    cur.execute("INSERT INTO carasa VALUES(1,'Audi',52642)")
    cur.execute("INSERT INTO carasa VALUES(2,'Mercedes',57127)")
    cur.execute("INSERT INTO carasa VALUES(3,'Skoda',9000)")
    cur.execute("INSERT INTO carasa VALUES(4,'Volvo',29000)")
    cur.execute("INSERT INTO carasa VALUES(5,'Bentley',350000)")
    cur.execute("INSERT INTO carasa VALUES(6,'Citroen',21000)")
    cur.execute("INSERT INTO carasa VALUES(7,'Hummer',41400)")
    cur.execute("INSERT INTO carasa VALUES(8,'Volkswagen',21600)")
    con.commit()
    cur.execute("SELECT * FROM carasa")
    print "\nShow me the databases:\n"
    for row in cur.fetchall():

       
      s='{"main" : {"aaa" : "10", "bbb" : [1,2,3]}}'
      
      row = '{"people": "'+row[1]+'"}'
      jk=json.dumps(row)
      j =json.loads(row)
      print(json2xml(j))
      
    
    
except psycopg2.DatabaseError, e:
    
    if con:
        con.rollback()
    
    print 'Error xyz %s' % e    
    sys.exit(1)
    
    
finally:
    
    if con:
        con.close()
