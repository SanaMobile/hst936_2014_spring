README

1. We have used table with following schema

 		  
		  Table "public.sample"
	
	         Column | Type | Modifiers 
		--------+------+-----------
		 title  | text | not null
		 author | text | not null
		 key    | text | not null
		 id     | int  | not null
		 
		 
the above table stores information ABOUT procedure attributes, like title , author name, key.

 
 Table "public.s2"
  Column  |  Type   |           Modifiers           
----------+---------+-------------------------------
 xml_id   | integer | 
 tag_name | text    | not null
 type     | text    | not null default 'TEXT'::text
 concept  | text    | not null default 'TEXT'::text
 question | text    | not null default ' '::text
 s_id     | text    | 
 answer   | text    | default ' '::text
 choices  | text    | default ' '::text
 required | text    | default 'false'::text
 helptext | text    | default ' '::text
Foreign-key constraints:
    "s2_xml_id_fkey" FOREIGN KEY (xml_id) REFERENCES sample(id) 

the above tables stores general information about components in the xml file


2. For testing Purposes , we entered sample datas in respective table as follows


		
 select * from sample;
 title | author |  key   | id 
-------+--------+--------+----
 Sana  | kundan | cancer |  1
 Sana  | kundan | cancer |  2
 Sana  | Azad   | TB     |  3
(3 rows)

 		 

		Table "public.s2"


xml_id | tag_name |  type  | concept |    question    | s_id | answer |  choices  | required |            helptext            
--------+----------+--------+---------+----------------+------+--------+-----------+----------+--------------------------------
      2 | Element  | RADIO  | TEXT    | SELECT INTEGER | 2    |        | 1,2,3,4   | false    |  
      1 | Element  | RADIO  | TEXT    | SELECT INTEGER | 2    |        | 1,2,3,4   | false    |  
      1 | Element  | ENTRY  | TEXT    | Enter name     | 1    |        |           | false    |  
      2 | Element  | ENTRY  | TEXT    | Enter number   | 1    |        |           | true     | please enter before advancing
      3 | Element  | SELECT | TEXT    | Choose numbers | 1    |        | 1,2,3,4,5 | true     | enter details before advancing
(5 rows)

3. we have created locally the database "mysanadb" where the above tables are stored

4. We are storing the generated XML code from Json object passed to the function json2xml in 1.xml

5. sample output is shown as stored in 1.xml


<Procedure title="Sana" key="cancer" author="kundan">
<Page>
	<Element>
		type="RADIO"
		concept="TEXT"
		question="SELECT INTEGER"
		id="2"
		answer=""
		choices="1,2,3,4"
		helpText=" "
		required="false"
	</Element>
</Page>
<Page>
	<Element>
		type="ENTRY"
		concept="TEXT"
		question="Enter name"
		id="1"
		answer=" "
		helpText=" "
		required="false"
	</Element>
</Page>
</Procedure>
















