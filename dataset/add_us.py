# -*- coding: utf-8 -*-
"""
This code was used to insert our responses into our parsed evaluation set.
It takes our responses from our_responses.csv and converts it to users_us.json
When we ran our sanity check tests, we took the users_us.json and injected our
responses into the evaluation dataset before running the algorithm.
"""
import csv
import json
import os
from collections import defaultdict
import ast

csvfile = open('our_responses.csv', 'r')
jsonfile = open('temp_users.json', 'w')

fieldnames = ('age','bodyType','diet','drinks', 'drugs', 'education', 'essay0', 'essay1', 'essay2', 'essay3', 'essay4', 'essay5', 'essay6', 'essay7', 'essay8', 'essay9', 'ethnicity', 'height', 'income', 'job', 'lastOnline', 'location', 'offspring', 'orientation', 'pets', 'religion', 'sex', 'sign', 'smokes', 'speaks', 'status')
reader = csv.DictReader( csvfile, fieldnames)
for row in reader:
    json.dump(row, jsonfile)
    jsonfile.write('\n')
    
jsonfile.close()
print ('Done reading CSV file')

outfile_training = open('users_us.json', 'w')

users_json = []
users_dict_training = defaultdict(dict)

essays = ['essay0', 'essay1', 'essay2', 'essay3', 'essay4', 'essay5', 'essay6', 'essay7', 'essay8', 'essay9']
user_id = 64322


#read json file
for line in open('temp_users.json', 'r'):
    
    u = ast.literal_eval(line)
    
    #remove fields we decide to ignore
    if 'location' in u: del u['location']
    if 'income' in u: del u['income']
    if 'offspring' in u: del u['offspring']
    if 'drugs' in u: del u['drugs']
    if 'job' in u: del u['job']
    if 'lastOnline' in u: del u['lastOnline']
    #print u
    
    #clean up essay questions
    for e in essays:
        u[e] = u[e].replace('<br />', '')
        u[e] = u[e].replace('\n', ' ')
    
    #add user to dictionary
    if (user_id != 64322):
        users_dict_training[user_id]= u
    
    user_id += 1;

    
#if '64322' in users_dict_training: del users_dict_training['64322']

print (users_dict_training)

json.dump(users_dict_training, outfile_training)

outfile_training.close()

os.remove('temp_users.json')
print ('Removed temp_users.json')
print ('Done Parsing. Data written to dataset/parsed_users.dict')
