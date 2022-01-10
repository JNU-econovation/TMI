from features import compatibilityScoreCompute
from MLtrainingtesting import MLLabelAssignment
def orientation_match(x, y):
    #if x is male
    if (x['sex'] == 'm'):
        #print 'x is male'
        if (x['orientation'] == 'straight'):
            if ((y['sex'] == 'f' and y['orientation'] == 'straight') or (y['sex'] == 'f' and y['orientation'] == 'bisexual')):
                #print '1a. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' match'
                return True
            else:
                #print '1b. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' dont match'
                return False
        if (x['orientation'] == 'gay'):
            if ((y['sex'] == 'm' and y['orientation'] == 'gay') or (y['sex'] == 'm' and y['orientation'] == 'bisexual')):
                #print '2a. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' match'
                return True
            else:
                #print '2b. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' dont match'
                return False
        if (x['orientation'] == 'bisexual'):
            if ((y['sex'] == 'f' and y['orientation'] == 'straight') or (y['sex'] == 'f' and y['orientation'] == 'bisexual') or (y['sex'] == 'm' and y['orientation'] == 'gay') or (y['sex'] == 'm' and y['orientation'] == 'bisexual')):
                #print '3a. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' match'
                return True
            else:
                #print '3b. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' dont match'
                return False
    else:
        #print 'x is female'
        if (x['orientation'] == 'straight'):
            if ((y['sex'] == 'm' and y['orientation'] == 'straight') or (y['sex'] == 'm' and y['orientation'] == 'bisexual')):
                #print '4a. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' match'
                return True
            else:
                #print '4b. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' dont match'
                return False
        if (x['orientation'] == 'gay'):
            if ((y['sex'] == 'f' and y['orientation'] == 'gay') or (y['sex'] == 'f' and y['orientation'] == 'bisexual')):
                #print '5a. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' match'
                return True
            else:
                #print '5b. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' dont match'
                return False
        if (x['orientation'] == 'bisexual'):
            if ((y['sex'] == 'f' and y['orientation'] == 'straight') or (y['sex'] == 'f' and y['orientation'] == 'bisexual') or (y['sex'] == 'm' and y['orientation'] == 'gay') or (y['sex'] == 'm' and y['orientation'] == 'bisexual')):
                #print '6a. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' match'
                return True
            else:
                #print '6b. x: ', x['sex'], x['orientation'], 'y: ', y['sex'], y['orientation'], ' dont match'
                return False

def language_match(x, y):
    return True
    
#print 'Done defining functions'




from collections import Counter
import numpy as np
import json
import time
import heapq
import ast
import sys
import random

# call this function and pass users_json
def compute(users, max_users, top_k, outfile, outfile_ml, users_json):
    
    # score matrix를 만든다.
    S = np.zeros((max_users, max_users), dtype=np.float)
    S_ml = np.zeros((max_users, max_users), dtype=np.float)

    # diagonal element에는 모두 -1을 저장해서 추천하지 않도록 만든다.
    for x in range(0, max_users):
        S[x][x] = -1.0
        S_ml[x][x] = -1.0


    t1 = time.time()
    print('Created and initialized Score matrix of size ', len(S), 'x', len(S[0]))
    
    print('Computing Compatibility Scores...')

    keys = {}
    index_X = 0

    for key_X in users:
        keys[index_X] = key_X  
        index_Y = 0
        
        if (index_X == max_users):
            break

        for key_Y in users: 
            #print (key_X, key_Y)
            if (index_X != index_Y):  # 자신과 비교하는 경우 방지
                if (index_Y > index_X):  # 불필요하게 중복 계산하는 것 방지
                    score = 0.0
                    score_ml = 0.0
                    # orientation은 모두 straight만 계산하도록 설정하였기 때문에 불필요함.
                    if (orientation_match(users_json[key_X], users_json[key_Y]) and language_match(users_json[key_X], users_json[key_Y])):
                        score = compatibilityScoreCompute(users_json[key_X], users_json[key_Y])
                        score_ml = MLLabelAssignment(users_json[key_X], users_json[key_Y])
                    else:
                        score = 0.0
                        score_ml = 0.0

                    S[index_X][index_Y] = score
                    S[index_Y][index_X] = score

                    S_ml[index_X][index_Y] = score_ml
                    S_ml[index_Y][index_X] = score_ml
            index_Y += 1
        index_X += 1

    #print 'S = ', S
    #print 'S_ml = ', S_ml
    t3 = time.time()
    print('Done computing scores!')

    compatibility = {}
    compatibility_ml = {}
    print('Extracting top ', top_k, ' users')
    for i in range(max_users):
        indexes = heapq.nlargest(top_k, range(len(S[i])), S[i].__getitem__)
        cusers = []
        for j in indexes:
            cusers.append(keys[j])
        compatibility[keys[i]] = cusers

        indexes_ml = heapq.nlargest(top_k, range(len(S_ml[i])), S_ml[i].__getitem__)
        cusers_ml = []
        for j in indexes_ml:
            cusers_ml.append(keys[j])
        compatibility_ml[keys[i]] = cusers_ml


    print('Writing output to file: ', outfile)
    print('Writing ML output to file: ', outfile_ml)

    json.dump(compatibility, outfile)
    outfile.close()

    json.dump(compatibility_ml, outfile_ml)
    outfile_ml.close()

    return

# 호환성을 계산한다. (학습된 모델을 가지고)
def calculate_compatibility(max_users):   # 평가할 검증 데이터 갯수
    t0 = time.time()
    top_k = 5
    infile = open('../dataset/users_evaluation.json', 'r')  # 검증 데이터셋 부르기
    outfile = open('output/compatibility.json', 'w')        # 결과 저장할 호환성 파일
    outfile_ml = open('output/compatibility_ml.json', 'w')  # ml을 이용한 결과 저장할 호환성 파일

    users_json = ast.literal_eval(infile.readline())        # validation set => dictionary 
    infile.close()  
    
    length = len(users_json)
    print("Done reading users file. Number of users found = ", length, "!")
    print('Extracting', max_users, ' random users...')
    
    rand_users = random.sample(range(15000, 27747), max_users)
    #print (rand_users)
    users = []
    
    #for i in rand_users:
    #    users.append(str(i))

    i = 0
    for user in users_json:  # 사용자 한 명씩 반복하면서
        if (i >= max_users): 
            break
        users.append(user)
        i += 1
    #print 'users: ', users    
    
    compute(users, max_users, top_k, outfile, outfile_ml, users_json)
    
    t4 = time.time()
    print('Done computing compatibility scores! Total time: ', t4-t0)
    
    return

#calculate_compatibility(10)

def compatibility_ground_truth(max_users):
    
    t0 = time.time()
    top_k = 5
    infile = open('../dataset/users_evaluation.json', 'r')
    outfile = open('output/compatibility.json', 'w')
    outfile_ml = open('output/compatibility_ml.json', 'w')   

    users_json = ast.literal_eval(infile.readline())    
    infile.close()  
    
    length = len(users_json)

    print("Done reading users file. Number of users found = ", length, "!")
    print('Extracting', max_users, ' random users...')

    users = []
    i = 0
    for user in users_json:
        if (i >= max_users):
            break
        users.append(user)
        i += 1
    #print 'users: ', users
        
        
    infile_us = open('../dataset/users_us.json', 'r')
    users_us = ast.literal_eval(infile_us.readline())    
    infile_us.close()  
    length_us = len(users_us)
    print("Length of us users: ", length_us)
    print('max users before: ', max_users)
    max_users += length_us
    print('max users after: ', max_users)
    
    for user in users_us:
        users.append(user)
        users_json[user] = users_us[user]
        #print user
    #print 'users: ', users  
    
    compute(users, max_users, top_k, outfile, outfile_ml, users_json)
    
    t4 = time.time()
    print('Done computing compatibility scores! Total time: ', t4-t0)
    
    return

#compatibility_ground_truth(10)