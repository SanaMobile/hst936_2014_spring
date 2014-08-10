"""
Tools to parse patient XML output from OpenMRS. It formats the patient
information in some compressed, inconsistent form that is crap and should be
rewritten.

Originally by Jenny Liu
"""

from xml.dom import minidom

def parse_patient_list_xml(s):
    patients = ""
    doc = minidom.parseString(s)
    doc = doc.childNodes[0]
    for i in range(0,len(doc.childNodes)):
        node = doc.childNodes[i]
        gender = node.getAttribute("gender")
        birthdate = node.getAttribute("birthdate")
        namenode = node.getElementsByTagName("name")[0]
        firstname = namenode.getElementsByTagName("givenName")[0].firstChild.data.strip()
        lastname = namenode.getElementsByTagName("familyName")[0].firstChild.data.strip()
        ids = node.getElementsByTagName("identifierList")[0].getElementsByTagName("identifier")
        for j in range(0,len(ids)):
            #format the string and add it to the list. birthdates are year+month+day
            #format is jenny19880926liu10909f where 10909=id number
            patient = firstname.lower() + birthdate[0:4] + birthdate[5:7] + birthdate[8:10] + lastname.lower() + ids[j].firstChild.data + gender.lower() + "##"
            patients += patient
    return patients

def parse_patient_xml(s):
    doc = minidom.parseString(s)
    doc = doc.childNodes[0]
    if (len(doc.childNodes)==0):
        return ""
    node = doc.childNodes[0]
    gender = node.getAttribute("gender")
    birthdate = node.getAttribute("birthdate")
    namenode = node.getElementsByTagName("name")[0]
    firstname = namenode.getElementsByTagName("givenName")[0].firstChild.data.strip()
    lastname = namenode.getElementsByTagName("familyName")[0].firstChild.data.strip()
    #format the string and add it to the list. birthdates are year+month+day
    #format is jenny19880926liuf
    patient = firstname.lower() + birthdate[0:4] + birthdate[5:7] + birthdate[8:10] + lastname.lower() + gender.lower()
    print patient
    return patient
