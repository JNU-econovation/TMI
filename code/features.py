from nltk.stem import PorterStemmer
from scipy import spatial


import re
import math
import collections 

pstemmer = PorterStemmer()
#cosine = spatial.distance.cosine()


def featureXYComputation(UserX, UserY):
    featureXY= [0]*8
    featureXY[0]= educationFeature(UserX['education'],UserY['education'])
    featureXY[1]= religionFeature(UserX['religion'],UserY['religion'])
    featureXY[2]= bodyTypeFeature(UserX['bodyType'],UserY['bodyType'])
    featureXY[3]= dietFeature(UserX['diet'],UserY['diet'])
    
    featureXY[4]= ageFeature(UserX['age'],UserY['age'])
    featureXY[5]= smokeFeature(UserX['smokes'],UserY['smokes'])
    featureXY[6]= drinkFeature(UserX['drinks'],UserY['drinks'])
    featureXY[7]= petFeature(UserX['pets'], UserY['pets'])
  
    return featureXY

#TODO: "you will be obselete when Ml comes to power !! "    
def compatibilityScoreCompute(UserX, UserY):
    featureXY= [0]*8
    featureXY[0]= educationFeature(UserX['education'],UserY['education'])
    featureXY[1]= religionFeature(UserX['religion'],UserY['religion'])
    featureXY[2]= bodyTypeFeature(UserX['bodyType'],UserY['bodyType'])
    featureXY[3]= dietFeature(UserX['diet'],UserY['diet'])
    
    featureXY[4]= ageFeature(UserX['age'],UserY['age'])
    featureXY[5]= smokeFeature(UserX['smokes'],UserY['smokes'])
    featureXY[6]= drinkFeature(UserX['drinks'],UserY['drinks'])
    featureXY[7] = petFeature(UserX['pets'], UserY['pets'])
    
    score= sum(featureXY)/ float(len(featureXY))
    return score


def ageFeature(x,y):
    weight = 0
    diff = abs(int(x)- int(y))
    if diff < 5:
        weight = 1/float(diff+1)
    return weight

def bodyTypeFeature(x,y):
    num_x= bodyTypeToNum(x)
    num_y= bodyTypeToNum(y)
    return math.log10(num_x*num_y+1)/math.log10(26) #dividing by max to normalize it 
    
def bodyTypeToNum(x):
    x= x.lower()
    if re.search("thin|skinny",x):
        num_x= 3 
    elif re.search("average|fit",x):
        num_x= 4 
    elif re.search("athletic|jacked",x):
        num_x= 5 
    elif re.search("overweight|a little extra|curvy|full figured",x):
        num_x= 3 
    elif re.search("used up",x):
        num_x= 2 
    else:
        num_x= 0 
    
    return num_x

def dietFeature(x,y):
    if x==y:
        #if both have matching response, just return the max, even if both responses are wacky. Eg, both have diet response as "love"
        return 1 
    else:
        num_x= dietToNum(x)
        num_y= dietToNum(y)
        return 1/float(abs(num_x-num_y)+1) if num_x!=0 and num_y!=0 else 0 
    
def dietToNum(x):
    x= x.lower()
    if re.search("vegetarian|vegan",x): 
        num_x= 2 
    elif re.search("kosher",x):
        num_x= 7
    elif re.search("halal",x):
        num_x= 10
    elif x is not None:
        num_x= 5 
    else:
        #no response case 
        num_x= 0
    return num_x    
    

def drinkFeature(x,y):
    if x==y:
        return 1 
    else:
        num_x= drinkToNum(x)
        num_y= drinkToNum(y)
        return 1/float(abs(num_x-num_y)+1) if num_x!=0 and num_y!=0 else 0

def drinkToNum(x):
    x=x.lower()
    if re.search("often|desperate|playmate|present",x):
        num_x= 1 
    elif re.search("social",x):
        num_x= 2 
    elif re.search("rare",x):
        num_x= 3 
    elif re.search("no|never",x):
        num_x= 4 
    else:
        num_x= 0 
    return num_x
    
    
#TODO: ignored this for now as not parsed in input vectors, might consider it later 
def drugFeature(x,y):
    if x==y:
        return 1 
    else:
        num_x= drugToNum(x)
        num_y= drugToNum(y)
        return 1/float(abs(num_x-num_y)+1) if num_x!=0 and num_y!=0 else 0

def drugToNum(x):
    x= x.lower()
    if re.search("often|smiling",x):
        num_x= 1 
    elif re.search("sometime",x):
        num_x= 2 
    elif re.search("no|never",x):
        num_x= 3
    else:
        num_x= 0 
    return num_x
        

# numpy caculation
def educationFeature(x,y):
    #premise: similar educational qualification people should prefer each other 
    if x==y:
        return 1 
    else:
        num_x= educationToNum(x)
        num_y= educationToNum(y)
        return 1/float(abs(num_x-num_y)+1) if num_x!=0 and num_y!=0 else 0

def educationToNum(x):
    x= x.lower()
    if re.search("space|high school",x):
        num_x= 1 
    elif re.search("college",x):
        num_x= 2 
    elif re.search("univ|master",x):
        num_x= 3
    elif re.search("law|med|ph",x):
        num_x= 4 
    else:
        num_x= 0 
    return num_x
                

def religionFeature(x,y):
    if x == y:
        weight = 1
    elif x != 'atheism' and y != 'atheism':
        weight = 0.5
    elif (x == 'atheism' and y != 'atheism') or (y == 'atheism' and x != 'atheism'):
        weight = 1/10
    else:
        weight = 0
    return weight

def smokeFeature(x,y):
    if x==y:
        return 1 
    else:
        num_x= smokeToNum(x)
        num_y= smokeToNum(y)
        return 1/float(abs(num_x-num_y)+1) if num_x!=0 and num_y!=0 else 0

def smokeToNum(x):
    x= x.lower()
    if re.search("yes",x):
        num_x= 1 
    elif re.search("sometime|drinking|trying to quit",x):
        num_x= 2 
    elif re.search("no|never",x):
        num_x= 3
    else:
        num_x= 0 
    return num_x





def petFeature(UserX= None, UserY= None):
    if UserX is None or UserY is None:
        return 0 
    
    cat_review_X, cat_review_Y, dog_review_X, dog_review_Y= None, None, None, None 
    #---------------------------------------------------------------------------------------
    # extracting review
    temp_X = UserX.split("and")
    for ele in temp_X:
        if re.search("cat",ele):
            cat_review_X= ele
        elif re.search("dogs",ele):
            dog_review_X= ele 

    temp_Y = UserY.split("and")
    for ele in temp_Y:
        if re.search("cat",ele):
            cat_review_Y= ele
        elif re.search("dogs",ele):
            dog_review_Y= ele 

    #-----------------------------------------------------------------------------------------
    #finding sentiment from review 
    cat_sentiment_X, cat_sentiment_Y, dog_sentiment_X, dog_sentiment_Y= 0,0,0,0
    #-1: dislike | 0: neutral | 1:like 
    if cat_review_X:
        if re.search("dislike",cat_review_X):
            cat_sentiment_X= -1 
        else:
            cat_sentiment_X= 1 
    if cat_review_Y:
        if re.search("dislike",cat_review_Y):
            cat_sentiment_Y= -1 
        else:
            cat_sentiment_Y= 1 
    if dog_review_X:
        if re.search("dislike",dog_review_X):
            dog_sentiment_X= -1 
        else:
            dog_sentiment_X= 1 
    if dog_review_Y:
        if re.search("dislike",dog_review_Y):
            dog_sentiment_Y= -1 
        else:
            dog_sentiment_Y= 1 
    
    #-----------------------------------------------------------------------------------------       
    #computing cat and dog matching scores  
    #TODO: make it complex: dont give zero score to "unknwon" and "likes cats"
    cat_score= 1 if cat_sentiment_X==cat_sentiment_Y else 0 
    dog_score= 1 if dog_sentiment_X==dog_sentiment_Y else 0
    pet_score =(cat_score + dog_score)
    
    return pet_score
    #------mine: returns between 0 to 2 inclusive=[0,2]
