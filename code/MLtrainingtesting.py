from features import featureXYComputation
from proxyLabelAssignement import proxyLabelAssignement
#from compatibility_score import compability_score

import ast 
# import cPickle
import pickle
import random 
from sklearn import linear_model, svm
from sklearn.model_selection import GridSearchCV
import numpy as np
import time
from tqdm import tqdm



#doesnt matter
num_training_samples= 15000*1000

#------------------------------------------------------------- 

# 학습 framework
# training_files_path = '../dataset/users_training.json'
def training(training_files_path, ML_algo):
    
    infile = open(training_files_path,'r')
    fulldictionary = ast.literal_eval(infile.readline())
    infile.close()
    
    print("full dict type: ", type(fulldictionary))
    print("len of fulldict: ", len(fulldictionary))
    
    dictionary={}
    #small_Dict 
    
    random_keys = random.sample(fulldictionary.keys(), 2*samples_num)
    iindex=0
    for ele in random_keys:
        if iindex > samples_num:
            break
        iindex+=1 
        dictionary[ele] = fulldictionary[ele]
    
    #keys: tuple, value: features
    user_user_training_matrix = {}
    user_user_training_labels = {}
    
    #TODO: include all_keys thing and make no. of samples count 
    #all_keys= sorted(dictionary.keys())
    #print(all_keys[:50])
    flag,index= False, 0
    
    for userXID in tqdm(dictionary):
        if int(userXID)<0:
            continue
        if flag==True:
            break
        
        for userYID in dictionary:
            if int(userYID)<0:
                continue
            index+=1
            if index>num_training_samples:
                flag= True
                break
            
            dict_key= tuple([userXID, userYID])
            #print(dictionary[userXID]['age'])
            if dictionary[userXID] and dictionary[userYID]:
                user_user_training_matrix[dict_key]= featureXYComputation(dictionary[userXID], dictionary[userYID]) 
                computed_score= proxyLabelAssignement(dictionary[userXID], dictionary[userYID])
                
                if ML_algo==0:
                    if computed_score>=0.5:
                        user_user_training_labels[dict_key]= 1 
                    else:
                        user_user_training_labels[dict_key]= 0
                else:        
                    user_user_training_labels[dict_key]= computed_score

    print("matrix type : ", type(user_user_training_matrix))
    print("label type : ", type(user_user_training_labels))

    MLmodel(user_user_training_matrix,user_user_training_labels, ML_algo)
    print("training successfully completed, myclassifier.log generated !!!")
    return
    #print(user_user_training_labels)

#------------------------------------------------------------------------------
# MLmodel (1. user_user_training_matrix, user_user_training_labels)
def MLmodel(user_user_training_matrix,user_user_training_labels, ML_algo):
    #X: features 
    #y: labels
    
    X,y=[],[]
    keys= user_user_training_labels.keys()
    for key in keys:
        X.append(user_user_training_matrix[key])
        y.append(user_user_training_labels[key])
    
    #mine experiment
    if ML_algo==0:
        print("Running Logistic Regression")
        model = linear_model.LogisticRegression()    
    elif ML_algo==1:
        print("Running Linear Regression")
        model = linear_model.LinearRegression()    
    elif ML_algo==2:
        print("Support Vector Machine Regression")
        model = svm.SVR()
    else:
        print("Invalid choice of ML_algo, supported choices are 0,1 and 2")

    #param_grid = {'C': [0.001, 0.01, 0.1, 1, 10, 100, 1000] }
    #parameters = {'fit_intercept':[True,False],'copy_X':[True, False]}
    if ML_algo==0:
        parameters = {'C': [0.001, 0.01, 0.1, 1, 10, 100, 1000] }
    else:
        parameters={}
    
    
    #logistic = GridSearchCV(linear_model.LogisticRegression(penalty='l2'), param_grid)
    grid = GridSearchCV(model,parameters, cv=None)

    grid.fit(X,y)
    
    #dump classifier to a file 
    with open('myClassifier.log', 'wb') as fid:
        pickle.dump(grid, fid)      
    
    return 
    
#------------------------------------    
# 머신러닝을 이용한 score calculation
def MLLabelAssignment(userX, userY ):
    #compute features for this pair 
    feature_vector= np.array(featureXYComputation(userX, userY)) # UserX와 UserY의 값을 계산한 후 그 결과(list)를 array로 변경함
    feature_vector= feature_vector.reshape(1,-1) # [1, #feature]
    print(feature_vector)
    
    #load classifier:
    with open('myClassifier.log', 'rb') as fid:
        logistic_classifier = pickle.load(fid)
    result= logistic_classifier.predict(feature_vector)
    print(result)
    return result

def run_training(sample_num, ML_algo=1):
    global samples_num
    samples_num = sample_num
    print("Samples num", samples_num)
    time0 = time.time()
    training("../dataset/users_training.json", ML_algo)
    time1 = time.time()
    print("time taken for training: ", time1-time0)
    return


"""
training("../dataset/users_training.json")

user1= {"orientation": "straight", "sex": "m", "education": "working on space camp", "ethnicity": "white", "drinks": "often", "essay9": "", "religion": "agnosticism but not too serious about it", "pets": "likes dogs and likes cats", "status": "single", "bodyType": "average", "diet": "mostly other", "sign": "cancer", "speaks": "english (fluently), spanish (poorly), french (poorly)", "age": "35", "smokes": "no", "height": "70", "essay8": "i am very open and will share just about anything.", "essay1": "dedicating everyday to being an unbelievable badass.", "essay0": "i am a chef: this is what that means. 1. i am a workaholic. 2. i love to cook regardless of whether i am at work. 3. i love to drink and eat foods that are probably really bad for me. 4. i love being around people that resemble line 1-3. i love the outdoors and i am an avid skier. if its snowing i will be in tahoe at the very least. i am a very confident and friendly. i'm not interested in acting or being a typical guy. i have no time or patience for rediculous acts of territorial pissing. overall i am a very likable easygoing individual. i am very adventurous and always looking forward to doing new things and hopefully sharing it with the right person.", "essay3": "", "essay2": "being silly. having ridiculous amonts of fun wherever. being a smart ass. ohh and i can cook. ;)", "essay5": "delicious porkness in all of its glories. my big ass doughboy's sinking into 15 new inches. my overly resilient liver. a good sharp knife. my ps3... it plays blurays too. ;) my over the top energy and my outlook on life... just give me a bag of lemons and see what happens. ;)", "essay4": "i am die hard christopher moore fan. i don't really watch a lot of tv unless there is humor involved. i am kind of stuck on 90's alternative music. i am pretty much a fan of everything though... i do need to draw a line at most types of electronica.", "essay7": "", "essay6": ""}
user2= {"orientation": "straight", "sex": "m", "education": "working on college/university", "ethnicity": "asian, white", "drinks": "socially", "essay9": "you want to be swept off your feet! you are tired of the norm. you want to catch a coffee or a bite. or if you want to talk philosophy.", "religion": "agnosticism and very serious about it", "pets": "likes dogs and likes cats", "status": "single", "bodyType": "a little extra", "diet": "strictly anything", "sign": "gemini", "speaks": "english", "age": "22", "smokes": "sometimes", "height": "75", "essay8": "i am new to california and looking for someone to wisper my secrets to.", "essay1": "currently working as an international agent for a freight forwarding company. import, export, domestic you know the works. online classes and trying to better myself in my free time. perhaps a hours worth of a good book or a video game on a lazy sunday.", "essay0": "about me:  i would love to think that i was some some kind of intellectual: either the dumbest smart guy, or the smartest dumb guy. can't say i can tell the difference. i love to talk about ideas and concepts. i forge odd metaphors instead of reciting cliches. like the simularities between a friend of mine's house and an underwater salt mine. my favorite word is salt by the way (weird choice i know). to me most things in life are better as metaphors. i seek to make myself a little better everyday, in some productively lazy way. got tired of tying my shoes. considered hiring a five year old, but would probably have to tie both of our shoes... decided to only wear leather shoes dress shoes.  about you:  you love to have really serious, really deep conversations about really silly stuff. you have to be willing to snap me out of a light hearted rant with a kiss. you don't have to be funny, but you have to be able to make me laugh. you should be able to bend spoons with your mind, and telepathically make me smile while i am still at work. you should love life, and be cool with just letting the wind blow. extra points for reading all this and guessing my favorite video game (no hints given yet). and lastly you have a good attention span.", "essay3": "the way i look. i am a six foot half asian, half caucasian mutt. it makes it tough not to notice me, and for me to blend in.", "essay2": "making people laugh. ranting about a good salting. finding simplicity in complexity, and complexity in simplicity.", "essay5": "food. water. cell phone. shelter.", "essay4": "books: absurdistan, the republic, of mice and men (only book that made me want to cry), catcher in the rye, the prince.  movies: gladiator, operation valkyrie, the producers, down periscope.  shows: the borgia, arrested development, game of thrones, monty python  music: aesop rock, hail mary mallon, george thorogood and the delaware destroyers, felt  food: i'm down for anything.", "essay7": "trying to find someone to hang out with. i am down for anything except a club.", "essay6": "duality and humorous things"}

label= MLLabelAssignment(user1, user2) 
print(label)  
print("Success, check the classifier file !!! ") 
"""
