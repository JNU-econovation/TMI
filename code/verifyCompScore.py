from proxyLabelAssignement import proxyLabelAssignement
import collections
import ast
import re

def verify_compatibility():
    out_filename = 'output/compatibility_with_proxyLabel.txt'
    out_filename_ml = 'output/compatibility_with_proxyLabel_ML.txt'

    infile = open('../dataset/users_evaluation.json','r') # validation set을 불러온다.
    dictionary = ast.literal_eval(infile.readline())      # validation set => dictionary
    infile.close()

    infile = open('output/compatibility.json','r')        # 예측기 1
    users = ast.literal_eval(infile.readline())
    infile.close()
    infile = open('output/compatibility_ml.json','r')
    users_ml = ast.literal_eval(infile.readline())
    infile.close()

    compScore = {}
    compScore_ml = {}
    for user in users:
        compScore[user] = {}
        user_X = dictionary[(user)]
        for compUser in users[user]:
            user_Y = dictionary[compUser]
            score = proxyLabelAssignement(user_X, user_Y)
            if score >= 0.5:
                compScore[user][compUser] = 'Yes'
            else:
                compScore[user][compUser] = 'No'          

    for user_ml in users_ml:
        compScore_ml[user_ml] = {}
        user_X = dictionary[(user_ml)]
        for compUser_ml in users_ml[user_ml]:
            user_Y = dictionary[compUser_ml]
            score = proxyLabelAssignement(user_X, user_Y)
            if score >= 0.5:
                compScore_ml[user_ml][compUser_ml] = 'Yes'
            else:
                compScore_ml[user_ml][compUser_ml] = 'No'          

    print('Writing output to file: ', out_filename)
    all_true = 0
    all_false = 0
    with open(out_filename, 'w+') as f:
        for key in sorted(users):
            f.write(str(key) + '\t\t:' + ' [')
            for compUser in users[key]:
                f.write(str(compUser) + ': ' + str(compScore[key][compUser]) + ', ')
                if compScore[key][compUser] == 'Yes':
                    all_true += 1
                else:
                    all_false += 1
            f.write(']\n')  

    print('Writing output to file: ', out_filename_ml)
    all_true_ml = 0
    all_false_ml = 0
    with open(out_filename_ml, 'w+') as f:
        for key in sorted(users_ml):
            f.write(str(key) + '\t\t:' + ' [')
            for compUser in users_ml[key]:
                f.write(str(compUser) + ': ' + str(compScore_ml[key][compUser]) + ', ')
                if compScore_ml[key][compUser] == 'Yes':
                    all_true_ml += 1
                else:
                    all_false_ml += 1
            f.write(']\n') 

    accuracy = all_true/float(all_false+all_true)
    accuracy_ml = all_true_ml/float(all_false_ml+all_true_ml)
    #print "The number of true results that were correct were:", all_true_ml
    #print "The number of false positives are:", all_false_ml
    #print "Total number of results: ", all_true_ml+all_false_ml
    print("Precision is without ML is: ", accuracy)
    print("Precision is with ML is: ", accuracy_ml)
    
    return




