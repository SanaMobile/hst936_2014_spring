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
	    
	    y=tag_name.split(" ")
            result_list.append("%s<%s>" % (line_padding, tag_name))
            result_list.append(json2xml(sub_obj, "\t" + line_padding))
            result_list.append("%s</%s>" % (line_padding, y[0]))

        return "\n".join(result_list)

    return "%s%s" % (line_padding, json_obj)

con = None

try:
     
    con = psycopg2.connect(database='mysanadb', user='azadkundan')    
    
    cur = con.cursor()

    con.commit()
    fh=open("test.xml","w")
    cur.execute("SELECT * FROM proc")
    for x in cur.fetchall():
     x='<Procedure title=\"'+x[0]+'\"author=\"'+x[1]+'\">\n'

     fh.write(x)
    
    cur.execute("SELECT * FROM component")
    
    for row in cur.fetchall():

      if row[1]=="ENTRY" or row[1]=="TEXT" or row[1]=="NUMBER" : 
      	   row = '{"Page":{"'+row[0]+'":["type=\\"'+row[1]+'\\"","concept=\\"'+row[2]+'\\"","question=\\"'+row[3]+'\\"","id=\\"'+str(row[4])+'\\"","answer=\\"'+row[5]+'\\"","helpText=\\"'+row[8]+'\\"","required=\\"'+row[7]+'\\""] }}'
      if row[1]=="MULTI_SELECT" or row[1]=="RADIO" or row[1]=="SELECT" :      
           row = '{"Page":{"'+row[0]+'":["type=\\"'+row[1]+'\\"","concept=\\"'+row[2]+'\\"","question=\\"'+row[3]+'\\"","id=\\"'+str(row[4])+'\\"","answer=\\"'+row[5]+'\\"","choices=\\"'+row[6]+'\\"","helpText=\\"'+row[8]+'\\"","required=\\"'+row[7]+'\\""] }}'
      jk=json.dumps(row)
      j =json.loads(row)
      print(json2xml(j))
      fh.write(json2xml(j))
      fh.write("\n")
    fh.write("</Procedure>\n")
    fh.close()
    
except psycopg2.DatabaseError, e:
    
    if con:
        con.rollback()
    
    print 'Error xyz %s' % e    
    sys.exit(1)
    
    
finally:
    
    if con:
        con.close()







