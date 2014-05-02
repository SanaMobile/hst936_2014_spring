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
            result_list.append("%s</%s>" % (line_padding,tag_name ))

        return "\n".join(result_list)

    return "%s%s" % (line_padding, json_obj)

con = None

try:
     
    con = psycopg2.connect(database='mysanadb', user='kundan')    
    
    cur = con.cursor()
    
    con.commit()
    cur.execute("select id from sample")
    for y in cur.fetchall():
	z=str(y[0])+".xml"

        fh=open(z,"w")
        cur.execute("SELECT * FROM sample where id="+str(y[0]))
        for x in cur.fetchall():
            x='<Procedure title=\"'+x[0]+'\" key=\"'+x[2]+'\" author=\"'+x[1]+'\">\n'

            fh.write(x)
    
        cur.execute("SELECT * FROM s2 where xml_id="+str(y[0]))
    
        for row in cur.fetchall():

          if row[2]=="ENTRY" or row[2]=="TEXT" or row[2]=="NUMBER" : 
      	     row = '{"Page":{"'+row[1]+'":["type=\\"'+row[2]+'\\"","concept=\\"'+row[3]+'\\"","question=\\"'+row[4]+'\\"","id=\\"'+str(row[5])+'\\"","answer=\\"'+row[6]+'\\"","helpText=\\"'+row[9]+'\\"","required=\\"'+row[8]+'\\""] }}'
          if row[2]=="MULTI_SELECT" or row[2]=="RADIO" or row[2]=="SELECT" :      
             row = '{"Page":{"'+row[1]+'":["type=\\"'+row[2]+'\\"","concept=\\"'+row[3]+'\\"","question=\\"'+row[4]+'\\"","id=\\"'+str(row[5])+'\\"","answer=\\"'+row[6]+'\\"","choices=\\"'+row[7]+'\\"","helpText=\\"'+row[9]+'\\"","required=\\"'+row[8]+'\\""] }}'
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







