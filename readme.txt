README

1. We have used table with following schema

 		  
		  Table "public.proc"
	
	 Column | Type | Modifiers 
		--------+------+-----------
		 title  | text | not null
		 author | text | not null

the above table stores information ABOUT procedure attributes, like title , author name.

 
             Table "public.component"
  Column  | Type |           Modifiers            
----------+------+--------------------------------
 tag_name | text | not null
 type     | text | not null default 'TEXT'::text
 concept  | text | not null default 'TEXT '::text
 question | text | not null default ' '::text
 id       | text | 
 answer   | text | default ' '::text
 choices  | text | default ' '::text
 required | text | default 'false'::text
 helptext | text | default ' '::text 

the above tables stores general information about components in the xml file


2. For testing Purposes , we entered sample datas in respective table as follows


		Table "public.proc"

 		 title  | author 
		--------+--------
		 VIT-13 | azad,kundan

		Table "public.component"



	 tag_name |  type  | concept |       question        | id | answer | choices | required |              helptext              
	----------+--------+---------+-----------------------+----+--------+---------+----------+------------------------------------
 	 Element  | ENTRY  | TEXT    | Enter a name          | 1  |        |         | false    |  
 	 Element  | SELECT | TEXT    | Select a single value | 2  |        | 1,2,3,4 | false    |  
 	 Element  | ENTRY  | NUMBER  | Enter integer value   | 3  |        |         | true     | Please enter data before advancing



3. we have created locally the database "mysanadb" where the above tables are stored

4. We are storing the generated XML code from Json object passed to the function json2xml in test.xml

5. sample output is shown as stored in test.xml


<Procedure title="VIT-13"author="azad,kundan">
<Page>
	<Element>
		type="ENTRY"
		concept="TEXT"
		question="Enter a name"
		id="1"
		answer=" "
		helpText=" "
		required="false"
	</Element>
</Page>
<Page>
	<Element>
		type="SELECT"
		concept="TEXT"
		question="Select a single value"
		id="2"
		answer=" "
		choices="1,2,3,4"
		helpText=" "
		required="false"
	</Element>
</Page>
<Page>
	<Element>
		type="ENTRY"
		concept="NUMBER"
		question="Enter integer value"
		id="3"
		answer=" "
		helpText="Please enter data before advancing"
		required="true"
	</Element>
</Page>
</Procedure>

















